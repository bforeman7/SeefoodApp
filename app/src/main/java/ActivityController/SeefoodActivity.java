package ActivityController;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seefood_final);

        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);
        ImageBundleView menuView = new SeefoodView(view);

        //need to set the scrollview and linear layout to variables
//        displaySelectedImages();
    }

    public void returnHome(View view) {

    }


    public void updateConfidenceRating(int nRatingIncrease){

    }

    public void sendImagesToServer(View view){
        Endpoints server = new Endpoints();
    }

    @Override
    public void updateView(){

    }
}
