package ActivityController;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import CustomViews.SeefoodView;
import ImageModel.ImageBundle;
import test.hulbert.seefood.R;

public class SeefoodActivity extends AppCompatActivity implements Controllable {

    private ImageBundle imageBundle;
    private SeefoodView BaseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
    }

    private void displaySelectedImages(){

    }

    public void updateConfidenceRating(int nRatingIncrease){

    }

    public void sendImagesToServer(View view){

    }

    @Override
    public void updateView(){

    }
}
