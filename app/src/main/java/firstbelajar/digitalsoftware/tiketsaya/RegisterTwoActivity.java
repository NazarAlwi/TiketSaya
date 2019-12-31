 package firstbelajar.digitalsoftware.tiketsaya;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

 public class RegisterTwoActivity extends AppCompatActivity {

    LinearLayout linearBtnBack;
    Button buttonContinue, buttonAddPhoto;
    ImageView imagePhotoRegisterUser;
    EditText editNama, editBio;
    DatabaseReference databaseReference;
    StorageReference firebaseStorage;

    Uri uriPhotoLocation;
    Integer photoMax = 1;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String new_username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);

        getUsernameLocal();

        linearBtnBack = findViewById(R.id.btn_back);
        buttonContinue = findViewById(R.id.btn_continue);
        buttonAddPhoto = findViewById(R.id.btn_add_photo);
        imagePhotoRegisterUser = findViewById(R.id.img_photo_register_user);
        editNama = findViewById(R.id.edt_nama);
        editBio = findViewById(R.id.edt_bio);

        buttonAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });

        linearBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToRegisterOne = new Intent(RegisterTwoActivity.this, RegisterOneActivity.class);
                startActivity(backToRegisterOne);
            }
        });

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ubah state menjadi loading
                buttonContinue.setEnabled(false);
                buttonContinue.setText("Loading...");

                // menyimpan kepada firebase
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(new_username_key);
                firebaseStorage = FirebaseStorage.getInstance().getReference().child("Photousers").child(new_username_key);

                // validasi untuk file (apakah ada?)
                if (uriPhotoLocation != null) {
                    StorageReference storageReference = firebaseStorage.child(System.currentTimeMillis() + "." + getFileExtension(uriPhotoLocation));

                    storageReference.putFile(uriPhotoLocation).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String uriPhoto = taskSnapshot.getStorage().getDownloadUrl().toString();
                            databaseReference.getRef().child("url_photo_profile").setValue(uriPhoto);
                            databaseReference.getRef().child("nama_lengkap").setValue(editNama.getText().toString());
                            databaseReference.getRef().child("bio").setValue(editBio.getText().toString());
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Intent goToSuccessRegister = new Intent(RegisterTwoActivity.this, SuccessRegisterActivity.class);
                            startActivity(goToSuccessRegister);
                        }
                    });
                }
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void findPhoto() {
        Intent addPhoto = new Intent();
        addPhoto.setType("image/*");
        addPhoto.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(addPhoto, photoMax);
    }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

         if (requestCode == photoMax && resultCode == RESULT_OK && data != null && data.getData() != null) {
             uriPhotoLocation = data.getData();
             Picasso.with(this).load(uriPhotoLocation).centerCrop().fit().into(imagePhotoRegisterUser);
         }
     }

     private void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        new_username_key = sharedPreferences.getString(username_key, "");
     }
 }
