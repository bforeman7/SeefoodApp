package activity_controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import custom_views.BaseView;
import custom_views.MenuView;
import image_model.ImageBundle;
import test.hulbert.seefood.R;

public class MenuActivity extends AppCompatActivity implements Controllable {

    private ImageBundle myImageBundle;
    private Uri outPutfileUri;

    Bitmap mBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // we set the initial content view
        setContentView(R.layout.home_final);
        //
        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);
        BaseView menuView = new MenuView(view);

        int MY_CAMERA_REQUEST_CODE = 100;
        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_REQUEST_CODE);
        }
    }

    public void TakePhoto(View view) {
        Intent intent = new Intent(this, ImageSelectionMenuActivity.class);
        startActivity(intent);
    }

    public void gotoGallery(View view) {
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateView() {

    }
}
