package ActivityController;

import android.media.ExifInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.InputStream;

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

    public void obtainImageDetails(File myFile){

    }

    public void displayImageDetails(Image image){

    }

}
