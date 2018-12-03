package CustomViews;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import test.hulbert.seefood.R;

/**
 * View class for ImageSelectionMenu. Handles view related logic.
 */

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

    /**
     * Returns rootView of the view.
     * @return View
     */

    @Override
    public View getRootView() {
        return rootView;
    }
}
