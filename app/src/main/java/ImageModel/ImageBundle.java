package ImageModel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

import in.mayanknagwanshi.imagepicker.imageCompression.ImageCompressionListener;
import in.mayanknagwanshi.imagepicker.imagePicker.ImagePicker;

public class ImageBundle {
    private ArrayList<Bitmap> images = new ArrayList<>();
    //private Controllable controller

    public Bitmap getImageByID(int nIDnumber){
        return images.get(nIDnumber);
    }


    public void compressImages(){

    }

//    public JSONObject convertImagesDataToJSON(){
//
//    }


    public ArrayList<Bitmap> getImages() {
        return images;
    }

    /**
     *This will remove an Image the user selects on the UI.
     * This will remove by index
     */
    public void deleteImageByID(int nPosition){
        images.remove(nPosition);
    }
}
