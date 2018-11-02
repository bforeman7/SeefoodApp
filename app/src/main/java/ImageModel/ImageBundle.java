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


    public void compressImages(Intent data,ImagePicker imagePicker){
        //imagePicker.withCompression(true);
        //imagePicker.start();
        imagePicker.addOnCompressListener(new ImageCompressionListener() {
            @Override
            public void onStart() {
               // picker.withCompression(true);
            }

            @Override
            public void onCompressed(String filePath) {//filePath of the compressed image
                //convert to bitmap easily
                Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
                images.add(selectedImage);

                //iImageView.setImageBitmap(selectedImage);
                // we need exception handling here if the image is not selected for camera!
            }
        });
        try {
            String filePath = imagePicker.getImageFilePath(data);
            if (filePath != null) {//filePath will return null if compression is set to true
                Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
            }
        }catch (Exception e){}
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
