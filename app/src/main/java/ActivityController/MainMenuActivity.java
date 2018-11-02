package ActivityController;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import CustomViews.MenuView;
import ImageModel.ImageBundle;
import in.mayanknagwanshi.imagepicker.imageCompression.ImageCompressionListener;
import in.mayanknagwanshi.imagepicker.imagePicker.ImagePicker;
import test.hulbert.seefood.BuildConfig;
import test.hulbert.seefood.R;

public class MainMenuActivity extends AppCompatActivity implements Controllable{

    private ImageBundle imageBundle;
    private MenuView baseView;
    private PhotoGalleryActivity myGallery;
    private ImageDetailsActivity imageDetails = new ImageDetailsActivity();
    private Button bExitingImage;
    private Button bTakePhoto;
    private Button bGallery;
    private ImageView iImageView;
    private Context mContext=MainMenuActivity.this;
    private static final int REQUEST = 112;
    private Bitmap mBitmap;
    private ImagePicker imagePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        imagePicker = new ImagePicker();
        Permissions();
        iImageView = findViewById(R.id.imageView);
        imageBundle = new ImageBundle();
    }

    public void gotoGallery(View view){
        myGallery.displayGallery(imagePicker);
    }

    public void selectImages(View view){
        imagePicker.withActivity(this).chooseFromGallery(false).withCompression(true).start();
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (requestCode == ImagePicker.SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            //Add compression listener if withCompression is set to true

           imageBundle.compressImages(data,imagePicker);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //do here
                } else {
                    Toast.makeText(mContext, "The app was not allowed to read your store.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void Permissions(){
        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (!hasPermissions(mContext, PERMISSIONS)) {
                ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS, REQUEST);
            } else {
                //do here
            }
        } else {
            //do here
        }
    }


    @Override
    public void updateView(){

    }
}
