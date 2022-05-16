package com.consumers.librarymanagementsystem.Home.Frags;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
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

import com.consumers.librarymanagementsystem.Home.BooksAdapter;
import com.consumers.librarymanagementsystem.R;
import com.google.firebase.database.ChildEventListener;
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
    LinearLayoutManager linearLayoutManager1;
    Context context;
    List<String> image = new ArrayList<>();
    List<String> bookName = new ArrayList<>();List<String> image1 = new ArrayList<>();
    List<String> bookName1 = new ArrayList<>();
    DatabaseReference allBooksRef;
    DatabaseReference allBooksRef1;
    List<String> genre = new ArrayList<>();
    List<String> authorName = new ArrayList<>();
    List<String> authorName1 = new ArrayList<>();
    List<String> publisherName = new ArrayList<>();
    List<String> publisherName1 = new ArrayList<>();
    List<String> contactNum = new ArrayList<>();
    List<String> contactNum1 = new ArrayList<>();
    List<String> genre1 = new ArrayList<>();
    RecyclerView recyclerView;
    List<String> price = new ArrayList<>();
    List<String> sellerEmail = new ArrayList<>();
    List<String> sellerID = new ArrayList<>();
    List<String> sellerName = new ArrayList<>();List<String> price1 = new ArrayList<>();
    List<String> sellerEmail1 = new ArrayList<>();
    List<String> sellerID1 = new ArrayList<>();
    List<String> sellerName1 = new ArrayList<>();
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.buy_books_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);linearLayoutManager1 = new LinearLayoutManager(view.getContext());
        linearLayoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        context = view.getContext();
        myPref = view.findViewById(R.id.preferencesRecyclerView);
        recyclerView = view.findViewById(R.id.allBooksRecyclerView);
        sharedPreferences = view.getContext().getSharedPreferences("BooksPref",MODE_PRIVATE);
        databaseReference = FirebaseDatabase.getInstance().getReference().getRoot().child("BooksDatabase");
        databaseReference1 = FirebaseDatabase.getInstance().getReference().getRoot().child("BooksDatabase");
        allBooksRef = FirebaseDatabase.getInstance().getReference().getRoot().child("BooksDatabase");
        allBooksRef1 = FirebaseDatabase.getInstance().getReference().getRoot().child("BooksDatabase");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    bookName.clear();
                    genre.clear();
                    authorName.clear();
                    publisherName.clear();contactNum.clear();
                    price.clear();
                    image.clear();
                    sellerName.clear();
                    sellerID.clear();
                    sellerEmail.clear();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        if(sharedPreferences.contains(dataSnapshot.getKey())) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                bookName.add(dataSnapshot1.getKey());
                                genre.add(dataSnapshot1.child("genre").getValue(String.class));
                                price.add(dataSnapshot1.child("price").getValue(String.class));
                                image.add(dataSnapshot1.child("imageURI").getValue(String.class));
                                authorName.add(dataSnapshot1.child("authorName").getValue(String.class));
                                publisherName.add(dataSnapshot1.child("publisherName").getValue(String.class));
                                contactNum.add(dataSnapshot1.child("contactNumber").getValue(String.class));
                                sellerID.add(dataSnapshot1.child("sellerID").getValue(String.class));
                                sellerEmail.add(dataSnapshot1.child("sellerEmail").getValue(String.class));
                                sellerName.add(dataSnapshot1.child("sellerName").getValue(String.class));
                            }
                        }
                        myPref.setLayoutManager(linearLayoutManager);
                        myPref.setAdapter(new BooksAdapter(image,bookName,genre,price,sellerEmail,sellerID,sellerName,authorName,publisherName,contactNum));
                    }
                }else
                    Toast.makeText(view.getContext(), "No Books Available As per preferences", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                updateChild();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                updateChild();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                updateChild();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        allBooksRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    bookName1.clear();
                    genre1.clear();
                    price1.clear();
                    image1.clear();
                    sellerName1.clear();
                    sellerID1.clear();
                    sellerEmail1.clear();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            bookName1.add(dataSnapshot1.getKey());
                            genre1.add(dataSnapshot1.child("genre").getValue(String.class));
                            price1.add(dataSnapshot1.child("price").getValue(String.class));
                            image1.add(dataSnapshot1.child("imageURI").getValue(String.class));
                            authorName1.add(dataSnapshot1.child("authorName").getValue(String.class));
                            publisherName1.add(dataSnapshot1.child("publisherName").getValue(String.class));
                            contactNum1.add(dataSnapshot1.child("contactNumber").getValue(String.class));
                            sellerID1.add(dataSnapshot1.child("sellerID").getValue(String.class));
                            sellerEmail1.add(dataSnapshot1.child("sellerEmail").getValue(String.class));
                            sellerName1.add(dataSnapshot1.child("sellerName").getValue(String.class));
                        }
                        recyclerView.setLayoutManager(linearLayoutManager1);
                        recyclerView.setAdapter(new AllBooksAdp(image1,bookName1,genre1,price1,sellerEmail1,sellerID1,sellerName1,authorName1,publisherName1,contactNum1));
                    }
                }else
                    Toast.makeText(view.getContext(), "No Books Available", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        allBooksRef1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                updateChild1();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                updateChild1();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                updateChild1();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void updateChild1() {
        allBooksRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    bookName1.clear();
                    genre1.clear();
                    price1.clear();
                    image1.clear();
                    sellerName1.clear();
                    sellerID1.clear();
                    sellerEmail1.clear();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            bookName1.add(dataSnapshot1.getKey());
                            genre1.add(dataSnapshot1.child("genre").getValue(String.class));
                            price1.add(dataSnapshot1.child("price").getValue(String.class));
                            image1.add(dataSnapshot1.child("imageURI").getValue(String.class));
                            authorName1.add(dataSnapshot1.child("authorName").getValue(String.class));
                            publisherName1.add(dataSnapshot1.child("publisherName").getValue(String.class));
                            contactNum1.add(dataSnapshot1.child("contactNumber").getValue(String.class));
                            sellerID1.add(dataSnapshot1.child("sellerID").getValue(String.class));
                            sellerEmail1.add(dataSnapshot1.child("sellerEmail").getValue(String.class));
                            sellerName1.add(dataSnapshot1.child("sellerName").getValue(String.class));
                        }
                        recyclerView.setLayoutManager(linearLayoutManager1);
                        recyclerView.setAdapter(new AllBooksAdp(image1,bookName1,genre1,price1,sellerEmail1,sellerID1,sellerName1,authorName1,publisherName1,contactNum1));
                    }
                }else
                    Toast.makeText(context, "No Books Available", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateChild() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    bookName.clear();
                    genre.clear();
                    authorName.clear();
                    publisherName.clear();contactNum.clear();
                    price.clear();
                    image.clear();
                    sellerName.clear();
                    sellerID.clear();
                    sellerEmail.clear();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        if(sharedPreferences.contains(dataSnapshot.getKey())) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                bookName.add(dataSnapshot1.getKey());
                                genre.add(dataSnapshot1.child("genre").getValue(String.class));
                                price.add(dataSnapshot1.child("price").getValue(String.class));
                                image.add(dataSnapshot1.child("imageURI").getValue(String.class));
                                authorName.add(dataSnapshot1.child("authorName").getValue(String.class));
                                publisherName.add(dataSnapshot1.child("publisherName").getValue(String.class));
                                contactNum.add(dataSnapshot1.child("contactNumber").getValue(String.class));
                                sellerID.add(dataSnapshot1.child("sellerID").getValue(String.class));
                                sellerEmail.add(dataSnapshot1.child("sellerEmail").getValue(String.class));
                                sellerName.add(dataSnapshot1.child("sellerName").getValue(String.class));
                            }
                        }
                        myPref.setLayoutManager(linearLayoutManager);
                        myPref.setAdapter(new BooksAdapter(image,bookName,genre,price,sellerEmail,sellerID,sellerName,authorName,publisherName,contactNum));
                    }
                }else
                    Toast.makeText(context, "No Books Available As per preferences", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
