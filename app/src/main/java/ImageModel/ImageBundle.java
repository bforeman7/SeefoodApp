package ImageModel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;


public class ImageBundle {
    private ArrayList<Image> images = new ArrayList<>();
    //private Controllable controller

    public Image getImageByID(int nIDnumber){

        return images.get(nIDnumber);
    }

    public void addImage(Image image) {
        images.add(image);
    }


    public ArrayList<Image> getImages() {
        return images;
    }

    /**
     *This will remove an Image the user selects on the UI.
     * This will remove by index
     */
    public void deleteImageByID(int nPosition){
        images.remove(nPosition);
    }

    public ArrayList<String> getImagePathsToString() {
        ArrayList<String> URIs = new ArrayList<String>();
        for(int i = 0; i < images.size(); i++)
        {
            URIs.add(images.get(i).getsFilePath());
        }
        return URIs;
    }

}
