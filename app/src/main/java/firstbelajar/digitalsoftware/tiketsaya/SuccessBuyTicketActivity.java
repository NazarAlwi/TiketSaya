package firstbelajar.digitalsoftware.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessBuyTicketActivity extends AppCompatActivity {
    ImageView imgSuccessBuy;
    TextView tvTitleSuccessBuy, tvSubtitleSuccessBuy;
    Button btnViewTickets, btnMyDashboard;
    Animation appSplash, bottomToTop, topToBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy_ticket);

        imgSuccessBuy = findViewById(R.id.img_success_buy);
        tvTitleSuccessBuy = findViewById(R.id.tv_title_success_buy);
        tvSubtitleSuccessBuy = findViewById(R.id.tv_subtitle_success_buy);
        btnViewTickets = findViewById(R.id.btn_view_tickets);
        btnMyDashboard = findViewById(R.id.btn_my_dashboard);

        appSplash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        bottomToTop = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
        topToBottom = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom);

        imgSuccessBuy.startAnimation(appSplash);
        tvTitleSuccessBuy.startAnimation(topToBottom);
        tvSubtitleSuccessBuy.startAnimation(topToBottom);
        btnViewTickets.startAnimation(bottomToTop);
        btnMyDashboard.startAnimation(bottomToTop);

        btnViewTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfile = new Intent(SuccessBuyTicketActivity.this, MyProfileActivity.class);
                startActivity(goToProfile);
            }
        });

        btnMyDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToDashboard = new Intent(SuccessBuyTicketActivity.this, HomeActivity.class);
                startActivity(goToDashboard);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
