package ActivityController;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import CustomViews.BaseView;
import CustomViews.MenuView;
import test.hulbert.seefood.R;

public class MenuActivity extends AppCompatActivity implements Controllable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // we set the initial content view
        setContentView(R.layout.home_final);
        //
        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);
        BaseView menuView = new MenuView(view);
    }

    public void uploadImages(View view) {
        Intent intent = new Intent(this, ImageSelectionMenuActivity.class);
        startActivity(intent);
    }

    public void gotoGallery(View view) {

    }



    @Override
    public void updateView() {

    }
}
