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
    private JSONArray jsonObjects;

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
                if(index+1 < jsonObjects.length()) {
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
        tvName = rootView.findViewById(R.id.gallery_tvName);
        tvUploaded = rootView.findViewById(R.id.gallery_tvUploadTime);
    }

    private void displayImage() {
        try {
            JSONObject object = jsonObjects.getJSONObject(index);
            Picasso.get().load("http://18.220.189.219/" + object.getString("image_path")).into(imageView);
            tvRating.setText(object.getString("first_class_confidence") +  " " + object.getString("second_class_confidence"));
            String[] path = object.getString("image_path").split("/");
            tvName.setText(path[1]);
            tvUploaded.setText(object.getString("date_taken"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public void bindImages(JSONArray jsonObjects) {
        if(jsonObjects.length() > 0) {
            this.jsonObjects = jsonObjects;
            index = 0;
            displayImage();
        }
    }

    @Override
    public void bindImageBundle(ImageBundle bundle) {

    }
}
