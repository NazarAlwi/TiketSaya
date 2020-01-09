package firstbelajar.digitalsoftware.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyProfileTicketDetailActivity extends AppCompatActivity {
    private DatabaseReference mReference;
    private TextView tvNamaWisata, tvLokasi, tvDate, tvTime, tvKetentuan;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_ticket_detail);

        // Ambil data dari intent
        Bundle bundle = getIntent().getExtras();
        String wisata = bundle.getString("nama_wisata");

        tvNamaWisata = findViewById(R.id.tv_nama_wisata_detail);
        tvLokasi = findViewById(R.id.tv_lokasi_wisata_detail);
        tvDate = findViewById(R.id.tv_tanggal);
        tvTime = findViewById(R.id.tv_time);
        tvKetentuan = findViewById(R.id.tv_ketentuan);
        btnBack = findViewById(R.id.btn_back_my_ticket_detail);

        mReference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(wisata);
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tvNamaWisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                tvLokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                tvDate.setText(dataSnapshot.child("date_wisata").getValue().toString());
                tvTime.setText(dataSnapshot.child("time_wisata").getValue().toString());
                tvKetentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
