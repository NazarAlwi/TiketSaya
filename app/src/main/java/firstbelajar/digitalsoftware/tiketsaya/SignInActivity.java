package firstbelajar.digitalsoftware.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class SignInActivity extends AppCompatActivity {

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
                Intent goToRegisterOne = new Intent(SignInActivity.this, RegisterOneActivity.class);
                startActivity(goToRegisterOne);
            }
        });
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_sign_in.setEnabled(false);
                btn_sign_in.setText("Loading...");

                if (edtUsername.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username kosong!", Toast.LENGTH_SHORT).show();
                    // ubah state menjadi loading
                    btn_sign_in.setEnabled(true);
                    btn_sign_in.setText("SIGN IN");
                } else {
                    if (edtPassword.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Password kosong!", Toast.LENGTH_SHORT).show();
                        // ubah state menjadi loading
                        btn_sign_in.setEnabled(true);
                        btn_sign_in.setText("SIGN IN");
                    } else {
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(edtUsername.getText().toString());

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    //Cocokkan password
                                    String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();

                                    //Validasi password
                                    if (edtPassword.getText().toString().equals(passwordFromFirebase)) {
                                        //Simpan ke lokal
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, edtUsername.getText().toString());
                                        editor.apply();

                                        Intent goToHome = new Intent(SignInActivity.this, HomeActivity.class);
                                        startActivity(goToHome);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Password salah", Toast.LENGTH_SHORT).show();
                                        // ubah state menjadi loading
                                        btn_sign_in.setEnabled(true);
                                        btn_sign_in.setText("SIGN IN");
                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(), "Username Tidak Ada", Toast.LENGTH_SHORT).show();
                                    // ubah state menjadi loading
                                    btn_sign_in.setEnabled(true);
                                    btn_sign_in.setText("SIGN IN");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "Database Error!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }
}
