package activity_controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

import custom_views.ImageDetailsView;
import image_model.Image;
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
