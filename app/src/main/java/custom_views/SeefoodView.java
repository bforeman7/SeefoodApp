package custom_views;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import activity_controller.Controllable;
import activity_controller.SeefoodActivity;
import image_model.Image;
import image_model.ImageBundle;
//import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import test.hulbert.seefood.R;

public class SeefoodView implements ImageBundleView {

    private View rootView;
    private FloatingActionButton bHome, bLeftImg, bRightImg;
//    private MaterialRatingBar ratingBar;
    private TextView tvRating;
    private ImageView imageView;
    private int index = 0;
    private Controllable controller;
    private ImageBundle imageBundle;


    public SeefoodView(final Controllable controller, View view) {
        this.controller = controller;
        this.rootView = view;
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
                if(index+1 < imageBundle.getImages().size()) {
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
//        ratingBar = rootView.findViewById(R.id.seefood_ratingBar);
        imageView = rootView.findViewById(R.id.seefood_imageView);
        tvRating = rootView.findViewById(R.id.seefood_tvFoodRating);
    }

    private void displayImage() {
        Image image = imageBundle.getImages().get(index);
        Picasso.get().load("http://18.220.189.219/" + image.getsFilePath()).into(imageView);
        String message = image.getFirstClassConfidenceRating() +  ", " + image.getSecondtClassConfidenceRating();
        int rating = image.calculateStars();
        tvRating.setText(getFoodDialog(rating));
//        ratingBar.setRating(image.calculateStars());
    }

    private void displayPlaceHolder() {
        Picasso.get().load("https://cdn-images-1.medium.com/max/1600/0*-ouKIOsDCzVCTjK-.png").into(imageView);
    }

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

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void bindImageBundle(ImageBundle bundle) {
        if(bundle.getImages().size() > 0) {
            this.imageBundle = bundle;
            index = 0;
            displayImage();
        }
    }
}
