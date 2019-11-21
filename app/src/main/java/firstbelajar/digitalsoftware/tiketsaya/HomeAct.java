package firstbelajar.digitalsoftware.tiketsaya;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.shapeofview.shapes.CircleView;

public class HomeAct extends AppCompatActivity {

    LinearLayout btnTicketPisa;
    ImageView imgPhotoHomeUser;
    CircleView btnToProfile;
    TextView tvNameHomeUser, tvBioHomeUser, tvBalanceHomeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnTicketPisa = findViewById(R.id.btn_ticket_pisa);
        imgPhotoHomeUser = findViewById(R.id.img_photo_home_user);
        tvNameHomeUser = findViewById(R.id.tv_name_home_user);
        tvBioHomeUser = findViewById(R.id.tv_bio_home_user);
        tvBalanceHomeUser = findViewById(R.id.tv_balance_home_user);
        btnToProfile = findViewById(R.id.btn_to_profile);

        btnTicketPisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToDetailTicket = new Intent(HomeAct.this, TicketDetailAct.class);
                startActivity(goToDetailTicket);
            }
        });

        btnToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfile = new Intent(HomeAct.this, MyProfileAct.class);
                startActivity(goToProfile);
            }
        });
    }
}
