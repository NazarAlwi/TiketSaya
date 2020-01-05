package firstbelajar.digitalsoftware.tiketsaya;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class EditProfileActivity extends AppCompatActivity {
    public static final String USERNAME_KEY = "username_key";
    private String mUsernameKey = "";
    String new_username_key = "";
    private DatabaseReference mReference;
    private StorageReference mStorageReference;
    private Uri mUriPhotoLocation;
    private EditText edtName, edtBio, edtUsername, edtEmail, edtPassword;
    private ImageView imgProfile;
    private Button btnSave, btnEditPhoto;
    private LinearLayout btnBack;
    private Integer photoMax = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getUsernameLocal();

        edtName = findViewById(R.id.edt_nama_edit_profile);
        edtBio = findViewById(R.id.edt_bio_edit_profile);
        edtUsername = findViewById(R.id.edt_username_edit_profile);
        edtEmail = findViewById(R.id.edt_email_address_edit_profile);
        edtPassword = findViewById(R.id.edt_password_edit_profile);
        imgProfile = findViewById(R.id.img_photo_edit_profile);
        btnBack = findViewById(R.id.btn_back_edit_profile);
        btnSave = findViewById(R.id.btn_save_profile);
        btnEditPhoto = findViewById(R.id.btn_edit_photo);

        mStorageReference = FirebaseStorage.getInstance().getReference().child("Photousers").child(new_username_key);
        mReference = FirebaseDatabase.getInstance().getReference().child("Users").child(new_username_key);
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                edtName.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                setSelcetion(edtName);
                edtBio.setText(dataSnapshot.child("bio").getValue().toString());
                setSelcetion(edtBio);
                edtUsername.setText(dataSnapshot.child("username").getValue().toString());
                setSelcetion(edtUsername);
                edtEmail.setText(dataSnapshot.child("email_address").getValue().toString());
                setSelcetion(edtEmail);
                edtPassword.setText(dataSnapshot.child("password").getValue().toString());
                setSelcetion(edtPassword);

                Picasso.with(EditProfileActivity.this)
                        .load(dataSnapshot.child("url_photo_profile").getValue().toString())
                        .centerCrop()
                        .fit()
                        .into(imgProfile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnEditPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSave.setEnabled(false);
                btnSave.setText("Loading...");

                mReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("nama_lengkap").setValue(edtName.getText().toString());
                        dataSnapshot.getRef().child("bio").setValue(edtBio.getText().toString());
                        dataSnapshot.getRef().child("username").setValue(edtUsername.getText().toString());
                        dataSnapshot.getRef().child("email_address").setValue(edtEmail.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(edtPassword.getText().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                // validasi untuk file (apakah ada?)
                if (mUriPhotoLocation != null) {
                    final StorageReference storageReference = mStorageReference.child(System.currentTimeMillis() + "." + getFileExtension(mUriPhotoLocation));

                    storageReference.putFile(mUriPhotoLocation).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String uriPhoto = uri.toString();
                                    mReference.getRef().child("url_photo_profile").setValue(uriPhoto);
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Intent goToMyProfile = new Intent(EditProfileActivity.this, MyProfileActivity.class);
                                    startActivity(goToMyProfile);
                                }
                            });
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        }
                    });
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == photoMax && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mUriPhotoLocation = data.getData();
            Picasso.with(this).load(mUriPhotoLocation).centerCrop().fit().into(imgProfile);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        new_username_key = sharedPreferences.getString(mUsernameKey, "");
    }

    private void setSelcetion(EditText editText) {
        int position = editText.length();
        editText.setSelection(position);
    }

    private void findPhoto() {
        Intent addPhoto = new Intent();
        addPhoto.setType("image/*");
        addPhoto.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(addPhoto, photoMax);
    }
}