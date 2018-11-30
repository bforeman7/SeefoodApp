package CustomViews;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import ActivityController.Controllable;
import ActivityController.ImageUploadActivity;
import test.hulbert.seefood.R;

public class ImageUploadView implements BaseView {

    private View rootView;
    private Button bUploadImages;
    private FloatingActionButton bDeleteImg, bLeftImg, bRightImg;
    private ImageView imageView;
    private ProgressBar progressBar;
    private ArrayList<String> imagePaths;
    private Controllable controller;
    private int index = 0;
    private int numOfImages;
    private Context context;

    public ImageUploadView(final Controllable controller, final ViewGroup container, Context controllerContext) {
        this.rootView = container;
        this.controller = controller;
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

        bDeleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numOfImages > 0) {
                    if (index == 0) {
                        ((ImageUploadActivity) controller).deleteImage(index);
                    } else {
                        ((ImageUploadActivity) controller).deleteImage(index);
                        index = 0;
                    }
                }
                else {
                    Toast.makeText(context, "No images left to delete", Toast.LENGTH_LONG).show();
                }
            }
        });


    }



    public void init(){
        bLeftImg = rootView.findViewById(R.id.upload_bViewLeftImg);
        bDeleteImg = rootView.findViewById(R.id.upload_bDeleteImg);
        bRightImg = rootView.findViewById(R.id.upload_bViewRightImg);
        bUploadImages = rootView.findViewById(R.id.upload_bUploadImages);
        imageView = rootView.findViewById(R.id.upload_imageView);
        progressBar = rootView.findViewById(R.id.upload_progressBar);
        hideProgressBar();
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        bUploadImages.setClickable(false);
        bDeleteImg.setClickable(false);
        Toast.makeText(context, "Connecting to server...", Toast.LENGTH_LONG).show();
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        bUploadImages.setClickable(true);
        bDeleteImg.setClickable(true);
    }

    private void displayImage() {
        Picasso.get().load(new File(imagePaths.get(index))).into(imageView);
    }

    private void displayPlaceHolder() {
        Picasso.get().load("https://cdn-images-1.medium.com/max/1600/0*-ouKIOsDCzVCTjK-.png").into(imageView);
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    public void bindImages(ArrayList<String> imagePaths) {
        if(!imagePaths.isEmpty()) {
            this.imagePaths = imagePaths;
            index = 0;
            numOfImages = imagePaths.size();
            displayImage();
        }
        else {
            displayPlaceHolder();
            numOfImages = 0;
        }

        numOfImages = imagePaths.size();
    }
}
