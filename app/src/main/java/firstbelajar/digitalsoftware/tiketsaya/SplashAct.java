package firstbelajar.digitalsoftware.tiketsaya;

import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashAct extends AppCompatActivity {
    Animation appSplash, bottomToTop;
    ImageView appLogo;
    TextView appSubtitle;

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

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // merubah activity ke activity lain
                Intent goGetStarted = new Intent(SplashAct.this, GetStartedAct.class);
                startActivity(goGetStarted);
                finish();
            }
        }, 2000);
    }
}
