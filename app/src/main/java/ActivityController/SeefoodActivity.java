package ActivityController;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Communication.Endpoints;
import CustomViews.ImageBundleView;
import CustomViews.MenuView;
import CustomViews.SeefoodView;
import ImageModel.Image;
import ImageModel.ImageBundle;
import test.hulbert.seefood.R;

public class SeefoodActivity extends AppCompatActivity implements Controllable {

    private ImageBundle imageBundle;
    private SeefoodView BaseView;
    private ScrollView myScrollView;
    private LinearLayout myLinearLayout;
    private ImageView imageView[];
    private ArrayList<String> imagePaths;

    public SeefoodActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seefood_final);

        imagePaths = getIntent().getStringArrayListExtra("imagePaths");

        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);
        ImageBundleView seefoodView = new SeefoodView(this, view);

        ArrayList<JSONObject> jsonResponses = new ArrayList<JSONObject>();
        imageBundle = new ImageBundle();

        for(int i = 0; i < imagePaths.size(); i++) {
            try {
                imageBundle.addImageThroughJSON(Endpoints.postFile(imagePaths.get(i)).getJSONObject("image"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ((SeefoodView) seefoodView).bindImageBundle(imageBundle);



    }

    /*
    returns user to home screen and clears all other activates from activity stack
     */
    public void returnHome() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void updateConfidenceRating(int nRatingIncrease){

    }


    /* This is the finish() method which is called when the user wants to exit the current activity i.e. clicked the back button. */
    @Override
    public void finish() {
        // Since this intent is now finished, we need to send the color selection choices back to the parent intent
        Intent data = new Intent();
        setResult(RESULT_OK, data);
        super.finish();
    }


    /* This is the method that is called when the hardware back button is pressed. */
    @Override
    public void onBackPressed() {

    }

    @Override
    public void updateView(){

    }
}
