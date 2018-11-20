//package ActivityController;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.os.Build;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.esafirm.imagepicker.features.ImagePicker;
//import com.esafirm.imagepicker.features.ReturnMode;
//
//import org.json.JSONObject;
//import java.io.FileOutputStream;
//import java.nio.ByteBuffer;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//import Communication.Endpoints;
//import CustomViews.MenuView;
//import ImageModel.Image;
//import ImageModel.ImageBundle;
//import br.com.packapps.retropicker.callback.CallbackPicker;
//import br.com.packapps.retropicker.config.Retropicker;
//import test.hulbert.seefood.R;
//
///*
//This class is now deprecated and only here for testing purposes. MenuActivity is its replacement.
// */
//
//
//public class MainMenuActivity extends AppCompatActivity implements Controllable{
//
//    private ImageBundle imageBundle;
//    private MenuView baseView;
//    private PhotoGalleryActivity myGallery;
//    private ImageDetailsActivity imageDetails = new ImageDetailsActivity();
//    private Button bExitingImage;
//    private Button bTakePhoto;
//    private Button bGallery;
//    private ImageView iImageView;
//    private Context mContext=MainMenuActivity.this;
//    private static final int REQUEST = 112;
//    private Bitmap mBitmap;
//    private FileOutputStream os;
//    private MenuView menuView;
//    private Retropicker retropicker;
//    private  Retropicker.Builder builder;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.home_final);
//        Permissions();
//        iImageView = findViewById(R.id.imageView);
//        imageBundle = new ImageBundle();
//    }
//
//    public void gotoGallery(View view){
//      //  ImagePicker.create(this).limit(10).showCamera(true).imageDirectory("Camera").start();
//        Retropicker.Builder builder2 =  new Retropicker.Builder(this);
//        builder.setTypeAction(Retropicker.GALLERY_PICKER).checkPermission(false);
//
//        builder2.enquee(new CallbackPicker() {
//            @Override
//            public void onSuccess(Bitmap bitmap, String s) {
//                Image newImage = new Image();
//                newImage.setBitmap(bitmap);
//                newImage.setsName(s);
//                imageBundle.getImages().add(newImage);
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//                throwable.getStackTrace();
//            }
//        });
//
//        retropicker = builder.create();
//        ((Retropicker) retropicker).open();
//
//    }
//
//    public void takePicture(View view){
//        //ImagePicker.cameraOnly().start(this);
//        Retropicker.Builder builder2 =  new Retropicker.Builder(this)
//                .setTypeAction(Retropicker.CAMERA_PICKER) //Para abrir a galeria passe Retropicker.GALLERY_PICKER
//                .setImageName("first_image.jpg") //Opicional
//                .checkPermission(false);
//
//        builder2.enquee(new CallbackPicker() {
//            @Override
//            public void onSuccess(Bitmap bitmap, String s) {
//                Image newImage = new Image();
//                newImage.setBitmap(bitmap);
//                newImage.setsName(s);
//                imageBundle.getImages().add(newImage);
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//                throwable.getStackTrace();
//            }
//        });
//
//        retropicker = builder.create();
//        retropicker.open();
//
//    }
//
//    public void postImage(View view){
//
////        if(imageBundle.getImages().size() != 0) {
////            Endpoints.postImage(imageBundle.getImageByID(0).getsFilePath());
////        }
//        JSONObject json = Endpoints.getImages(1, 3);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
////        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
////            // Get a list of picked images
////            List<com.esafirm.imagepicker.model.Image> images = ImagePicker.getImages(data);
////            Bitmap bitmap = images.get(0);
////           //StoreImagesinList(ImagePicker.getImages(data));
////            // or get a single image only
////
////        }
//
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
////    private void StoreImagesinList(List<com.esafirm.imagepicker.model.Image> imageList){
////        com.esafirm.imagepicker.model.Image image = imageList.get(0);
////        ByteBuffer buffer = image.getPath()[0].getBuffer();
////        byte[] bytes = new byte[buffer.capacity()];
////        buffer.get(bytes);
////        Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
////
////        for (int i = 0; i < imageList.size(); i++){
////           // imageBundle.getImages().add(imageList.get(i));
////        }
////    }
//
//    private static boolean hasPermissions(Context context, String... permissions) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
//            for (String permission : permissions) {
//                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
////    @Override
////    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
////        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
////        switch (requestCode) {
////            case REQUEST: {
////                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                    //do here
////                } else {
////                    Toast.makeText(mContext, "The app was not allowed to read your store.", Toast.LENGTH_LONG).show();
////                }
////            }
////        }
////    }
//
//    @Override
//    public  void  onRequestPermissionsResult ( int  requestCode , @NonNull  String [] permissions , @NonNull  int [] grantResults ) {
//        super . onRequestPermissionsResult (requestCode, permissions, grantResults);
//
//        retropicker . onRequesPermissionResult (requestCode, permissions, grantResults);
//
//    }
//
//    private void Permissions(){
//        if (Build.VERSION.SDK_INT >= 23) {
//            String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
//            if (!hasPermissions(mContext, PERMISSIONS)) {
//                ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS, REQUEST);
//            } else {
//                //do here
//            }
//        } else {
//            //do here
//        }
//    }
//
//
//    @Override
//    public void updateView(){
//
//    }
//}
