package firstbelajar.digitalsoftware.tiketsaya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessBuyTicketAct extends AppCompatActivity {
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
    }
}
