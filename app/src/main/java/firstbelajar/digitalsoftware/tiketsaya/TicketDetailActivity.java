package firstbelajar.digitalsoftware.tiketsaya;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TicketDetailActivity extends AppCompatActivity {

    Button btnBuyTicket;
    TextView tvTitleTicket, tvLocationTicket, tvPhotoSpotTicket, tvWifiTicket, tvFestivalTicket, tvShortDescTicket;
    ImageView imgHeaderTicketDetail;
    LinearLayout btnBack;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        tvTitleTicket = findViewById(R.id.title_ticket);
        tvLocationTicket = findViewById(R.id.location_ticket);
        tvPhotoSpotTicket = findViewById(R.id.photo_spot_ticket);
        tvWifiTicket = findViewById(R.id.wifi_ticket);
        tvFestivalTicket = findViewById(R.id.festival_ticket);
        tvShortDescTicket = findViewById(R.id.short_desc_ticket);
        btnBuyTicket = findViewById(R.id.btn_buy_ticket);
        imgHeaderTicketDetail = findViewById(R.id.img_header_ticket_detail);
        btnBack = findViewById(R.id.btn_back_ticket_detail);

        // Mengambil data dari Intent
        Bundle bundle = getIntent().getExtras();
        final String jenisTiket = bundle.getString("Jenis tiket");

        // Mengambil data dari firebase berdasarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("wisata").child(jenisTiket);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Menimpa dengan data yang baru
                tvTitleTicket.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                tvLocationTicket.setText(dataSnapshot.child("lokasi").getValue().toString());
                tvPhotoSpotTicket.setText(dataSnapshot.child("is_photo_spot").getValue().toString());
                tvWifiTicket.setText(dataSnapshot.child("is_wifi").getValue().toString());
                tvFestivalTicket.setText(dataSnapshot.child("is_festival").getValue().toString());
                tvShortDescTicket.setText(dataSnapshot.child("short_desc").getValue().toString());

                Picasso.with(TicketDetailActivity.this)
                        .load(dataSnapshot.child("url_thumbnail").getValue().toString())
                        .centerCrop()
                        .fit()
                        .into(imgHeaderTicketDetail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCheckout = new Intent(TicketDetailActivity.this, TicketCheckoutActivity.class);
                goToCheckout.putExtra("Jenis Tiket", jenisTiket);
                startActivity(goToCheckout);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
