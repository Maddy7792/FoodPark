package com.foodpark.profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpark.application.SaveData;
import com.foodpark.R;
import com.foodpark.Utils.AppConstants;
import com.foodpark.model.Profile;
import com.foodpark.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class FPProfileEditActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView fpTVName;
    private TextView fpTVSurname;
    private TextView fpTVEmail;
    private TextView fpTVPhoneNumber;
    private TextView fpTVPassword;
    private ImageView fpBack;
    private ImageView fpProfileAvatar;
    private ImageView fpProfileEdit;
    private User user;
    private Uri saveURI;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseDatabase profileDatabase;
    private DatabaseReference profileRef;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fp_profile_edit);
        context = this;
        profileDatabase = FirebaseDatabase.getInstance();
        profileRef = profileDatabase.getReference(AppConstants.PROFILE_AVATAR);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        fpProfileEdit = findViewById(R.id.fp_edit_small_image);
        fpProfileAvatar = findViewById(R.id.fp_iv_profile_avatar);
        fpBack = findViewById(R.id.fp_edit_back);
        fpTVName = findViewById(R.id.fp_tv_edit_name);
        fpTVEmail = findViewById(R.id.fp_tv_edit_email);
        fpTVPhoneNumber = findViewById(R.id.fp_tv_edit_phone);
        fpTVSurname = findViewById(R.id.fp_tv_edit_sur_name);
        fpTVPassword = findViewById(R.id.fp_tv_edit_password);
        setDetails();
        fpBack.setOnClickListener(this);
        fpProfileEdit.setOnClickListener(this);
        fpTVName.setOnClickListener(this);
        fpTVSurname.setOnClickListener(this);
        fpTVPassword.setOnClickListener(this);
        fpTVPhoneNumber.setOnClickListener(this);
        fpTVPassword.setOnClickListener(this);
        fpTVEmail.setOnClickListener(this);
        getProfileAvatar();

    }


    private void setDetails() {
        user = SaveData.getInstance().getUser();
        fpTVName.setText(user.getName());
        fpTVSurname.setText(user.getSurName());
        fpTVEmail.setText(user.getEmail());
        fpTVPhoneNumber.setText(user.getPhone());
        fpTVPassword.setText(user.getPassword());
    }

    @Override
    public void onClick(View v) {
        int Id = v.getId();

        if (Id == R.id.fp_edit_back) {
            finish();
        }

        if (Id == R.id.fp_tv_edit_name) {
            openSaveEditActivity(AppConstants.KEY_FIRST_NAME, user.getName());
        }
        if (Id == R.id.fp_tv_edit_email) {
            openSaveEditActivity(AppConstants.KEY_EMAIL, user.getEmail());
        }
        if (Id == R.id.fp_tv_edit_phone) {
            openSaveEditActivity(AppConstants.KEY_PHONE_NUMBER, user.getPhone());
        }
        if (Id == R.id.fp_tv_edit_sur_name) {
            openSaveEditActivity(AppConstants.KEY_SURNAME, user.getSurName());
        }
        if (Id == R.id.fp_tv_edit_password) {
            openSaveEditActivity(AppConstants.KEY_PASSWORD, user.getPassword());
        }

        if (Id == R.id.fp_edit_small_image) {
            openGalleryImages();

        }

    }


    private void openSaveEditActivity(String type, String data) {
        Intent intent = new Intent(FPProfileEditActivity.this, FpProfileUpdateActivity.class);
        intent.putExtra(AppConstants.KEY_TYPE, type);
        intent.putExtra(AppConstants.KEY_DATA, data);
        startActivity(intent);
    }

    private void openGalleryImages() {
        Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery.createChooser(gallery, "Select Picture"), AppConstants.PICK_GALLERY_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppConstants.PICK_GALLERY_IMAGE
                && resultCode == RESULT_OK
                && data != null) {

            saveURI = data.getData();
            /*String[] filePathColumn = { MediaStore.Images.Media.DATA };

            // Get the cursor
            Cursor cursor = getContentResolver().query(saveURI,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imgDecodableString = cursor.getString(columnIndex);
            cursor.close();*/

            uploadProfileAvatar(saveURI);

        }
    }

    private void uploadProfileAvatar(Uri saveURI) {
        if (saveURI != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(AppConstants.UPLOADING_TEXT);
            progressDialog.setCancelable(false);
            progressDialog.show();
            String imageName = UUID.randomUUID().toString();
            final StorageReference imageFolder = storageReference.child("profile_images/" + imageName);
            imageFolder.putFile(saveURI)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            imageFolder.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Profile profile = new Profile();
                                            profile.setName(SaveData.getInstance().getUser().getName());
                                            profile.setAvatarUrl(uri.toString());
                                            profileRef.child(SaveData.getInstance().getUser().getPhone()).setValue(profile);
                                        }
                                    });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage(AppConstants.UPLOADING_TEXT + (int) progress + " % ");
                }
            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    Picasso.with(context).load(task.getResult().getDownloadUrl()).into(fpProfileAvatar);
                }
            });
        }
    }


    private void getProfileAvatar() {

        profileRef.child(SaveData.getInstance().getPhoneNumber()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    Log.d("AVATAR", "" + dataSnapshot.getValue(Profile.class).getAvatarUrl());
                    Picasso.with(context).load(dataSnapshot.getValue(Profile.class).getAvatarUrl()).into(fpProfileAvatar);
                }
                profileRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}


