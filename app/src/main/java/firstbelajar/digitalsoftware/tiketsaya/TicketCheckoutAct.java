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

public class TicketCheckoutAct extends AppCompatActivity {

    Button btnBuyTicket, btnMinus, btnPlus;
    TextView ticket_count, tvTotalHarga, tvMyBalance;
    Animation shake;
    ImageView imgWarning;
    Integer count = 1;
    Integer myBalance = 200;
    Integer totalHarga = 0;
    Integer hargaTicket = 75;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        btnMinus = findViewById(R.id.btn_minus);
        btnPlus = findViewById(R.id.btn_plus);
        ticket_count = findViewById(R.id.ticket_count);
        btnBuyTicket = findViewById(R.id.btn_buy_ticket);
        tvTotalHarga = findViewById(R.id.tv_total_harga);
        tvMyBalance = findViewById(R.id.tv_my_balance);
        imgWarning = findViewById(R.id.img_warning);
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);

        ticket_count.setText(count.toString());
        tvMyBalance.setText("US$ " + myBalance + "");
        totalHarga = hargaTicket * count;
        tvTotalHarga.setText("US$ " + totalHarga + "");

        imgWarning.setVisibility(View.GONE);
        btnMinus.setAlpha(0f);
        btnMinus.setEnabled(false);

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count + 1;
                ticket_count.setText(count.toString());

                if (count > 1) {
                    btnMinus.animate().alpha(1).setDuration(300).start();
                    btnMinus.setEnabled(true);
                }

                totalHarga = hargaTicket * count;
                tvTotalHarga.setText("US$ " + totalHarga + "");

                if (myBalance < totalHarga) {
                    tvMyBalance.setTextColor(getResources().getColor(R.color.redPrimary));
                    imgWarning.setVisibility(View.VISIBLE);
                    imgWarning.startAnimation(shake);

                    btnBuyTicket.animate().translationY(250).alpha(0).setDuration(300).start();
                    btnBuyTicket.setEnabled(false);
                }
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count - 1;
                ticket_count.setText(count.toString());

                if (count < 2) {
                    btnMinus.animate().alpha(0).setDuration(300).start();
                    btnMinus.setEnabled(false);
                }

                totalHarga = hargaTicket * count;
                tvTotalHarga.setText("US$ " + totalHarga + "");

                if (myBalance > totalHarga) {
                    tvMyBalance.setTextColor(getResources().getColor(R.color.bluePrimary));
                    imgWarning.setVisibility(View.GONE);

                    btnBuyTicket.animate().translationY(0).alpha(1).setDuration(300).start();
                    btnBuyTicket.setEnabled(true);
                }
            }
        });

        btnBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSuccessBuy = new Intent(TicketCheckoutAct.this, SuccessBuyTicketAct.class);
                startActivity(goToSuccessBuy);
            }
        });
    }
}
