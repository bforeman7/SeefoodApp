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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import Communication.Endpoints;
import CustomViews.GalleryView;
import CustomViews.MenuView;
import ImageModel.Image;
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
    private OutputStream os;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seefood_final);

        imagePicker = new ImagePicker();
        Permissions();
        iImageView = findViewById(R.id.imageView);
        imageBundle = new ImageBundle();
    }

    public void gotoGallery(View view){
        imagePicker.withActivity(this).chooseFromGallery(true).withCompression(false).start();
    }

    public void selectImages(View view){
        imagePicker.withActivity(this).chooseFromGallery(false).withCompression(false).start();
    }

    public void postImage(View view){
        Endpoints endpoints = new Endpoints(this);
        endpoints.postFile(imageBundle,0);
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (requestCode == ImagePicker.SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            //Add compression listener if withCompression is set to true

            imagePicker.addOnCompressListener(new ImageCompressionListener() {
                @Override
                public void onStart() {
                    // picker.withCompression(true);
                }

                @Override
                public void onCompressed(String filePath)  {//filePath of the compressed image
                    //convert to bitmap easily
                    Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
                }
            });
            try {
                String filePath = imagePicker.getImageFilePath(data);
                if (filePath != null) {//filePath will return null if compression is set to true
                    Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
                    //this creates a new image object to add the new image and
                    //store the bitmap and the file path and the time.
                    SimpleDateFormat date = new SimpleDateFormat("MM-DD-yyyy");
                    Date newDate = new Date(Calendar.DATE);
                    Image image = new Image();
                    image.setBitmap(selectedImage);
                    image.setsFilePath(filePath);
                    image.setDateTaken(date.format(newDate));
                    imageBundle.getImages().add(image);
                }
            }catch (Exception e){}
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
