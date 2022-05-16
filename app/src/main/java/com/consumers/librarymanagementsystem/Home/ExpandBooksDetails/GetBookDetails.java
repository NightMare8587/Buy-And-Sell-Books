package com.consumers.librarymanagementsystem.Home.ExpandBooksDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.consumers.librarymanagementsystem.R;
import com.squareup.picasso.Picasso;

public class GetBookDetails extends AppCompatActivity {
    TextView bookName,bookGenre;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_book_details);
        bookName = findViewById(R.id.bookDetialsBookName);
        bookGenre = findViewById(R.id.bookDetailsGenre);
        bookName.setText(getIntent().getStringExtra("bookName"));
        bookGenre.setText(getIntent().getStringExtra("genre"));
        imageView = findViewById(R.id.getBookDetailsImageView);
        Picasso.get().load(getIntent().getStringExtra("imageUri")).into(imageView);
    }
}