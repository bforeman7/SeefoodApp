package activity_controller;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import custom_views.BaseView;
import custom_views.ImageSelectionMenuView;
import image_model.ImageBundle;
import test.hulbert.seefood.BuildConfig;
import test.hulbert.seefood.R;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class ImageSelectionMenuActivity extends AppCompatActivity implements Controllable {
    private Uri outPutfileUri;
    private Bitmap mBitmap;
    private ArrayList<String> imagePaths;
    private String myFilePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // we set the initial content view
        setContentView(R.layout.image_selection_final);
        //
        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);
        BaseView imageSelectionView = new ImageSelectionMenuView(view);
//        myImageBundle = new ImageBundle();
        imagePaths = new ArrayList<>();
    }

    public void takePicture(View view) {
        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddyyHHmmss");
        String myDate = simpleDateFormat.format(Calendar.getInstance().getTime());
        File file = new File(getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                myDate + ".jpg");

        outPutfileUri = FileProvider.getUriForFile(this,BuildConfig.APPLICATION_ID + ".provider",file);


        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutfileUri);
        startActivityForResult(intent, 1);
        myFilePath = file.getPath();
    }

    public void selectImage(View view) {
        Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    public void reviewImages(View view) {
        Intent intent = new Intent(this, ImageUploadActivity.class);
        intent.putStringArrayListExtra("imagePaths", imagePaths);
        startActivityForResult(intent, 3);
    }

    @Override
    public void updateView() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String uri = outPutfileUri.toString();
            try {
                mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), outPutfileUri);
                //Drawable draw = new BitmapDrawable(getResources(), mBitmap);
                if (mBitmap.getWidth() > mBitmap.getHeight()) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    mBitmap = Bitmap.createBitmap(mBitmap , 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
                }
                imagePaths.add(myFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            imagePaths.add(imagePath);
        }
        // user backed out of reviewing and uploading their images, so we need to get the updated list in case an image was deleted
        else if(requestCode == 3 && resultCode == RESULT_OK) {
            if(data.hasExtra("imagePaths")) {
                imagePaths = data.getStringArrayListExtra("imagePaths");
            }
        }
    }

}
