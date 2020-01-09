package com.elif.traveltheworld;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;


public class EditProfile extends AppCompatActivity  {
    private Uri resultUri;
    private static final String TAG = "EditProfileActivity";
    ImageView imgEditProfile;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    DocumentReference documentReference;

    EditText name, email, password, phonenumber, username;
    Button savebutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        imgEditProfile = findViewById(R.id.imgEditProfile);
        name = findViewById(R.id.editName);
        email = findViewById(R.id.editMail);

        password = findViewById(R.id.editPassword);

        phonenumber = findViewById(R.id.editPhone);

        username = findViewById(R.id.editUname);

        savebutton = findViewById(R.id.save);
        userID = fAuth.getCurrentUser().getUid();

        documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                phonenumber.setText(documentSnapshot.getString("phone"));
                password.setText(documentSnapshot.getString("pWord"));
                email.setText(documentSnapshot.getString("email"));
                username.setText(documentSnapshot.getString("uName"));
                name.setText(documentSnapshot.getString("fName"));

            }
        });




        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mail = email.getText().toString().trim();
                final String fullName = name.getText().toString().trim();
                final String uname = username.getText().toString().trim();
                final String pw = password.getText().toString().trim();
                final String phone = phonenumber.getText().toString().trim();

                if (TextUtils.isEmpty(mail)){
                    email.setError("Email is required!");
                    return;
                }
                if (TextUtils.isEmpty(fullName)){
                    name.setError("Full Name is required!");
                    return;
                }
                if (TextUtils.isEmpty(uname)){
                    username.setError("Username is required!");
                    return;
                }

                if (TextUtils.isEmpty(pw)){
                    password.setError("Password is required!");
                    return;
                }

                if (pw.length() < 6){
                    password.setError("Password must be equal or greater than 6 characters!");
                    return;
                }


                documentReference.update("fName", fullName);
                documentReference.update("uName", uname);
                documentReference.update("email", mail);
                documentReference.update("pWord", pw);
                documentReference.update("phone", phone);


                    documentReference.update(Register.user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Log.d("total", "complete it");


                        }
                    });  startActivity(new Intent(getApplicationContext(), Profile.class));           }
        });

        imgEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);
                savedUserInformation();
            }
        });

    }

    private void savedUserInformation(){

        if(resultUri !=null){
            StorageReference filePath = FirebaseStorage.getInstance().getReference().child("profileImages").child(userID);
            Bitmap bitmap = null;

            final DocumentReference documentReference = FirebaseFirestore.getInstance().
                    collection("users").
                    document();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos= new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,20, baos);
            byte[] data= baos.toByteArray();
            UploadTask uploadTask= filePath.putBytes(data);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    finish();
                    return;
                }
            });
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                    HashMap newImage = new HashMap();
                    newImage.put("profileImageUrl", downloadUrl.toString());

                    documentReference.set(newImage);

                    finish();
                    return;





                }
            });
        }else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.item1){
            Intent intent= new Intent(EditProfile.this, MainActivity.class);
            startActivity(intent);
        }
        if(id==R.id.item2){
            Intent intent= new Intent(EditProfile.this, Map.class);
            startActivity(intent);
        }
        if(id==R.id.item3){
            Intent intent= new Intent(EditProfile.this, Total.class);
            startActivity(intent);
        }
        if(id==R.id.item4){
            Intent intent= new Intent(EditProfile.this, Where2Go.class);
            startActivity(intent);
        }


        if(id==R.id.item5){
            Intent intent= new Intent(EditProfile.this, Profile.class);
            startActivity(intent);
        }

        if(id==R.id.item6) {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            final Uri imageUri= data.getData();
            resultUri = imageUri;
            imgEditProfile.setImageURI(resultUri);

        }
    }

    private void handleUpload(Bitmap bitmap){
        ByteArrayOutputStream baos= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final StorageReference reference = FirebaseStorage.getInstance().getReference().child("profileImages").child(uid + ".jpeg");

        reference.putBytes(baos.toByteArray()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                getDownloadUrl(reference);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: ", e.getCause());
            }
        });

    }

    private void getDownloadUrl(StorageReference reference){
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d(TAG, "onSuccess: "+ uri);
                setUserProfileUrl(uri);
            }
        });
    }

    private void setUserProfileUrl(Uri uri){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setPhotoUri(uri).build();

        user.updateProfile(request).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(EditProfile.this, "Updated successfully!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfile.this, "Profile img failed!", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
