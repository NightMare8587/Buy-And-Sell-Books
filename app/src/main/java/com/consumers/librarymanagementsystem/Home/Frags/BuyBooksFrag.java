package com.consumers.librarymanagementsystem.Home.Frags;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.consumers.librarymanagementsystem.Home.HomeScreen;
import com.consumers.librarymanagementsystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BuyBooksFrag extends Fragment {
    RecyclerView myPref;
    LinearLayoutManager linearLayoutManager;
    List<String> image = new ArrayList<>();
    List<String> bookName = new ArrayList<>();
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.buy_books_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        myPref = view.findViewById(R.id.preferencesRecyclerView);
        sharedPreferences = view.getContext().getSharedPreferences("BooksPref",MODE_PRIVATE);
        databaseReference = FirebaseDatabase.getInstance().getReference().getRoot().child("BooksDatabase");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                }else
                    Toast.makeText(view.getContext(), "No Books Available", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
