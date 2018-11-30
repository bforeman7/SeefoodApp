package ActivityController;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import Communication.Endpoints;
import CustomViews.BaseView;
import CustomViews.ImageUploadView;
import CustomViews.MenuView;
import ImageModel.Image;
import ImageModel.ImageBundle;
import test.hulbert.seefood.R;

public class MenuActivity extends AppCompatActivity implements Controllable {

    private ImageBundle myImageBundle;
    private Uri outPutfileUri;
    private BaseView menuView;

    Bitmap mBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // we set the initial content view
        setContentView(R.layout.home_final);
        //
        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);
        menuView = new MenuView(view, this.getApplicationContext());

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
        new GetGallery(){
            @Override public void onPostExecute(String result)
            {
                if(jsonResponses != null) {
                    Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
                    intent.putExtra("response", jsonResponses.toString());
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Could not connect to the server.  Please try again when the server is running.", Toast.LENGTH_LONG).show();
                    ((MenuView) menuView).hideProgressBar();
                }
            }

            @Override
            protected void onPreExecute() {
                ((MenuView) menuView).showProgressBar();
            }

            @Override
            protected String doInBackground(String... params) {
                jsonResponses = Endpoints.getImages(1, 1000);
//                for(int i=0;i<5;i++) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
                return null;
            }
        }
        .execute("");
    }

    @Override
    public void updateView() {

    }

    private abstract class GetGallery extends AsyncTask<String, Void, String> {
        public JSONObject jsonResponses;
    }
}
