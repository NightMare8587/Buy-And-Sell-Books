package com.consumers.librarymanagementsystem.Home.ExpandBooksDetails;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.consumers.librarymanagementsystem.R;
import com.squareup.picasso.Picasso;

public class GetBookDetails extends AppCompatActivity {
    TextView bookName,bookGenre,publsihed,author;
    Button buyThisBook;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView imageView;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_book_details);
        bookName = findViewById(R.id.bookDetialsBookName);
        bookGenre = findViewById(R.id.bookDetailsGenre);
        publsihed = findViewById(R.id.publisherBookDetails);
        buyThisBook = findViewById(R.id.BuyBookNowButton);
        author = findViewById(R.id.authorNameBookDetails);
        bookName.setText(getIntent().getStringExtra("bookName"));
        bookGenre.setText(getIntent().getStringExtra("genre"));
        author.setText("Author: " + getIntent().getStringExtra("authorName"));
        publsihed.setText("Published By: " + getIntent().getStringExtra("publisherName"));
        imageView = findViewById(R.id.getBookDetailsImageView);
        Picasso.get().load(getIntent().getStringExtra("imageUri")).into(imageView);
        sharedPreferences = getSharedPreferences("loginInfo",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        buyThisBook.setOnClickListener(click -> {
            if(sharedPreferences.contains("contactNumUser")){
                
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(GetBookDetails.this);
                LinearLayout layout = new LinearLayout(GetBookDetails.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                EditText editText = new EditText(GetBookDetails.this);
                editText.setHint("Enter Number Here");
                editText.setInputType(InputType.TYPE_CLASS_PHONE);
                builder.setTitle("Contact").setMessage("You need to add your contact number before purchasing this book");
                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(editText.getText().toString().length() == 10){
                            Toast.makeText(GetBookDetails.this, "Contact Info Saved Successfully", Toast.LENGTH_SHORT).show();
                            editor.putString("contactNumUser",editText.getText().toString());
                            editor.apply();
                        }else
                            Toast.makeText(GetBookDetails.this, "Enter 10 digit Number", Toast.LENGTH_SHORT).show();

                    }
                }).setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss()).create();
                layout.addView(editText);
                builder.setView(layout);
                builder.show();
            }
        });
    }
}