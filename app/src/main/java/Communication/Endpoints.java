package Communication;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import ImageModel.ImageBundle;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Endpoints {
    private Endpoints instance = new Endpoints();
    private Context context;
    private ImageBundle imageBundle;
    private static String myPath = "";
    private static String url = "http://18.220.189.219/";
    private static JSONObject json;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private Endpoints() {
    }


    public static JSONObject getImages(int start, int end) {

        OkHttpClient client = new OkHttpClient();
        url = url + "images?start=" + start + "&end=" + end;
        Request request = new Request.Builder()
                .url(url)
                .build();

        // this is here to await the end of the call so that way we don't do anything until request comes back
        // async call because sync call causes exception
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    // do something wih the result
                    try {
                        json = new JSONObject(response.body().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(json.toString());
        return json;

    }

    public static JSONObject postFile(String imagePath) {

            final MediaType MEDIA_TYPE;

            if (imagePath.contains(".jpeg") || imagePath.contains(".jpg")){
                MEDIA_TYPE = MediaType.parse("image/jpeg");
            } else if (imagePath.contains(".png")) {
                MEDIA_TYPE = MediaType.parse("image/png");
            } else {
                Log.d("Endpoints", "Unsupported image format for image " + imagePath);
                return null;
            }

            File f = new File(imagePath);

            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

            RequestBody req = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image", f.getName(), RequestBody.create(MEDIA_TYPE, f))
                    .addFormDataPart("time_taken", date).build();

            Request request = new Request.Builder().url(url + "image").post(req).build();

            OkHttpClient client = new OkHttpClient();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    } else {
                        // do something wih the result
                        try {
                            json = new JSONObject(response.body().string());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    countDownLatch.countDown();
                }
            });

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(json.toString());
            return json;
        }

}
