package com.elif.traveltheworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Profile extends AppCompatActivity {

    private Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        edit = (Button) findViewById(R.id.edit);
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
            Intent intent= new Intent(Profile.this, Total.class);
            startActivity(intent);
        }
        if(id==R.id.item3){
            Intent intent= new Intent(Profile.this, Where2Go.class);
            startActivity(intent);
        }
        if(id==R.id.item4){
            Intent intent= new Intent(Profile.this, Profile.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
