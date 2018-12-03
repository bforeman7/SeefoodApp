package ActivityController;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Displays a splash screen for when the application first boots up. Once application is loaded, MenuActivity is displayed.
 */

public class SplashActivity extends AppCompatActivity implements Controllable {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }



    @Override
    public void updateView() {

    }
}
