package firstbelajar.digitalsoftware.tiketsaya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GetStartedAct extends AppCompatActivity {

    Button button_sign_in, btn_new_account_on_getStarted;
    Animation topToBottom, bottomToTop;
    ImageView emblemApp;
    TextView introApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        topToBottom = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom);
        bottomToTop = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);

        emblemApp = findViewById(R.id.emblem_app);
        introApp = findViewById(R.id.intro_app);
        button_sign_in = findViewById(R.id.button_sign_in);
        btn_new_account_on_getStarted= findViewById(R.id.btn_new_account_on_getStarted);

        emblemApp.setAnimation(topToBottom);
        introApp.setAnimation(topToBottom);
        button_sign_in.setAnimation(bottomToTop);
        btn_new_account_on_getStarted.setAnimation(bottomToTop);

        button_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSign = new Intent(GetStartedAct.this, SignInAct.class);
                startActivity(goToSign);
            }
        });
        btn_new_account_on_getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRegisterOne = new Intent(GetStartedAct.this, RegisterOneAct.class);
                startActivity(goToRegisterOne);
            }
        });
    }
}
