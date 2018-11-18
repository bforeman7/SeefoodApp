package ActivityController;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import CustomViews.BaseView;
import CustomViews.ImageSelectionMenuView;
import test.hulbert.seefood.R;

public class ImageSelectionMenuActivity extends AppCompatActivity implements Controllable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // we set the initial content view
        setContentView(R.layout.image_selection_final);
        //
        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);
        BaseView imageSelectionView = new ImageSelectionMenuView(view);
    }

    public void takePicture(View view) {
//        Intent intent = new Intent(this, .class);
    }

    public void selectImage(View view) {

    }

    public void reviewImages(View view) {
        Intent intent = new Intent(this, ImageUploadActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateView() {

    }
}
