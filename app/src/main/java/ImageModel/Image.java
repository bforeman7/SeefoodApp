package ImageModel;


public class Image {
    private String dateTaken;
    private String sName;
    private double firstClassConfidenceRating;
    private double secondClassConfidenceRating;
    private String sFilePath;
    private int id;
    private boolean urlPathBool;
    private int rotation;

    public String getDateTaken() {
        return dateTaken;
    }

    public double getFirstClassConfidenceRating() {
        return firstClassConfidenceRating;
    }

    public double getSecondClassConfidenceRating() {
        return secondClassConfidenceRating;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return sName;
    }

    public int getRotation() {
        return rotation;
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

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    /**
     * Returns a 0-5 integer start rating based off the confidence ratings.
     * @return star rating
     */
    public int calculateStars() {
        // calculate differential between confidence ratings
        int rating = 1;
        // calculateDifferential returns a 0 for less than differential threshold and 1 for greater than differential threshold
        // We add that return value to the lower bound of the possible star ratings for each range of food values
        double threshold = .5;

        // AI is sure that it is food, 4 to 5 star rating based off differential
        if(firstClassConfidenceRating > 0 && secondClassConfidenceRating < 0) {
            rating = calculateDifferential(firstClassConfidenceRating, secondClassConfidenceRating, threshold) + 4;
        }
        // AI is less sure that it is food, but still thinks it is as long as first class is greater than second class, 2 to 3 star
        else if(firstClassConfidenceRating > 0 && secondClassConfidenceRating >= 0 && firstClassConfidenceRating > secondClassConfidenceRating) {
            rating = calculateDifferential(firstClassConfidenceRating, secondClassConfidenceRating, threshold) + 2;
        }
        // AI is less sure that is isn't food, but still thinks it isn't food as long as first class is less than second class, 1 to 2 star
        else if(firstClassConfidenceRating > 0 && secondClassConfidenceRating > 0 && firstClassConfidenceRating < secondClassConfidenceRating) {
            rating = calculateDifferential(firstClassConfidenceRating, secondClassConfidenceRating, threshold) + 1;
        }
        // AT is for sure it doesn't see food by indicating negative first class, 1 to 0 star
        else if(firstClassConfidenceRating < 0 && secondClassConfidenceRating > 0){
            rating = calculateDifferential(firstClassConfidenceRating, secondClassConfidenceRating, threshold) + 0;
        }
        return rating;
    }

    /**
     * Calcuates the differential between numbers x and y. Will return a 0 if the deferential is less than or equal to the threshold and a 1 if it is greater than the threshold.
     * @param x first number
     * @param y second number
     * @param threshold threshold
     * @return 0 if the deferential is less than or equal to the threshold and a 1 if it is greater than the threshold.
     */
    private int calculateDifferential(double x, double y, double threshold) {
        double delta;
        if(x > y) {
            delta = x - y;
        }
        else {
            delta = y - x;
        }

        if(delta > threshold) {
            return 1;
        }
        else {
            return 0;
        }
    }

}
