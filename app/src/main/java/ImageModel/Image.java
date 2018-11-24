package ImageModel;

import android.graphics.Bitmap;

public class Image {
    private Bitmap bitmap;
    private String dateTaken;
    private String sName;
    private double firstClassConfidenceRating;
    private double secondClassConfidenceRating;
    private String sFilePath;
    private int id;
    private boolean urlPathBool;


    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getDateTaken() {
        return dateTaken;
    }

    public double getFirstClassConfidenceRating() {
        return firstClassConfidenceRating;
    }

    public double getSecondtClassConfidenceRating() {
        return secondClassConfidenceRating;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return sName;
    }

    public boolean hasURLPath() {
        return urlPathBool;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }

    public void setFirstClassConfidenceRating(double firstClassConfidenceRating) {
        this.firstClassConfidenceRating = firstClassConfidenceRating;
    }

    public void setSecondClassConfidenceRating(double secondClassConfidenceRating) {
        this.secondClassConfidenceRating = secondClassConfidenceRating;
    }


    public void setName(String sName) {
        this.sName = sName;
    }

    public String getsFilePath() {
        return sFilePath;
    }

    public void setFilePath(String sFilePath) {
        this.sFilePath = sFilePath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrlPathBool(boolean pathBool) {
        this.urlPathBool = pathBool;
    }
}
