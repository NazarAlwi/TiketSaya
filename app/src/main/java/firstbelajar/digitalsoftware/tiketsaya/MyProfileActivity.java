package firstbelajar.digitalsoftware.tiketsaya;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
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

import java.util.ArrayList;

public class MyProfileActivity extends AppCompatActivity {
    public static final String USERNAME_KEY = "username_key";
    private String mUsernameKey = "";
    String new_username_key = "";

    LinearLayout linearBtnMyTicket;
    Button buttonEditProfile, btnBack, btnSignOut;
    TextView tvNamaLengkap, tvBio;
    ImageView imgPhotoProfile;
    RecyclerView recyclerView;

    ArrayList<MyTicket> myTickets;
    TicketAdapter adapter;

    DatabaseReference reference, ticketReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        getUsernameLocal();

        linearBtnMyTicket = findViewById(R.id.my_ticket);
        buttonEditProfile = findViewById(R.id.btn_edit_profile);
        tvNamaLengkap = findViewById(R.id.tv_name_profile_user);
        tvBio = findViewById(R.id.tv_bio_profile_user);
        imgPhotoProfile = findViewById(R.id.img_photo_profile_user);
        recyclerView = findViewById(R.id.my_ticket_place);
        btnBack = findViewById(R.id.btn_back_to_home);
        btnSignOut = findViewById(R.id.btn_sign_out);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myTickets = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(new_username_key);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tvNamaLengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                tvBio.setText(dataSnapshot.child("bio").getValue().toString());

                Picasso.with(MyProfileActivity.this)
                        .load(dataSnapshot.child("url_photo_profile").getValue().toString())
                        .centerCrop()
                        .fit()
                        .into(imgPhotoProfile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfileActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToEditProfile = new Intent(MyProfileActivity.this, EditProfileActivity.class);
                startActivity(goToEditProfile);
            }
        });

        ticketReference = FirebaseDatabase.getInstance().getReference().child("MyTickets").child(new_username_key);
        ticketReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    MyTicket p = data.getValue(MyTicket.class);
                    myTickets.add(p);
                }
                adapter = new TicketAdapter(MyProfileActivity.this, myTickets);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menghapus data ke local storage
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(mUsernameKey, null);
                editor.apply();

                Intent goToSignIn = new Intent(MyProfileActivity.this, SignInActivity.class);
                startActivity(goToSignIn);
            }
        });
    }

    private void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        new_username_key = sharedPreferences.getString(mUsernameKey, "");
    }
}
