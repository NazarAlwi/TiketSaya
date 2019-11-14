 package firstbelajar.digitalsoftware.tiketsaya;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
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

 public class RegisterTwoAct extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_continue, btnAddPhoto;
    ImageView imgPhotoRegisterUser;
    EditText edtNama, edtBio;
    DatabaseReference reference;
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

        btn_back = findViewById(R.id.btn_back);
        btn_continue = findViewById(R.id.btn_continue);
        btnAddPhoto = findViewById(R.id.btn_add_photo);
        imgPhotoRegisterUser = findViewById(R.id.img_photo_register_user);
        edtNama = findViewById(R.id.edt_nama);
        edtBio = findViewById(R.id.edt_bio);

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToRegisterOne = new Intent(RegisterTwoAct.this, RegisterOneAct.class);
                startActivity(backToRegisterOne);
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(new_username_key);
                firebaseStorage = FirebaseStorage.getInstance().getReference().child("Photousers").child(new_username_key);

                if (uriPhotoLocation != null) {
                    StorageReference storageReference = firebaseStorage.child(System.currentTimeMillis() + "," + getFileExtension(uriPhotoLocation));

                    storageReference.putFile(uriPhotoLocation).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String uriPhoto = taskSnapshot.getStorage().getDownloadUrl().toString();
                            reference.getRef().child("url_photo_profile").setValue(uriPhoto);
                            reference.getRef().child("nama_lengkap").setValue(edtNama.getText().toString());
                            reference.getRef().child("bio").setValue(edtBio.getText().toString());
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Intent goToSuccessRegister = new Intent(RegisterTwoAct.this, SuccessRegisterAct.class);
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
             Picasso.with(this).load(uriPhotoLocation).centerCrop().fit().into(imgPhotoRegisterUser);
         }
     }

     private void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        new_username_key = sharedPreferences.getString(username_key, "");
     }
 }
