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

public class ImageSelectionTestClass extends AppCompatActivity implements Controllable{

    private ImageBundle imageBundle;
    private MenuView baseView;
    private Button bExitingImage;
    private Button bTakePhoto;
    private Button bGallery;
    private ImageView iImageView;
    private Context mContext=ImageSelectionTestClass.this;
    private static final int REQUEST = 112;
    private Bitmap mBitmap;
    private Uri outPutfileUri;
    private ImagePicker imagePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayout);

        imagePicker = new ImagePicker();
        Permissions();
        iImageView = findViewById(R.id.imageView);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        if (requestCode == 1 && resultCode==RESULT_OK) {
//
//            String uri = outPutfileUri.toString();
//            Log.e("uri-:", uri);
//            Toast.makeText(this, outPutfileUri.toString(),Toast.LENGTH_LONG).show();
//
//            try {
//                mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), outPutfileUri);
//                Drawable draw = new BitmapDrawable(getResources(), mBitmap);
//                iImageView.setImageDrawable(draw);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//        }
//    }

    public void gotoGallery(View view){
        imagePicker.withActivity(this).chooseFromGallery(true).withCompression(true).start();
    }

    public void selectImages(View view){
        imagePicker.withActivity(this).chooseFromGallery(false).withCompression(true).start();
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (requestCode == ImagePicker.SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            //Add compression listener if withCompression is set to true
            imagePicker.addOnCompressListener(new ImageCompressionListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onCompressed(String filePath) {//filePath of the compressed image
                    //convert to bitmap easily
                    Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
                    iImageView.setImageBitmap(selectedImage);
                    // we need exception handling here if the image is not selected for camera!
                }
            });
        }
        //call the method 'getImageFilePath(Intent data)' even if compression is set to false
        String filePath = imagePicker.getImageFilePath(data);
        if (filePath != null) {//filePath will return null if compression is set to true
            Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
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
