package com.elif.traveltheworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button where;
    private Button total;
    private Button profile;
    private Button map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView image = (ImageView) findViewById(R.id.imageView);
        int imageResource= getResources().getIdentifier("@drawable/globe", null, this.getPackageName());
        image.setImageResource(imageResource);


        where = (Button) findViewById(R.id.where);
        where.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWhere2Go();
            }
        });
        total = (Button) findViewById(R.id.total);
        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                openTotal();
            }
        });

        profile = (Button) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfile();
            }
        });

        map = (Button) findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });




    }
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();

    }


    public void openWhere2Go(){
        Intent intent = new Intent(this, Where2Go.class);
        startActivity(intent);

    }
    public void openTotal(){
        Intent intent = new Intent(this, Total.class);
        startActivity(intent);

    }
    public void openProfile(){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    public void openMap(){
        Intent intent = new Intent(this, Map.class );
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
            Intent intent= new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        }
        if(id==R.id.item2){
            Intent intent= new Intent(MainActivity.this, Map.class);
            startActivity(intent);
        }
        if(id==R.id.item3){
            Intent intent= new Intent(MainActivity.this, Total.class);
            startActivity(intent);
        }
        if(id==R.id.item4){
            Intent intent= new Intent(MainActivity.this, Where2Go.class);
            startActivity(intent);
        }

        if(id==R.id.item5){
            Intent intent= new Intent(MainActivity.this, Profile.class);
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
