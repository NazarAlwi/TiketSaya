package firstbelajar.digitalsoftware.tiketsaya;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MyProfileActivity extends AppCompatActivity {
    LinearLayout linearBtnMyTicket;
    Button buttonEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        linearBtnMyTicket = findViewById(R.id.my_ticket);
        buttonEditProfile = findViewById(R.id.btn_edit_profile);

        linearBtnMyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMyTicketDetail = new Intent(MyProfileActivity.this, MyProfileTicketDetailActivity.class);
                startActivity(goToMyTicketDetail);
            }
        });

        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToEditProfile = new Intent(MyProfileActivity.this, EditProfileActivity.class);
                startActivity(goToEditProfile);
            }
        });
    }
}
