package ActivityController;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import CustomViews.ImageBundleView;
import CustomViews.SeefoodView;
import ImageModel.ImageBundle;
import test.hulbert.seefood.R;

/**
 * Controller class for the Seefood screen. Handles controller logic which displays the images sent to the server and their food confidence ratings.
 */

public class SeefoodActivity extends AppCompatActivity implements Controllable {

    private ImageBundle imageBundle;
    private ArrayList<String> imagePaths;
    private ImageBundleView seefoodView;

    public SeefoodActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seefood_final);

        imagePaths = getIntent().getStringArrayListExtra("imagePaths");

        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);
        seefoodView = new SeefoodView(this, this.getApplicationContext(), view);
        ArrayList<String> responses = getIntent().getStringArrayListExtra("jsonResponses");
        imageBundle = new ImageBundle();

        for (String response : responses) {
            try {
                imageBundle.addImageThroughJSON(new JSONObject(response));
            } catch (JSONException e) {
                finish();
                e.printStackTrace();
            }
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
     * This is the finish() method which is called when the user wants to exit the current activity i.e. clicked the back button.
     * */
    @Override
    public void finish() {
        // Since this intent is now finished, we need to send the color selection choices back to the parent intent
        Intent data = new Intent();
        setResult(RESULT_CANCELED, data);
        super.finish();
    }


    /**
     * This is the method that is called when the hardware back button is pressed.
     */
    @Override
    public void onBackPressed() {

    }

    /**
     * Updates the view for the current activity
     */
    @Override
    public void updateView(){
        (seefoodView).bindImageBundle(imageBundle);
    }
}
