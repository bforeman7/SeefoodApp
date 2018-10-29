package ActivityController;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import CustomViews.MenuView;
import ImageModel.ImageBundle;
import test.hulbert.seefood.R;

public class MainMenuActivity extends AppCompatActivity implements Controllable{

    private ImageBundle imageBundle;
    private MenuView baseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
    }

    public void gotoGallery(View view){

    }

    public void selectImages(View view){

    }

    public void takPictures(View view){

    }

    @Override
    public void updateView(){

    }
}
