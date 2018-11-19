package ActivityController;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import CustomViews.BaseView;
import CustomViews.ImageSelectionMenuView;
import CustomViews.SeefoodView;
import test.hulbert.seefood.R;

public class ImageUploadActivity extends AppCompatActivity implements Controllable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // we set the initial content view
        setContentView(R.layout.image_upload_final);
        //
        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);
        BaseView imageSelectionView = new ImageSelectionMenuView(view);
    }

    public void uploadImages(View view) {
        Intent intent = new Intent(this, SeefoodActivity.class);
        startActivity(intent);
    }

    public void goBack(View view) {

    }

    public void deleteImage(View view) {

    }

    @Override
    public void updateView() {

    }
}
