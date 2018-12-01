package CustomViews;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
    private MaterialRatingBar ratingBar;
    private FloatingActionButton bHome, bLeftImg, bRightImg, bDeleteImg;
    private TextView tvRating, tvName, tvUploaded;
    private ImageView imageView;
    private int index = 0;
    private int numOfImages;
    private Controllable controller;
    private ImageBundle imageBundle;
    private Context context;

    public GalleryView(final Controllable controller, Context controllerContext, View view) {
        this.controller = controller;
        this.rootView = view;
        this.context = controllerContext;
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
                if(index+1 < numOfImages) {
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

        // delete current image based off index
        bDeleteImg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                if(numOfImages > 0) {
                    try {
                        // send request to delete image
                        Image imageToDelete = imageBundle.getImageByID(index);
                        int id = imageToDelete.getId();

                        // adjust image bundle accordingly
                        if (index == 0){
                            ((GalleryActivity) controller).deleteImage(id, index);
                        } else {
                            ((GalleryActivity) controller).deleteImage(id, index);
                            index = 0;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(context, "No images left to delete", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void init(){
        bLeftImg = rootView.findViewById(R.id.gallery_bViewLeftImg);
        bRightImg = rootView.findViewById(R.id.gallery_bViewRightImg);
        bHome = rootView.findViewById(R.id.gallery_bHome);
        ratingBar = rootView.findViewById(R.id.gallery_ratingBar);
        bDeleteImg = rootView.findViewById(R.id.gallery_bDeleteImg);
        imageView = rootView.findViewById(R.id.gallery_imageView);
        tvRating = rootView.findViewById(R.id.gallery_tvFoodRating);
        tvName = rootView.findViewById(R.id.gallery_tvName);
        tvUploaded = rootView.findViewById(R.id.gallery_tvUploadTime);
    }

    private void displayImage() {
        Image image = imageBundle.getImages().get(index);
        Picasso.get()
                .load("http://18.220.189.219/" + image.getsFilePath())
                .rotate(image.getRotation())
                .into(imageView);
        String[] path = image.getsFilePath().split("/");
        tvName.setText(path[1]);
        tvUploaded.setText(image.getDateTaken());
        int rating = image.calculateStars();
        tvRating.setText(getFoodDialog(rating));
        ratingBar.setRating(image.calculateStars());
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

    private void displayPlaceHolder() {
        Picasso.get().load("https://cdn-images-1.medium.com/max/1600/0*-ouKIOsDCzVCTjK-.png").into(imageView);
        tvName.setText("");
        tvUploaded.setText("");
        tvRating.setText("");

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
            numOfImages = bundle.getImages().size();
        } else {
            displayPlaceHolder();
            numOfImages = 0;
        }
    }
}
