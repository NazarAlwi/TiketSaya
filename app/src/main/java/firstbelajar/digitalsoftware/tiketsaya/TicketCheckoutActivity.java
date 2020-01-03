package firstbelajar.digitalsoftware.tiketsaya;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class TicketCheckoutActivity extends AppCompatActivity {
    public static final String USERNAME_KEY = "username_key";
    private String mUsernameKey = "";
    String new_username_key = "";

    Button btnBuyTicket, btnMinus, btnPlus;
    TextView ticket_count, tvTotalHarga, tvMyBalance, tvNamaWisata, tvLokasi, tvKetentuan;
    Animation shake;
    ImageView imgWarning;
    Integer count = 1;
    Integer myBalance;
    Integer totalHarga = 0;
    Integer hargaTicket;

    Integer nomorTransaksi = new Random().nextInt();
    Integer sisaBalance;

    String dateWisata;
    String timeWisata;

    DatabaseReference wisataReference, userReference, userTicketPaymentReference, userBalanceReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        getUsernameLocal();

        // Mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenisTiket = bundle.getString("Jenis Tiket");

        btnMinus = findViewById(R.id.btn_minus);
        btnPlus = findViewById(R.id.btn_plus);
        ticket_count = findViewById(R.id.ticket_count);
        btnBuyTicket = findViewById(R.id.btn_buy_ticket);
        tvTotalHarga = findViewById(R.id.tv_total_count);
        tvMyBalance = findViewById(R.id.tv_my_balance);
        imgWarning = findViewById(R.id.img_warning);
        tvNamaWisata = findViewById(R.id.tv_nama_wisata);
        tvLokasi = findViewById(R.id.tv_lokasi_wisata);
        tvKetentuan = findViewById(R.id.tv_short_tos_ticket);
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);

        // Mengambil data user dari firebase
        userReference = FirebaseDatabase.getInstance().getReference().child("Users").child(new_username_key);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myBalance = Integer.valueOf(dataSnapshot.child("balance").getValue().toString()) ;
                tvMyBalance.setText(String.format(getResources().getString(R.string.dolar), String.valueOf(myBalance)));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Mengambil data wisata dari firebase
        wisataReference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenisTiket);
        wisataReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Menimpa dengan data yang baru
                tvNamaWisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                tvLokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                tvKetentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());
                hargaTicket = Integer.valueOf(dataSnapshot.child("harga_tiket").getValue().toString());
                totalHarga = hargaTicket * count;
                tvTotalHarga.setText(String.format(getResources().getString(R.string.dolar), String.valueOf(totalHarga)));

                dateWisata = dataSnapshot.child("date_wisata").getValue().toString();
                timeWisata = dataSnapshot.child("time_wisata").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ticket_count.setText(count.toString());

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
                tvTotalHarga.setText(String.format(getResources().getString(R.string.dolar), String.valueOf(totalHarga)));

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
                tvTotalHarga.setText(String.format(getResources().getString(R.string.dolar), String.valueOf(totalHarga)));

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
                // Menyimpan data user ke firebase dan membuat table baru "MyTickets"
                userTicketPaymentReference = FirebaseDatabase.getInstance().getReference().child("MyTickets").child(new_username_key).child(tvNamaWisata.getText().toString() + nomorTransaksi);
                userTicketPaymentReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        userTicketPaymentReference.getRef().child("id_tiket").setValue(nomorTransaksi);
                        userTicketPaymentReference.getRef().child("nama_wisata").setValue(tvNamaWisata.getText().toString());
                        userTicketPaymentReference.getRef().child("lokasi").setValue(tvLokasi.getText().toString());
                        userTicketPaymentReference.getRef().child("ketentuan").setValue(tvKetentuan.getText().toString());
                        userTicketPaymentReference.getRef().child("jumlah_tiket").setValue(count.toString());

                        userTicketPaymentReference.getRef().child("date_wisata").setValue(dateWisata);
                        userTicketPaymentReference.getRef().child("time_wisata").setValue(timeWisata);

                        Intent goToSuccessBuy = new Intent(TicketCheckoutActivity.this, SuccessBuyTicketActivity.class);
                        startActivity(goToSuccessBuy);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                // Update data balance user yang saat ini login
                // Mengambil data user dari firebase
                userBalanceReference = FirebaseDatabase.getInstance().getReference().child("Users").child(new_username_key);
                userBalanceReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisaBalance = myBalance - totalHarga;
                        userBalanceReference.getRef().child("balance").setValue(sisaBalance);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    private void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        new_username_key = sharedPreferences.getString(mUsernameKey, "");
    }
}
