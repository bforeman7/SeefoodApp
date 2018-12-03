package ActivityController;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import Communication.Endpoints;
import CustomViews.GalleryView;
import CustomViews.ImageBundleView;
import ImageModel.ImageBundle;
import test.hulbert.seefood.R;

/***
 *This class will be used for getting images that are already on the server and viewing them in gallery view.
 * The images will be added into an image bundle that will be displayed later.  If the server is down the user
 * is prompted with a message stating that it could not connect due to the server being down.
 */

public class GalleryActivity extends AppCompatActivity implements Controllable {
    private ImageBundle imageBundle;
    private ImageBundleView galleryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_gallery_final);

        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);
        galleryView = new GalleryView(this, this.getApplicationContext(), view);
        String response = getIntent().getStringExtra("response");
        JSONObject jsonResponses = null;
        try {
            jsonResponses = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(jsonResponses != null) {
            imageBundle = new ImageBundle();

            JSONArray jsonArray = null;
            try {
                jsonArray = jsonResponses.getJSONArray("images");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    imageBundle.addImageThroughJSON(jsonArray.getJSONObject(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            returnHome();
        }
        updateView();
    }


    /**
     * Returns user to home screen and clears all other activates from activity stack
     */
    public void returnHome() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    /**
     * This is the method that is called when the hardware back button is pressed.
     */
    @Override
    public void onBackPressed() {
        returnHome();
    }

    /**
     * Controller logic for deleting an image on th server
     * @param id server side id for image to delete
     * @param index index of image bundle to delete
     */
    public void deleteImage(int id, int index){
        Endpoints.deleteImage(id);
        imageBundle.deleteImageByID(index);
        updateView();
    }

    /**
     *This will update the view associated with this Activity
     */
    @Override
    public void updateView(){
        galleryView.bindImageBundle(imageBundle);
    }
}
