package firstbelajar.digitalsoftware.tiketsaya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.shapeofview.shapes.CircleView;

public class HomeActivity extends AppCompatActivity {

    LinearLayout linearBtnTicketPisa;
    ImageView imagePhotoHomeUser;
    CircleView buttonToProfile;
    TextView textNameHomeUser, textBioHomeUser, textBalanceHomeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        linearBtnTicketPisa = findViewById(R.id.btn_ticket_pisa);
        imagePhotoHomeUser = findViewById(R.id.img_photo_home_user);
        textNameHomeUser = findViewById(R.id.tv_name_home_user);
        textBioHomeUser = findViewById(R.id.tv_bio_home_user);
        textBalanceHomeUser = findViewById(R.id.tv_balance_home_user);
        buttonToProfile = findViewById(R.id.btn_to_profile);

        linearBtnTicketPisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToDetailTicket = new Intent(HomeActivity.this, TicketDetailActivity.class);
                startActivity(goToDetailTicket);
            }
        });

        buttonToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfile = new Intent(HomeActivity.this, MyProfileActivity.class);
                startActivity(goToProfile);
            }
        });
    }
}
