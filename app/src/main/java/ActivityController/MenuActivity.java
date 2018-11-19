package ActivityController;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;

import CustomViews.BaseView;
import CustomViews.MenuView;
import ImageModel.Image;
import ImageModel.ImageBundle;
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
    }

    public void TakePhoto(View view) {
        Intent intent = new Intent(this, ImageSelectionMenuActivity.class);
        startActivity(intent);
    }

    public void gotoGallery(View view) {

    }

    @Override
    public void updateView() {

    }
}
