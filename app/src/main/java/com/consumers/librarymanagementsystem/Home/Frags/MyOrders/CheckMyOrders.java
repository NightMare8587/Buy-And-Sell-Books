package com.consumers.librarymanagementsystem.Home.Frags.MyOrders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.consumers.librarymanagementsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class CheckMyOrders extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_my_orders);
        databaseReference = FirebaseDatabase.getInstance().getReference().getRoot().child("Users").child(Objects.requireNonNull(auth.getUid())).child("My Orders Placed");
        recyclerView = findViewById(R.id.CheckMyOrdersRecuclerView);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}