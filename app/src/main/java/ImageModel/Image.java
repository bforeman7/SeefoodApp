package ImageModel;

import android.graphics.Bitmap;

import java.util.Date;

public class Image {
    private Bitmap bitmap;
    private String dateTaken;
    private String sName;
    private int nConfidenceRating;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getDateTaken() {
        return dateTaken;
    }

    public int getnConfidenceRating() {
        return nConfidenceRating;
    }

    public String getsName() {
        return sName;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }

    public void setnConfidenceRating(int nConfidenceRating) {
        this.nConfidenceRating = nConfidenceRating;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

}
