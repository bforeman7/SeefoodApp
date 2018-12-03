package CustomViews;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import test.hulbert.seefood.R;

/**
 * View class for Main Menu. Handles view related logic.
 */


public class MenuView implements BaseView {
    private View rootView;
    private Button uploadImages, viewGallery;
    private ProgressBar progressBar;
    private Context context;

    public MenuView(ViewGroup container, Context context) {
        this.rootView = container;
        this.context = context;
        init();
    }

    /**
     * Returns rootView of the view.
     * @return View
     */

    @Override
    public View getRootView() {
        return rootView;
    }

    public void init(){
        viewGallery = rootView.findViewById(R.id.menu_bViewGallery);
        uploadImages = rootView.findViewById(R.id.menu_bUploadImages);
        progressBar = rootView.findViewById(R.id.menu_progressBar);
        hideProgressBar();
    }

    /**
     * This will show the progress bar to the user. Restores modified UI elements.
     */
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        uploadImages.setClickable(false);
        uploadImages.setVisibility(View.GONE);
        viewGallery.setClickable(false);
        viewGallery.setVisibility(View.GONE);
        Toast.makeText(context, "Connecting to server...", Toast.LENGTH_LONG).show();

    }

    /**
     * This will hide the current progress bar from the user. Also modifies UI elements which should be hidden or not intractable to the user.
     */
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        uploadImages.setClickable(true);
        uploadImages.setVisibility(View.VISIBLE);
        viewGallery.setClickable(true);
        viewGallery.setVisibility(View.VISIBLE);
    }

}
