package com.elif.traveltheworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class WantBtnActivity extends AppCompatActivity  implements View.OnClickListener{

    Button mapbtn;
    Button wikibtn;
    String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_want_btn);

        mapbtn = findViewById(R.id.gmapbtn);
        mapbtn.setOnClickListener(this);
        wikibtn = findViewById(R.id.wikibtn);
        wikibtn.setOnClickListener(this);


        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
             value = extras.getString("Key");
        }



    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.gmapbtn:

                String geoUri = "https://www.google.com/maps/place/" + value;

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                this.startActivity(intent);
                break;
            case R.id.wikibtn:

                String wikiUri = "https://en.wikipedia.org/wiki/" + value;

                Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(wikiUri));
                this.startActivity(intent2);

                break;


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
            Intent intent= new Intent(WantBtnActivity.this, MainActivity.class);
            startActivity(intent);
        }
        if(id==R.id.item2){
            Intent intent= new Intent(WantBtnActivity.this, Map.class);
            startActivity(intent);
        }
        if(id==R.id.item3){
            Intent intent= new Intent(WantBtnActivity.this, Total.class);
            startActivity(intent);
        }
        if(id==R.id.item4){
            Intent intent= new Intent(WantBtnActivity.this, Where2Go.class);
            startActivity(intent);
        }

        if(id==R.id.item5){
            Intent intent= new Intent(WantBtnActivity.this, Profile.class);
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

