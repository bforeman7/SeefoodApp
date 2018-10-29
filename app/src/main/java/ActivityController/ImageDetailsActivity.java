package ActivityController;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import CustomViews.ImageDetailsView;
import ImageModel.Image;
import test.hulbert.seefood.R;

public class ImageDetailsActivity extends AppCompatActivity implements Controllable {

    private Image image;
    private ImageDetailsView baseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
    }

    @Override
    public void updateView(){

    }

    public void dispalyImageDetails(Image image){

    }
}
