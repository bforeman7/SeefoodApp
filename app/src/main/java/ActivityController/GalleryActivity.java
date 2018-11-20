package ActivityController;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Communication.Endpoints;
import CustomViews.GalleryView;
import CustomViews.ImageBundleView;
import CustomViews.SeefoodView;
import ImageModel.ImageBundle;
import test.hulbert.seefood.R;

public class GalleryActivity extends AppCompatActivity implements Controllable {
    private ImageBundle imageBundle;
    private SeefoodView BaseView;
    private ScrollView myScrollView;
    private LinearLayout myLinearLayout;
    private ImageView imageView[];
    private ArrayList<String> imagePaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_gallery_final);

        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);
        ImageBundleView galleryView = new GalleryView(this, view);

        JSONObject jsonResponses = new JSONObject();
        jsonResponses = (Endpoints.getImages(1, 1000));
        try {
            ((GalleryView) galleryView).bindImages(jsonResponses.getJSONArray("images"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void returnHome(View view) {

    }

    public void updateConfidenceRating(int nRatingIncrease){

    }

    public void sendImagesToServer(View view){
    }

    @Override
    public void updateView(){

    }
}
