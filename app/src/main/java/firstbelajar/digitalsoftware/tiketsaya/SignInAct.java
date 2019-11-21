package firstbelajar.digitalsoftware.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInAct extends AppCompatActivity {

    TextView btn_new_account;
    Button btn_sign_in;
    EditText edtUsername, edtPassword;

    DatabaseReference databaseReference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btn_new_account = findViewById(R.id.btn_new_account);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRegisterOne = new Intent(SignInAct.this, RegisterOneAct.class);
                startActivity(goToRegisterOne);
            }
        });
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(edtUsername.getText().toString());

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            //Cocokkan passwor
                            String password = dataSnapshot.child("password").getValue().toString();

                            //Validasi password
                            if (password.equals(edtPassword.getText().toString())) {
                                //Simpan ke lokal
                                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(username_key, edtUsername.getText().toString());
                                editor.apply();

                                Intent goToHome = new Intent(SignInAct.this, HomeAct.class);
                                startActivity(goToHome);
                            } else {
                                Toast.makeText(getApplicationContext(), "Password salah", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Username Tidak Ada", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
