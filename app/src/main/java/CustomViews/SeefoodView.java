package CustomViews;

import android.support.design.widget.FloatingActionButton;
import android.util.JsonReader;
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
    private ArrayList<String> imagePaths;
    private ArrayList<JSONObject> jsonArray;

    public SeefoodView(Controllable controller, View view) {
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
        bLeftImg = rootView.findViewById(R.id.seefood_bViewLeftImg);
        bRightImg = rootView.findViewById(R.id.seefood_bViewRightImg);
        bHome = rootView.findViewById(R.id.seefood_bHome);
        ratingBar = rootView.findViewById(R.id.seefood_ratingBar);
        imageView = rootView.findViewById(R.id.seefood_imageView);
        tvRating = rootView.findViewById(R.id.seefood_tvFoodRating);
    }

    private void displayImage() {

        Picasso.get().load(new File(imagePaths.get(index))).into(imageView);
        try {
            JSONObject object = jsonArray.get(index).getJSONObject("image");
            String message = object.getString("first_class_confidence") +  ", " + object.getString("second_class_confidence");
            tvRating.setText(message);
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

    public void bindImages(ArrayList<String> imagePaths, ArrayList<JSONObject> jsonArray) {
        if(!imagePaths.isEmpty() && jsonArray.size() > 0) {
            this.imagePaths = imagePaths;
            this.jsonArray = jsonArray;
            index = 0;
            displayImage();
        }
    }

    @Override
    public void bindImageBundle(ImageBundle bundle) {

    }
}
