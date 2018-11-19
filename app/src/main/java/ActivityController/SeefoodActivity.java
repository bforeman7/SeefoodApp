package ActivityController;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

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

        ArrayList<JSONObject> jsonResponses = new ArrayList<>();

        for(int i = 0; i < imagePaths.size(); i++) {
            jsonResponses.add(Endpoints.postFile(imagePaths.get(i)));
        }
        ((SeefoodView) seefoodView).updateConfidenceRating(jsonResponses.get(0).toString());

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
