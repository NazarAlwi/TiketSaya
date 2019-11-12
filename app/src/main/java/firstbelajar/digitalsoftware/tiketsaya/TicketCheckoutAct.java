package firstbelajar.digitalsoftware.tiketsaya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TicketCheckoutAct extends AppCompatActivity {

    Button btnBuyTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        btnBuyTicket = findViewById(R.id.btn_buy_ticket);
        btnBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSuccessBuy = new Intent(TicketCheckoutAct.this, SuccessBuyTicketAct.class);
                startActivity(goToSuccessBuy);
            }
        });
    }
}
