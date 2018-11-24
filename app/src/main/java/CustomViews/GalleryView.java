package CustomViews;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import ActivityController.Controllable;
import ActivityController.GalleryActivity;
import ImageModel.Image;
import ImageModel.ImageBundle;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import test.hulbert.seefood.R;

public class GalleryView implements ImageBundleView {

    private View rootView;
    private FloatingActionButton bHome, bLeftImg, bRightImg;
    private MaterialRatingBar ratingBar;
    private TextView tvRating, tvName, tvUploaded;
    private ImageView imageView;
    private int index = 0;
    private Controllable controller;
    private ImageBundle imageBundle;

    public GalleryView(final Controllable controller, View view) {
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
                ((GalleryActivity)controller).returnHome();
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
        tvName = rootView.findViewById(R.id.gallery_tvName);
        tvUploaded = rootView.findViewById(R.id.gallery_tvUploadTime);
    }

    private void displayImage() {
        Image image = imageBundle.getImages().get(index);
        Picasso.get().load("http://18.220.189.219/" + image.getsFilePath()).into(imageView);
        tvRating.setText(image.getFirstClassConfidenceRating() +  ", " + image.getSecondtClassConfidenceRating());
        String[] path = image.getsFilePath().split("/");
        tvName.setText(path[1]);
        tvUploaded.setText(image.getDateTaken());
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
