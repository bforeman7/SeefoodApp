package ActivityController;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import CustomViews.BaseView;
import CustomViews.MenuView;
import ImageModel.Image;
import ImageModel.ImageBundle;
import br.com.packapps.retropicker.callback.CallbackPicker;
import br.com.packapps.retropicker.config.Retropicker;
import test.hulbert.seefood.R;

public class MenuActivity extends AppCompatActivity implements Controllable {

    private Retropicker retropicker;
    private ImageBundle myImageBundle;
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
        final Retropicker.Builder builder2 =  new Retropicker.Builder(this)
                .setTypeAction(Retropicker.GALLERY_PICKER) //Para abrir a galeria passe Retropicker.GALLERY_PICKER
                .setImageName("first_image.jpg") //Opicional
                .checkPermission(false);

        builder2.enquee(new CallbackPicker() {
            @Override
            public void onSuccess(Bitmap bitmap, String s) {
                Image newImage = new Image();
                myImageBundle = new ImageBundle();
                newImage.setBitmap(bitmap);
                newImage.setsFilePath(s);
                myImageBundle.getImages().add(newImage);
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.getStackTrace();
            }
        });

        retropicker = builder2.create();
        retropicker.open();
    }



    @Override
    public void updateView() {

    }
}
