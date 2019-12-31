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

public class SuccessRegisterActivity extends AppCompatActivity {
    Button btn_explore;
    Animation appSplash, bottomToTop, topToBottom;
    ImageView iconSuccess;
    TextView title, subTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);

        btn_explore = findViewById(R.id.btn_explore);
        iconSuccess = findViewById(R.id.icon_success);
        title = findViewById(R.id.title);
        subTitle = findViewById(R.id.sub_title);

        appSplash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        bottomToTop = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
        topToBottom = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom);

        iconSuccess.startAnimation(appSplash);
        title.startAnimation(topToBottom);
        subTitle.startAnimation(topToBottom);
        btn_explore.startAnimation(bottomToTop);

        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHome = new Intent(SuccessRegisterActivity.this, HomeActivity.class);
                startActivity(goToHome);
            }
        });
    }
}
