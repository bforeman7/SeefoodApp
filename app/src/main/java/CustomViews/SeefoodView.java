package CustomViews;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ActivityController.Controllable;
import ActivityController.SeefoodActivity;
import Communication.Endpoints;
import ImageModel.Image;
import ImageModel.ImageBundle;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import test.hulbert.seefood.R;

public class SeefoodView implements ImageBundleView {

    private View rootView;
    private FloatingActionButton bHome, bLeftImg, bRightImg;
    private MaterialRatingBar ratingBar;
    private TextView tvRating;
    private ImageView imageView;
    private int index = 0;
    private Controllable controller;
    private ImageBundle imageBundle;
    private Context context;


    public SeefoodView(final Controllable controller, Context context, View view) {
        this.controller = controller;
        this.rootView = view;
        this.context = context;
        init();

        // move one image to the left in the array
        bLeftImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index - 1 >= 0 ) {
                    index--;
                    displayImage();
                }
            }
        });

        // move one image to the right in the array
        bRightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index+1 < imageBundle.getSize()) {
                    index++;
                    displayImage();
                }
            }
        });
        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SeefoodActivity)controller).returnHome();
            }
        });
    }

    public void init(){
        bLeftImg = rootView.findViewById(R.id.seefood_bViewLeftImg);
        bRightImg = rootView.findViewById(R.id.seefood_bViewRightImg);
        bHome = rootView.findViewById(R.id.seefood_bHome);
        ratingBar = rootView.findViewById(R.id.seefood_ratingBar);
        imageView = rootView.findViewById(R.id.seefood_imageView);
        tvRating = rootView.findViewById(R.id.seefood_tvFoodRating);
    }

    /**
     * This will display each image to the image view for the user to see the image that is out on the server
     */

    private void displayImage() {
        Image image = imageBundle.getSpecificImage(index);
        Picasso.get()
                .load(Endpoints.getServerURL() + image.getsFilePath())
                .rotate(image.getRotation())
                .into(imageView);
        String message = image.getFirstClassConfidenceRating() +  ", " + image.getSecondClassConfidenceRating();
        int rating = image.calculateStars();
        tvRating.setText(getFoodDialog(rating));
        ratingBar.setRating(image.calculateStars());
    }


    /**
     * This will control our dialog to the user if the server sees food or not.  It will be based on the star rating
     */
    private String getFoodDialog(int starRating) {
        String dialog = "";
        if(starRating == 5) {
            dialog = "I definitely see food!";
        }
        else if(starRating == 4) {
            dialog = "I am pretty sure I see food!";
        }
        else if(starRating == 3) {
            dialog = "I maybe see food!";
        }
        else if(starRating == 2) {
            dialog = "I don't think I see food!";
        }
        else if(starRating == 1) {
            dialog = "I am pretty sure I don't see food!";
        }
        else if(starRating == 0) {
            dialog = "I definitely don't see food!";
        }
        return dialog;
    }

    /**
     * Returns rootView of the view.
     * @return View
     */

    @Override
    public View getRootView() {
        return rootView;
    }

    /**
     * Binds a bundle of images to the view
     * @param bundle the ImageBundle
     */
    @Override
    public void bindImageBundle(ImageBundle bundle) {
        if(bundle.getSize() > 0) {
            this.imageBundle = bundle;
            index = 0;
            displayImage();
        }
    }
}
