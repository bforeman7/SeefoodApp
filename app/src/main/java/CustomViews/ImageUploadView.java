package CustomViews;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

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
    private ArrayList<String> imagePaths;
    private Controllable controller;
    private int index = 0;
    private int numOfImages;

    public ImageUploadView(final Controllable controller, ViewGroup container) {
        this.rootView = container;
        this.controller = controller;
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
            }
        });


    }

    public void init(){
        bLeftImg = rootView.findViewById(R.id.upload_bViewLeftImg);
        bDeleteImg = rootView.findViewById(R.id.upload_bDeleteImg);
        bRightImg = rootView.findViewById(R.id.upload_bViewRightImg);
        bUploadImages = rootView.findViewById(R.id.upload_bUploadImages);
        imageView = rootView.findViewById(R.id.upload_imageView);
    }

    private void displayImage() {
        Picasso.get().load(new File(imagePaths.get(index))).into(imageView);
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
            imageView.setImageBitmap(null);
            numOfImages = 0;
        }

        numOfImages = imagePaths.size();
    }
}
