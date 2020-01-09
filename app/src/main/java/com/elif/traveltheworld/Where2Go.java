package com.elif.traveltheworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Where2Go extends AppCompatActivity implements View.OnClickListener {
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where2_go);

        btn1 = findViewById(R.id.place_1);
        btn1.setOnClickListener(this);
        btn2 = findViewById(R.id.place_2);
        btn2.setOnClickListener(this);
        btn3 = findViewById(R.id.place_3);
        btn3.setOnClickListener(this);
        btn4 = findViewById(R.id.place_4);
        btn4.setOnClickListener(this);
        btn5 = findViewById(R.id.place_5);
        btn5.setOnClickListener(this);
        btn6 = findViewById(R.id.place_6);
        btn6.setOnClickListener(this);
        btn7 = findViewById(R.id.place_7);
        btn7.setOnClickListener(this);
        btn8 = findViewById(R.id.place_8);
        btn8.setOnClickListener(this);
        btn9 = findViewById(R.id.place_9);
        btn9.setOnClickListener(this);
        btn10 = findViewById(R.id.place_10);
        btn10.setOnClickListener(this);
        btn11 = findViewById(R.id.place_11);
        btn11.setOnClickListener(this);
        btn12 = findViewById(R.id.place_12);
        btn12.setOnClickListener(this);
        btn13= findViewById(R.id.place_13);
        btn13.setOnClickListener(this);
        btn14 = findViewById(R.id.place_14);
        btn14.setOnClickListener(this);
        btn15 = findViewById(R.id.place_15);
        btn15.setOnClickListener(this);
        btn16 = findViewById(R.id.place_16);
        btn16.setOnClickListener(this);
        btn17 = findViewById(R.id.place_17);
        btn17.setOnClickListener(this);
        btn18 = findViewById(R.id.place_18);
        btn18.setOnClickListener(this);
        final  Button [] buttons = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10,
                btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18};





        for (int item = 0 ; item < buttons.length ; item++ ) {
            Random generator = new Random();
            int randomIndex = generator.nextInt(Country.getCountry().length);
            String text = Country.getCountry()[randomIndex];
            buttons[item].setText(text);


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
            Intent intent= new Intent(Where2Go.this, MainActivity.class);
            startActivity(intent);
        }
        if(id==R.id.item2){
            Intent intent= new Intent(Where2Go.this, Map.class);
            startActivity(intent);
        }
        if(id==R.id.item3){
            Intent intent= new Intent(Where2Go.this, Total.class);
            startActivity(intent);
        }
        if(id==R.id.item4){
            Intent intent= new Intent(Where2Go.this, Where2Go.class);
            startActivity(intent);
        }

        if(id==R.id.item5){
            Intent intent= new Intent(Where2Go.this, Profile.class);
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
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.place_1:
                openWantPage(btn1.getText().toString());
               // goToCountry(btn1.getText().toString());
                break;
            case R.id.place_2:
                openWantPage(btn2.getText().toString());
                break;

            case R.id.place_3:
                openWantPage(btn3.getText().toString());
                break;

            case R.id.place_4:
                openWantPage(btn4.getText().toString());
                break;

            case R.id.place_5:
                openWantPage(btn5.getText().toString());
                break;

            case R.id.place_6:
                openWantPage(btn6.getText().toString());
                break;

            case R.id.place_7:
                openWantPage(btn7.getText().toString());
                break;

            case R.id.place_8:
                openWantPage(btn8.getText().toString());
                break;

            case R.id.place_9:
                openWantPage(btn9.getText().toString());
                break;

            case R.id.place_10:
                openWantPage(btn10.getText().toString());
                break;

            case R.id.place_11:
                openWantPage(btn11.getText().toString());
                break;

            case R.id.place_12:
                openWantPage(btn12.getText().toString());
                break;

            case R.id.place_13:
                openWantPage(btn13.getText().toString());
                break;

            case R.id.place_14:
                openWantPage(btn14.getText().toString());
                break;

            case R.id.place_15:
                openWantPage(btn15.getText().toString());
                break;

            case R.id.place_16:
                openWantPage(btn16.getText().toString());
                break;

            case R.id.place_17:
                openWantPage(btn17.getText().toString());
                break;

            case R.id.place_18:
                openWantPage(btn18.getText().toString());
                break;


        }




    }



    public void openWantPage(String country){
        Intent intent = new Intent(this, WantBtnActivity.class );
        intent.putExtra("Key", country);
        startActivity(intent);
    }

}