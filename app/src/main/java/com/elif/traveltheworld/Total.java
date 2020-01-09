package com.elif.traveltheworld;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Total extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static final ArrayList<Item> exampleList = new ArrayList<>();
    public FirebaseAuth fAuth;
    public FirebaseFirestore fStore;
    public  String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);

        mRecyclerView = findViewById(R.id.recylerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new totalAdapter(exampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userID);

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                exampleList.clear();
                List<String> elems =  (List<String>) documentSnapshot.get("countries");
                Iterator iterator = elems.iterator();
                while (iterator.hasNext()) {
                    String country_name = (String ) iterator.next();

                    exampleList.add(new Item(R.drawable.ic_fiber, country_name)); }



            }
        });
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                exampleList.clear();
                List<String>  elems =  (List<String>) documentSnapshot.get("countries");
                Iterator iterator = elems.iterator();
                while (iterator.hasNext()) {
                    String country_name = (String ) iterator.next();

                    exampleList.add(new Item(R.drawable.ic_fiber, country_name)); }


            }
        });

    }


    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item1) {
            Intent intent = new Intent(Total.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.item2) {
            Intent intent = new Intent(Total.this, Map.class);
            startActivity(intent);
        }
        if (id == R.id.item3) {
            Intent intent = new Intent(Total.this, Total.class);
            startActivity(intent);
        }
        if (id == R.id.item4) {
            Intent intent = new Intent(Total.this, Where2Go.class);
            startActivity(intent);
        }

        if(id==R.id.item5){
            Intent intent= new Intent(Total.this, Profile.class);
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
