package firstbelajar.digitalsoftware.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {
    public static final String USERNAME_KEY = "username_key";
    private String mUsernameKey = "";
    private String new_username_key = "";

    LinearLayout linearBtnTicketPisa, linearBtnTicketTorri, linearBtnTicketPagoda, linearBtnTicketCandi, linearBtnTicketSphinx, linearBtnTicketMonas;
    ImageView imagePhotoHomeUser;
    CircleView buttonToProfile;
    TextView textNameHomeUser, textBioHomeUser, textBalanceHomeUser;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getUsernameLocal();

        linearBtnTicketPisa = findViewById(R.id.btn_ticket_pisa);
        linearBtnTicketTorri = findViewById(R.id.btn_ticket_torri);
        linearBtnTicketPagoda = findViewById(R.id.btn_ticket_pagoda);
        linearBtnTicketCandi = findViewById(R.id.btn_ticket_candi);
        linearBtnTicketSphinx = findViewById(R.id.btn_ticket_sphinx);
        linearBtnTicketMonas = findViewById(R.id.btn_ticket_monaas);
        imagePhotoHomeUser = findViewById(R.id.img_photo_home_user);
        textNameHomeUser = findViewById(R.id.tv_name_home_user);
        textBioHomeUser = findViewById(R.id.tv_bio_home_user);
        textBalanceHomeUser = findViewById(R.id.tv_balance_home_user);
        buttonToProfile = findViewById(R.id.btn_to_profile);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(new_username_key);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String balance = String.format(getResources().getString(R.string.dolar), dataSnapshot.child("balance").getValue().toString());
                textNameHomeUser.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                textBioHomeUser.setText(dataSnapshot.child("bio").getValue().toString());
                textBalanceHomeUser.setText(balance);

                Picasso.with(HomeActivity.this)
                        .load(dataSnapshot.child("url_photo_profile").getValue().toString())
                        .centerCrop()
                        .fit()
                        .into(imagePhotoHomeUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        linearBtnTicketPisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToDetailTicket = new Intent(HomeActivity.this, TicketDetailActivity.class);
                goToDetailTicket.putExtra("Jenis tiket", "Pisa");
                startActivity(goToDetailTicket);
            }
        });

        linearBtnTicketTorri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToDetailTicket = new Intent(HomeActivity.this, TicketDetailActivity.class);
                goToDetailTicket.putExtra("Jenis tiket", "Torri");
                startActivity(goToDetailTicket);
            }
        });

        linearBtnTicketPagoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToDetailTicket = new Intent(HomeActivity.this, TicketDetailActivity.class);
                goToDetailTicket.putExtra("Jenis tiket", "Pagoda");
                startActivity(goToDetailTicket);
            }
        });

        linearBtnTicketCandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToDetailTicket = new Intent(HomeActivity.this, TicketDetailActivity.class);
                goToDetailTicket.putExtra("Jenis tiket", "Candi");
                startActivity(goToDetailTicket);
            }
        });

        linearBtnTicketSphinx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToDetailTicket = new Intent(HomeActivity.this, TicketDetailActivity.class);
                goToDetailTicket.putExtra("Jenis tiket", "Sphink");
                startActivity(goToDetailTicket);
            }
        });

        linearBtnTicketMonas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToDetailTicket = new Intent(HomeActivity.this, TicketDetailActivity.class);
                goToDetailTicket.putExtra("Jenis tiket", "Monas");
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

    private void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        new_username_key = sharedPreferences.getString(mUsernameKey, "");
    }
}
