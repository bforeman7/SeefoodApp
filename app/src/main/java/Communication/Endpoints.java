package Communication;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
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
    private final String myUrl = "http://0.0.0.0:5000/image";
    private Context context;
    private ImageBundle imageBundle;
    private String myPath = "";
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


//    public static ImageBundle getSpecificNumberOfImagesFromServer(int nNumber,int nNumber2){
//
//    }
//
//    public static int getPrimaryKeyForNewImageRecord(){
//
//    }

    }
