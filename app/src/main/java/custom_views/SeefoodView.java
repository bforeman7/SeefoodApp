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
        tvRating.setText(message);
    }

    public void updateConfidenceRating(String confidence) {
        tvRating.setText(confidence);
    }

    @Override
    public View getRootView() {
        return rootView;
    }


    public  void displayConfidenceRating(int nRating){

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
