package firstbelajar.digitalsoftware.tiketsaya;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class MyProfileActivity extends AppCompatActivity {
    public static final String USERNAME_KEY = "username_key";
    private String mUsernameKey = "";
    String new_username_key = "";

    LinearLayout linearBtnMyTicket;
    Button buttonEditProfile;
    TextView tvNamaLengkap, tvBio;
    ImageView imgPhotoProfile;

    DatabaseReference reference;

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

    private void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        new_username_key = sharedPreferences.getString(mUsernameKey, "");
    }
}
