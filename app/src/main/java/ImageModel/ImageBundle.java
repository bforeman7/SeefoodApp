package ImageModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ImageBundle {
    private ArrayList<Image> images = new ArrayList<>();

    public Image getImageByID(int nIDnumber){

        return images.get(nIDnumber);
    }

    /**
     * Used to create an image object that we want to create from a JSON server response
     * @param jsonObject the object which will be converted to an Image
     */
    public void addImageThroughJSON(JSONObject jsonObject) {
        // Build an Image Object out of a response from the server
        try {
        Image image = new Image();
        String[] path = jsonObject.getString("image_path").split("/");
        image.setId(Integer.parseInt(jsonObject.getString("id")));
        image.setFilePath(jsonObject.getString("image_path"));
        image.setName(path[1]);
        image.setFirstClassConfidenceRating(Double.parseDouble(jsonObject.getString("first_class_confidence")));
        image.setSecondClassConfidenceRating(Double.parseDouble(jsonObject.getString("second_class_confidence")));
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
        Date date =null;
            try {
                date = sdf.parse(jsonObject.getString("date_taken"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MM/dd/yyyy");
        String myDate = simpleDateFormat.format(date);
        image.setDateTaken(myDate);
        image.setUrlPathBool(true);
        image.setRotation(Integer.parseInt(jsonObject.getString("image_orientation")));
        images.add(image);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * Gets the arraylist of images
     * @return ArrayList<Image>
     */
    public ArrayList<Image> getImages() {
        return images;
    }

    /**
     * Gets and Image at a specific array index
     * @param index index to get
     * @return Image
     */
    public Image getSpecificImage(int index) {
        return images.get(index);
    }

    /**
     * Gets the size of the ImageBundle
     * @return size
     */
    public int getSize() {
        return images.size();
    }

    /**
     * Removes an image from the at a specific index from the ImageBundle
     * @param nPosition index to delete at
     */
    public void deleteImageByID(int nPosition){
        images.remove(nPosition);
    }

}
