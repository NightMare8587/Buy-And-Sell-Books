package com.consumers.librarymanagementsystem.Home.SellBooks.Sell.CheckOrdersUsersList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.consumers.librarymanagementsystem.Home.Frags.MyOrders.showAdapterOrders;
import com.consumers.librarymanagementsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AvailableOrders extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    List<String> buyerID = new ArrayList<>();
    List<String> buyerName = new ArrayList<>();
    List<String> buyerContactNum = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_orders);
        recyclerView = findViewById(R.id.availableOrdersRecyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReference().getRoot().child("Users").child(Objects.requireNonNull(auth.getUid())).child("My Books").child(getIntent().getStringExtra("bookName")).child("Orders");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    buyerID.add(dataSnapshot.getKey());
                    buyerName.add(dataSnapshot.child("buyerName").getValue(String.class));
                    buyerContactNum.add(dataSnapshot.child("buyerContactNum").getValue(String.class));
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(AvailableOrders.this));
                recyclerView.setAdapter(new showAdapterOrders(buyerID,buyerName,buyerContactNum,getIntent().getStringExtra("bookName")));
//                Toast.makeText(AvailableOrders.this, "" + buyerID.toString(), Toast.LENGTH_SHORT).show();
                Log.d("infoses",buyerContactNum.toString() + "\n" + buyerID.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}