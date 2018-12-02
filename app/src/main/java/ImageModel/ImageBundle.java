package ImageModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ImageBundle {
    private ArrayList<Image> images = new ArrayList<>();

    public Image getImageByID(int nIDnumber){

        return images.get(nIDnumber);
    }
    /*
    Used to create an image object that we want to create from a JSON server response
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
        image.setDateTaken(jsonObject.getString("date_taken"));
        image.setUrlPathBool(true);
        image.setRotation(Integer.parseInt(jsonObject.getString("image_orientation")));
        images.add(image);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

}
