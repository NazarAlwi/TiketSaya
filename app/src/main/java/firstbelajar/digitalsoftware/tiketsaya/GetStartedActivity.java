package firstbelajar.digitalsoftware.tiketsaya;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GetStartedActivity extends AppCompatActivity {

    Button buttonSignIn, buttonNewAccountOnGetStarted;
    Animation topToBottom, bottomToTop;
    ImageView imageEmblemApp;
    TextView textIntroApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        topToBottom = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom);
        bottomToTop = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);

        imageEmblemApp = findViewById(R.id.emblem_app);
        textIntroApp = findViewById(R.id.intro_app);
        buttonSignIn = findViewById(R.id.button_sign_in);
        buttonNewAccountOnGetStarted= findViewById(R.id.btn_new_account_on_getStarted);

        imageEmblemApp.startAnimation(topToBottom);
        textIntroApp.startAnimation(topToBottom);
        buttonSignIn.startAnimation(bottomToTop);
        buttonNewAccountOnGetStarted.startAnimation(bottomToTop);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSign = new Intent(GetStartedActivity.this, SignInActivity.class);
                startActivity(goToSign);
            }
        });
        buttonNewAccountOnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRegisterOne = new Intent(GetStartedActivity.this, RegisterOneActivity.class);
                startActivity(goToRegisterOne);
            }
        });
    }
}
