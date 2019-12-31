package firstbelajar.digitalsoftware.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

    LinearLayout linearBtnBack;
    Button buttonContinue;
    EditText editUsername, editPassword, editEmailAddress;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        linearBtnBack = findViewById(R.id.btn_back);
        buttonContinue = findViewById(R.id.btn_continue);
        editUsername = findViewById(R.id.edt_username);
        editPassword = findViewById(R.id.edt_password);
        editEmailAddress = findViewById(R.id.edt_email_address);

        linearBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToSignIn = new Intent(RegisterOneActivity.this, SignInActivity.class);
                startActivity(backToSignIn);
            }
        });
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonContinue.setEnabled(false);
                buttonContinue.setText("Loading...");

                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(mUsernameKey, editUsername.getText().toString());
                editor.apply();

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
