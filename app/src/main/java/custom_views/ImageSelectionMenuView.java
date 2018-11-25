package custom_views;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import test.hulbert.seefood.R;

public class ImageSelectionMenuView implements BaseView {

    private View rootView;
    private Button takePicture, selectImage, reviewImages;

    public ImageSelectionMenuView(ViewGroup container) {
        this.rootView = container;
        init();
    }

    public void init(){
        takePicture = rootView.findViewById(R.id.selection_bTakePicture);
        selectImage = rootView.findViewById(R.id.selection_bSelectImage);
        reviewImages = rootView.findViewById(R.id.selection_bReviewImages);
    }

    @Override
    public View getRootView() {
        return rootView;
    }
}
