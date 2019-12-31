package firstbelajar.digitalsoftware.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    Animation appSplash, bottomToTop;
    ImageView appLogo;
    TextView appSubtitle;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        appSplash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        bottomToTop = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
        appLogo = findViewById(R.id.app_logo);
        appSubtitle = findViewById(R.id.app_subtitle);

        appLogo.startAnimation(appSplash);
        appSubtitle.startAnimation(bottomToTop);

        getUsernameLocal();
    }

    private void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");

        if (username_key_new.isEmpty()) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // merubah activity ke activity lain
                    Intent goGetStarted = new Intent(SplashActivity.this, GetStartedActivity.class);
                    startActivity(goGetStarted);
                    finish();
                }
            }, 2000);
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // merubah activity ke activity lain
                    Intent goGetHome = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(goGetHome);
                    finish();
                }
            }, 2000);
        }
    }
}
