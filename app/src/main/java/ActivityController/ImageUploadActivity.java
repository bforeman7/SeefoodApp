package ActivityController;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Communication.Endpoints;
import CustomViews.BaseView;
import CustomViews.ImageSelectionMenuView;
import CustomViews.ImageUploadView;
import CustomViews.SeefoodView;
import test.hulbert.seefood.R;

public class ImageUploadActivity extends AppCompatActivity implements Controllable {

    private ArrayList<String> imagePaths;
    private BaseView imageUploadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // we set the initial content view
        setContentView(R.layout.image_upload_final);
        //
        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);
        imagePaths = getIntent().getStringArrayListExtra("imagePaths");
        imageUploadView = new ImageUploadView(this, view);
        ((ImageUploadView) imageUploadView).bindImages(imagePaths);
    }

    public void uploadImages(View view) {
        if(!imagePaths.isEmpty()) {
            Intent intent = new Intent(this, SeefoodActivity.class);
            intent.putStringArrayListExtra("imagePaths", imagePaths);
            startActivity(intent);
        }
    }

    public void deleteImage(int index) {
        imagePaths.remove(index);
        updateView();
    }

    @Override
    public void updateView() {
        ((ImageUploadView) imageUploadView).bindImages(imagePaths);
    }

    /* This is the finish() method which is called when the user wants to exit the current activity i.e. clicked the back button. */
    @Override
    public void finish() {
        // Since this intent is now finished, we need to send the color selection choices back to the parent intent
        Intent data = new Intent();
        data.putStringArrayListExtra("imagePaths", imagePaths);
        setResult(RESULT_OK, data);
        super.finish();
    }

    /* This is the method that is called when the hardware back button is pressed. */
    @Override
    public void onBackPressed() {
        finish();
    }
}
