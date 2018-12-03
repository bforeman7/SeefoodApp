package Communication;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * This class holds all the endpoint calls to the server. It encapsulates client to server communication.
 */

public class Endpoints {
    private static String url = "http://18.220.189.219/";
    private static JSONObject jsonResponse;
    private static CountDownLatch countDownLatch;

    private Endpoints() {
    }

    private static void clearPreviousEndpointContext() {
        jsonResponse = null;
        countDownLatch = new CountDownLatch(1);
    }

    public static String getServerURL() {
        return url;
    }

    /**
     * This will GET a specific range of images from the server.
     * On success the response will be a JSON object with all the image data
     * On failure the response will be a null jSON object
     * @param start start of id ranges to get from server
     * @param end end of id ranges to get from server
     */
    public static JSONObject getImages(int start, int end) {

        OkHttpClient client = new OkHttpClient();
        String localURL = url + "images?start=" + start + "&end=" + end;

        // Form request
        Request request = new Request.Builder()
                .url(localURL)
                .build();

        clearPreviousEndpointContext();
        // this is here to await the end of the call so that way we don't do anything until request comes back
        // async call because sync call causes exception
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Failed request");
                e.printStackTrace();
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    try {
                        jsonResponse = new JSONObject(response.body().string());
                    } catch (JSONException e) {
                        System.out.println("Failed to get JSON");
                        e.printStackTrace();
                    }
                }
                System.out.println(jsonResponse);
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jsonResponse;

    }

    /**
     * This will POST a single image to the server.
     * Success returns a JSON with Image data. Failure will leave the JSON response as null.
     * @param imagePath path to image on mobile device
     * @param orientation orientation of image at imagePath
     */
    public static JSONObject postImage(String imagePath, int orientation) {

            final MediaType MEDIA_TYPE;

            // Verify image format
            if (imagePath.contains(".jpeg") || imagePath.contains(".jpg")){
                MEDIA_TYPE = MediaType.parse("image/jpeg");
            } else if (imagePath.contains(".png")) {
                MEDIA_TYPE = MediaType.parse("image/png");
            } else {
                Log.d("Endpoints", "Unsupported image format for image " + imagePath);
                return null;
            }

            File f = new File(imagePath);

            String date = new SimpleDateFormat("MM-dd-yyyy").format(new Date());

            // Form request
            RequestBody req = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image", f.getName(), RequestBody.create(MEDIA_TYPE, f))
                    .addFormDataPart("time_taken", date)
                    .addFormDataPart("image_orientation", Integer.toString(orientation))
                    .build();

            String localURL = url + "image";
            Request request = new Request.Builder().url(localURL).post(req).build();

            clearPreviousEndpointContext();
            OkHttpClient client = new OkHttpClient();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("Failed request");
                    e.printStackTrace();
                    countDownLatch.countDown();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response + " " + response.body().string());
                    } else {
                        // do something wih the result
                        try {
                            jsonResponse = new JSONObject(response.body().string());
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
            return jsonResponse;
    }

    /**
     * Sends a DELETE request to the server to delete an Image.
     * The request holds the image which will be deleted on the server.
     * Success will leave the JSON response with a server confirmation of successful deletion.
     * Failure will leave the JSON response as null.
     * @param id id of image to delete on server
     */
    public static JSONObject deleteImage(int id){
        OkHttpClient client = new OkHttpClient();
        String localURL = url + "image?id=" + id;

        // Form request
        Request request = new Request.Builder()
                .url(localURL)
                .delete()
                .build();

        clearPreviousEndpointContext();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Failed request");
                e.printStackTrace();
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    try {
                        jsonResponse = new JSONObject(response.body().string());
                    } catch (JSONException e) {
                        System.out.println("Failed to get JSON");
                        e.printStackTrace();
                    }
                }
                System.out.println(jsonResponse);
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jsonResponse;

    }

}
