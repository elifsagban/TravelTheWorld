package com.elif.traveltheworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class Profile extends AppCompatActivity {
    TextView fullName, username, email, password, phone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    private Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fullName = findViewById(R.id.profileNameUser);
        username = findViewById(R.id.profileUsername);
        email = findViewById(R.id.profileEmailUser);
        password = findViewById(R.id.profilePasswordU);
        phone = findViewById(R.id.profilePhoneUser);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                phone.setText(documentSnapshot.getString("phone"));
                password.setText(documentSnapshot.getString("pWord"));
                email.setText(documentSnapshot.getString("email"));
                username.setText(documentSnapshot.getString("uName"));
                fullName.setText(documentSnapshot.getString("fName"));




            }
        });


        edit = findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditProfile();
            }
        });
    }


    public void openEditProfile(){
        Intent intent = new Intent(this, EditProfile.class);
        startActivity(intent);

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
            Intent intent= new Intent(Profile.this, MainActivity.class);
            startActivity(intent);
        }
        if(id==R.id.item2){
            Intent intent= new Intent(Profile.this, Map.class);
            startActivity(intent);
        }
        if(id==R.id.item3){
            Intent intent= new Intent(Profile.this, Total.class);
            startActivity(intent);
        }
        if(id==R.id.item4){
            Intent intent= new Intent(Profile.this, Where2Go.class);
            startActivity(intent);
        }

        if(id==R.id.item5){
            Intent intent= new Intent(Profile.this, Profile.class);
            startActivity(intent);
        }

        if(id==R.id.item6) {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();


        }
        return super.onOptionsItemSelected(item);
    }


}
