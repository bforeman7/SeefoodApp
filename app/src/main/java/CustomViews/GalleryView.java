package CustomViews;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import ActivityController.Controllable;
import ImageModel.Image;
import ImageModel.ImageBundle;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import test.hulbert.seefood.R;

public class GalleryView implements ImageBundleView {

    private View rootView;
    private FloatingActionButton bHome, bLeftImg, bRightImg;
    private MaterialRatingBar ratingBar;
    private TextView tvRating;
    private ImageView imageView;
    private int index = 0;
    private Controllable controller;
    private ArrayList<String> imagePaths;

    public GalleryView(Controllable controller, View view) {
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
                if(index+1 < imagePaths.size()) {
                    index++;
                    displayImage();
                }
            }
        });
    }

    public void init(){
        bLeftImg = rootView.findViewById(R.id.gallery_bViewLeftImg);
        bRightImg = rootView.findViewById(R.id.gallery_bViewRightImg);
        bHome = rootView.findViewById(R.id.gallery_bHome);
        ratingBar = rootView.findViewById(R.id.gallery_ratingBar);
        imageView = rootView.findViewById(R.id.gallery_imageView);
        tvRating = rootView.findViewById(R.id.gallery_tvFoodRating);
    }

    private void displayImage() {
        Picasso.get().load(new File(imagePaths.get(index))).into(imageView);
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

    public void bindImages(ArrayList<String> imagePaths) {
        if(!imagePaths.isEmpty()) {
            this.imagePaths = imagePaths;
            index = 0;
            displayImage();
        }
    }

    @Override
    public void bindImageBundle(ImageBundle bundle) {

    }
}
