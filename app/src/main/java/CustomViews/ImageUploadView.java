package CustomViews;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import test.hulbert.seefood.R;

public class ImageUploadView implements BaseView {

    private View rootView;
    private Button bLeftImg, bRightImg, bUploadImages;
    private FloatingActionButton bDeleteImg, bBack;
    private ImageView imageView;

    public ImageUploadView(ViewGroup container) {
        this.rootView = container;
        init();

        // move one image to the left in the array
        bLeftImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // move one image to the right in the array
        bRightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    public void init(){
        bLeftImg = rootView.findViewById(R.id.upload_bViewLeftImg);
        bBack = rootView.findViewById(R.id.seefood_bHome);
        bDeleteImg = rootView.findViewById(R.id.upload_bDeleteImg);
        bRightImg = rootView.findViewById(R.id.seefood_bViewRightImg);
        bUploadImages = rootView.findViewById(R.id.upload_bUploadImages);
        imageView = rootView.findViewById(R.id.upload_imageView);
    }

    @Override
    public View getRootView() {
        return rootView;
    }
}
