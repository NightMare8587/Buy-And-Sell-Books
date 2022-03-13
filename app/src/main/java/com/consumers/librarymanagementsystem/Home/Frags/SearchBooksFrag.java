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

import com.consumers.librarymanagementsystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class SearchBooksFrag extends Fragment {
    EditText editText;
    Button searchBooks;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editTextSearchBooks);
        searchBooks = view.findViewById(R.id.buttonSearchBooks);

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
