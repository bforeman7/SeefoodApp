package ActivityController;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
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
import test.hulbert.seefood.BuildConfig;
import test.hulbert.seefood.R;

import static android.os.Environment.getExternalStoragePublicDirectory;

/**
 * Controller responsible for the ImageSelectionMenu screen. Handles controller logic for images being selected through camera or file path.
 */

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
        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);
        imagePaths = new ArrayList<>();
    }

    /**
     * The user is sent to the camera to take a picture.
     * @param view
     */
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

    /**
     * The user is taken to the gallery to select an image to send.
     * @param view
     */
    public void selectImage(View view) {
        Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    /**
     * This will call on image upload class if the user presses the review button
     * @param view
     */
    public void reviewImages(View view) {
        Intent intent = new Intent(this, ImageUploadActivity.class);
        intent.putStringArrayListExtra("imagePaths", imagePaths);
        startActivityForResult(intent, 3);
    }

    /**
     * Nothing is displayed that must also be updated in this controller's view.
     */
    @Override
    public void updateView() {

    }

    /**
     * This will get the activity of a image selection from the gallery or a picture was taken and save the bitmap and the
     * file path for the images. Additionally, it also get data from the next activity if it returns its execution.
     * @param requestCode request code given when intent was created
     * @param resultCode resulting code from intent that has finished
     * @param data the completed intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            try {
                mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), outPutfileUri);
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
