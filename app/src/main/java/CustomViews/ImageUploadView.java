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

/**
 * View class for ImageUpload. Handles view related logic.
 */

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

    /**
     * This controls what each button does in the image upload view.
     */
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



    /**
     * This will initialize all or our variables
     */
    public void init(){
        bLeftImg = rootView.findViewById(R.id.upload_bViewLeftImg);
        bDeleteImg = rootView.findViewById(R.id.upload_bDeleteImg);
        bRightImg = rootView.findViewById(R.id.upload_bViewRightImg);
        bUploadImages = rootView.findViewById(R.id.upload_bUploadImages);
        imageView = rootView.findViewById(R.id.upload_imageView);
        progressBar = rootView.findViewById(R.id.upload_progressBar);
        hideProgressBar();
    }

    /**
     * This will show the progress bar to the user. Restores modified UI elements.
     */
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        bUploadImages.setClickable(false);
        bDeleteImg.setClickable(false);
        Toast.makeText(context, "Connecting to server...", Toast.LENGTH_LONG).show();
    }
    /**
     * This will hide the current progress bar from the user. Also modifies UI elements which should be hidden or not intractable to the user.
     */
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        bUploadImages.setClickable(true);
        bDeleteImg.setClickable(true);
    }

    /**
     * This will display the images to the phone that the user has selected
     */
    private void displayImage() {
        Picasso.get().load(new File(imagePaths.get(index))).into(imageView);
    }

    /**
     * If there is no images selected this place holder image will be displayed.
     */
    private void displayPlaceHolder() {
        Picasso.get().load(R.drawable.place_holder_img).into(imageView);
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
     *This will get the number of images currently selected and will also call the display method.  If
     * there is no images to show then it will call the place holder image.
     * @param imagePaths the file path to the image on the mobile device
     */
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
