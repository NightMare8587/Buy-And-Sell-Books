package com.consumers.librarymanagementsystem.Home.SellBooks.Sell;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.consumers.librarymanagementsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

public class ExpandSellBookActivity extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_sell_book);
        imageView = findViewById(R.id.imageViewExpandSellBooks);
        Picasso.get().load(getIntent().getStringExtra("imageUri"));

    }
}