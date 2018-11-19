package CustomViews;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import ActivityController.MainMenuActivity;
import test.hulbert.seefood.R;

public class MenuView extends RelativeLayout implements BaseView {

    private Button btTakePicture;
    private MainMenuActivity mainMenu;
    private Button btUploadImage;

    public MenuView(Context context){
        super(context);
        init(context);
    }

    @Override
    public View getRootView() {
        return null;
    }



    public void init(Context context){
        View rootView = inflate(context, R.layout.home_final,this);
       btTakePicture = rootView.findViewById(R.id.bTakePhoto);
       btUploadImage = rootView.findViewById(R.id.btUploadImage);
       mainMenu = new MainMenuActivity();
    }

    public void takePictureButton(){

        //mainMenu.takePicture();
    }

    public void uploadPictureButton(){
      //mainMenu.postImage();
    }

}
