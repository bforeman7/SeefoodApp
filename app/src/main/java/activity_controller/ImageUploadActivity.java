package activity_controller;

import android.content.Intent;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import communication.Endpoints;
import custom_views.BaseView;
import custom_views.ImageUploadView;
import image_model.ImageBundle;
import test.hulbert.seefood.R;

public class ImageUploadActivity extends AppCompatActivity implements Controllable {

    private ArrayList<String> imagePaths;
    private BaseView imageUploadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // we set the initial content view
        setContentView(R.layout.image_upload_final);
        //
        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);
        imagePaths = getIntent().getStringArrayListExtra("imagePaths");
        imageUploadView = new ImageUploadView(this, view, this.getApplicationContext());
        ((ImageUploadView) imageUploadView).bindImages(imagePaths);
    }

    public void uploadImages(View view) {
        if(!imagePaths.isEmpty()) {
            new PostImages(){
                @Override public void onPostExecute(String result)
                {
                    if(jsonResponses != null) {
                        Intent intent = new Intent(getApplicationContext(), SeefoodActivity.class);
                        intent.putStringArrayListExtra("jsonResponses", jsonResponses);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Could not connect to the server.  Please try again when the server is running.", Toast.LENGTH_LONG).show();
                        ((ImageUploadView) imageUploadView).hideProgressBar();
                    }

                }

                @Override
                protected void onPreExecute() {
                    ((ImageUploadView) imageUploadView).showProgressBar();

                }

                @Override
                protected String doInBackground(String... params) {
                    jsonResponses = new ArrayList<String>();
                    JSONObject tmpJSON = null;

                    for(int i = 0; i < imagePaths.size(); i++) {
                        try {
                            tmpJSON = Endpoints.postFile(imagePaths.get(i), getCameraPhotoOrientation(imagePaths.get(i)));
                            if(tmpJSON== null){
                                jsonResponses = null;
                                return null;
                            }else {
                                jsonResponses.add(tmpJSON.getJSONObject("image").toString());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    return null;
                }
            }
            .execute("");
        }
        else {
            Toast.makeText(this, "You must select images before uploading",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void deleteImage(int index) {
        imagePaths.remove(index);
        updateView();
    }

    @Override
    public void updateView() {
        ((ImageUploadView) imageUploadView).bindImages(imagePaths);
    }

    public static int getCameraPhotoOrientation(String imageFilePath) {
        int rotate = 0;
        try {

            ExifInterface exif = null;

            exif = new ExifInterface(imageFilePath);
            String exifOrientation = exif
                    .getAttribute(ExifInterface.TAG_ORIENTATION);
            Log.d("exifOrientation", exifOrientation);
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rotate;
    }

    /* This is the finish() method which is called when the user wants to exit the current activity i.e. clicked the back button. */
    @Override
    public void finish() {
        // Since this intent is now finished, we need to send the color selection choices back to the parent intent
        Intent data = new Intent();
        data.putStringArrayListExtra("imagePaths", imagePaths);
        setResult(RESULT_OK, data);
        super.finish();
    }


    /* This is the method that is called when the hardware back button is pressed. */
    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999 && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Failed to send images to server.", Toast.LENGTH_LONG).show();
        }
    }

    private abstract class PostImages extends AsyncTask<String, Void, String> {
        public ArrayList<String> jsonResponses;
    }
}
