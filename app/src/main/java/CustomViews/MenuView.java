package CustomViews;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import test.hulbert.seefood.R;

public class MenuView extends RelativeLayout implements BaseView {
    private View rootView;
    private Button uploadImages, viewGallery;

    public MenuView(ViewGroup container) {
        this.rootView = container;
        init();
    }

    public void init(){
        viewGallery = rootView.findViewById(R.id.menu_bViewGallery);
        uploadImages = rootView.findViewById(R.id.menu_bUploadImages);
    }


    @Override
    public View getRootView() {
        return rootView;
    }

}
