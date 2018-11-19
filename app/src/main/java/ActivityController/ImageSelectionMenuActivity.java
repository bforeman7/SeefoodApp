package ActivityController;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import CustomViews.BaseView;
import CustomViews.ImageSelectionMenuView;
import ImageModel.Image;
import ImageModel.ImageBundle;
import test.hulbert.seefood.BuildConfig;
import test.hulbert.seefood.R;

public class ImageSelectionMenuActivity extends AppCompatActivity implements Controllable {

    private ImageBundle myImageBundle;
    private Uri outPutfileUri;
    private Bitmap mBitmap;
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
        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddyyHHmm");
        String myDate = simpleDateFormat.format(Calendar.getInstance().getTime());
        File file = new File(Environment.
                getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                myDate + ".jpg");

        outPutfileUri = FileProvider.getUriForFile(this,BuildConfig.APPLICATION_ID + ".provider",file);


        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutfileUri);
        startActivityForResult(intent, 1);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {

            String uri = outPutfileUri.toString();
            Log.e("uri-:", uri);
            Toast.makeText(this, outPutfileUri.toString(), Toast.LENGTH_LONG).show();

            try {
                mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), outPutfileUri);
                Image newImage = new Image();
                newImage.setBitmap(mBitmap);
                newImage.setsFilePath(uri);
                myImageBundle.getImages().add(newImage);
                //Drawable draw = new BitmapDrawable(getResources(), mBitmap);

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

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
            Image newImage = new Image();
            newImage.setBitmap(bitmap);
            newImage.setsFilePath(imagePath);
            //we can parse this file name to get the date and time it was taken. may need to rotate image??
            myImageBundle.getImages().add(newImage);
        }
    }
}
