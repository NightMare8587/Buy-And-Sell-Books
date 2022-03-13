package com.consumers.librarymanagementsystem.Home.SellBooks.Sell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.consumers.librarymanagementsystem.Home.SellBooks.SellingBooks;
import com.consumers.librarymanagementsystem.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SellYourBooks extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FloatingActionButton floatingActionButton;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    List<String> bookName = new ArrayList<>();
    List<String> bookGenre = new ArrayList<>();
    List<String> bookPrice = new ArrayList<>();
    List<String> purchaseCount = new ArrayList<>();
    List<String> imageUri = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_your_books);
        recyclerView = findViewById(R.id.sellBooksRecyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        databaseReference = FirebaseDatabase.getInstance().getReference().getRoot().child("Users").child(Objects.requireNonNull(auth.getUid())).child("My Books");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        bookName.add(dataSnapshot.getKey());
                        bookPrice.add(String.valueOf(dataSnapshot.child("price").getValue()));
                        purchaseCount.add(String.valueOf(dataSnapshot.child("purchasedCount").getValue()));
                        bookGenre.add(String.valueOf(dataSnapshot.child("genre").getValue()));
                        imageUri.add(String.valueOf(dataSnapshot.child("imageUri").getValue()));
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(SellYourBooks.this));
                    recyclerView.setAdapter(new Adap(bookName,bookGenre,bookPrice,purchaseCount,imageUri));
                }else
                    Toast.makeText(SellYourBooks.this, "No Sold Books available", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        floatingActionButton.setOnClickListener(click -> {
            startActivity(new Intent(SellYourBooks.this, SellingBooks.class));
        });
    }
}