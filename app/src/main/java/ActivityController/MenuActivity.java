package ActivityController;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONObject;
import Communication.Endpoints;
import CustomViews.BaseView;
import CustomViews.MenuView;
import test.hulbert.seefood.R;

/**
 * Controller class for Main menu for the application. Handles controller logic for the upload images UI path or view images UI path.
 */

public class MenuActivity extends AppCompatActivity implements Controllable {

    private BaseView menuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // we set the initial content view
        setContentView(R.layout.home_final);
        //
        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);
        menuView = new MenuView(view, this.getApplicationContext());

        // Check camera permissions
        int MY_CAMERA_REQUEST_CODE = 100;
        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_REQUEST_CODE);
        }
    }

    /***
     * This will take the user to select an image, take a picture, and review their images from the image selection menu.
     * @param view
     */
    public void TakePhoto(View view) {
        Intent intent = new Intent(this, ImageSelectionMenuActivity.class);
        startActivity(intent);
    }


    /**
     * Uses and Async thread to make a Endpoints call. Upon success the gallery activity will be started. Upon failure the menu activity will stay in view.
     * @param view
     */
    public void gotoGallery(View view) {
        new GetGallery(){
            // After Async call has been made
            @Override public void onPostExecute(String result)
            {
                if(jsonResponses != null) {
                    Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
                    intent.putExtra("response", jsonResponses.toString());
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Could not connect to the server.  Please try again when the server is running.", Toast.LENGTH_LONG).show();
                    ((MenuView) menuView).hideProgressBar();
                }
            }

            // Before async call is made. UI logic goes here.
            @Override
            protected void onPreExecute() {
                ((MenuView) menuView).showProgressBar();
            }

            // Task to be completed during async call
            @Override
            protected String doInBackground(String... params) {
                jsonResponses = Endpoints.getImages(1, 1000);
                return null;
            }
        }
        .execute("");
    }

    @Override
    public void updateView() {

    }

    /**
     * Async class for endpoints call
     */
    private abstract class GetGallery extends AsyncTask<String, Void, String> {
        public JSONObject jsonResponses;
    }
}
