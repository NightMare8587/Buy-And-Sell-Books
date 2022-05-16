package com.consumers.librarymanagementsystem.Home.Frags;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.consumers.librarymanagementsystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchBooksFrag extends Fragment {
    EditText editText;
    Button searchBooks;
    List<String> genreList = new ArrayList<>();
    List<String> bookNameList = new ArrayList<>();
    List<String> bookAuthorList = new ArrayList<>();
    List<String> bookImageUri = new ArrayList<>();
    RecyclerView recyclerView;
    List<String> sellerName = new ArrayList<>();
    List<String> sellerID = new ArrayList<>();
    List<String> publisherName = new ArrayList<>();
    List<String> contactNum = new ArrayList<>();
    List<String> price = new ArrayList<>();
    String genre;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editTextSearchBooks);
        searchBooks = view.findViewById(R.id.buttonSearchBooks);
        recyclerView = view.findViewById(R.id.searchBooksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        searchBooks.setOnClickListener(view1 -> {
            if(editText.length() == 0)
                Toast.makeText(view1.getContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
            else{
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().getRoot().child("BooksDatabase");
                String input = editText.getText().toString().toLowerCase(Locale.ROOT);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            genreList.clear();
                            bookAuthorList.clear();
                            bookNameList.clear();
                            bookImageUri.clear();
                            sellerName.clear();
                            sellerID.clear();
                            publisherName.clear();
                            contactNum.clear();
                            price.clear();
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                genre = dataSnapshot.getKey();
                                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                    if(dataSnapshot1.getKey().toLowerCase().contains(input)){
                                        genreList.add(genre);
                                        bookAuthorList.add(String.valueOf(dataSnapshot1.child("authorName").getValue()));
                                        bookNameList.add(dataSnapshot1.getKey());
                                        contactNum.add(String.valueOf(dataSnapshot1.child("contactNumber").getValue()));
                                        price.add(String.valueOf(dataSnapshot1.child("price").getValue()));
                                        publisherName.add(String.valueOf(dataSnapshot1.child("publisherName").getValue()));
                                        sellerID.add(String.valueOf(dataSnapshot1.child("sellerID").getValue()));
                                        sellerName.add(String.valueOf(dataSnapshot1.child("sellerName").getValue()));
                                        bookImageUri.add(String.valueOf(dataSnapshot1.child("imageURI").getValue()));
                                    }
                                }
                            }
                            recyclerView.setAdapter(new SearchFragAdapter(genreList,bookNameList,bookAuthorList,bookImageUri,sellerName,sellerID,publisherName,contactNum,price));

                        }else
                            Toast.makeText(view1.getContext(), "No Books Available", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_books_layout,container,false);
    }
}
