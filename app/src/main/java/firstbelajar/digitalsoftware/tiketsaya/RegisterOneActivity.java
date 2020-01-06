package firstbelajar.digitalsoftware.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterOneActivity extends AppCompatActivity {
    public static final String USERNAME_KEY = "username_key";
    private String mUsernameKey = "";

    Button buttonContinue, buttonBack;
    EditText editUsername, editPassword, editEmailAddress;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        buttonBack = findViewById(R.id.btn_back_reg_one);
        buttonContinue = findViewById(R.id.btn_continue_reg_one);
        editUsername = findViewById(R.id.username);
        editPassword = findViewById(R.id.password);
        editEmailAddress = findViewById(R.id.email_address);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonContinue.setEnabled(false);
                buttonContinue.setText(getString(R.string.loading_btn));

                // Menyimpan data ke local storage
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(mUsernameKey, editUsername.getText().toString());
                editor.apply();

                // Menyimpan ke database
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(editUsername.getText().toString());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("username").setValue(editUsername.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(editPassword.getText().toString());
                        dataSnapshot.getRef().child("email_address").setValue(editEmailAddress.getText().toString());
                        dataSnapshot.getRef().child("balance").setValue(800);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Intent goToRegisterTwo = new Intent(RegisterOneActivity.this, RegisterTwoActivity.class);
                startActivity(goToRegisterTwo);
            }
        });
    }
}
