package firstbelajar.digitalsoftware.tiketsaya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MyProfileAct extends AppCompatActivity {
    private LinearLayout myTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        myTicket = findViewById(R.id.my_ticket);

        myTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMyTicketDetail = new Intent(MyProfileAct.this, MyProfileTicketDetailAct.class);
                startActivity(goToMyTicketDetail);
            }
        });
    }
}
