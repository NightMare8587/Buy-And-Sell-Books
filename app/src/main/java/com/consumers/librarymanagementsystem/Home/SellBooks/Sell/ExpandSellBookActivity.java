package com.consumers.librarymanagementsystem.Home.SellBooks.Sell;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.consumers.librarymanagementsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ExpandSellBookActivity extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference;
    DatabaseReference reference;
    TextView bookName,bookGenre;
    Button checkOrders,changeBookDetails;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_sell_book);
        imageView = findViewById(R.id.imageViewExpandSellBooks);
        checkOrders = findViewById(R.id.checkAvailableOrders);
        bookGenre = findViewById(R.id.bookGenreExpandSellBook);
        databaseReference = FirebaseDatabase.getInstance().getReference().getRoot().child("Users").child(auth.getUid()).child("My Books").child(getIntent().getStringExtra("bookName"));
        reference = FirebaseDatabase.getInstance().getReference().getRoot().child("BooksDatabase").child(getIntent().getStringExtra("bookGenre")).child(getIntent().getStringExtra("bookName"));
        bookName  = findViewById(R.id.bookNameExpandSell);
        changeBookDetails = findViewById(R.id.changeBookDetailsButton);
        bookName.setText(getIntent().getStringExtra("bookName"));
        bookGenre.setText(getIntent().getStringExtra("bookGenre"));
        if(getIntent().getStringExtra("orderAvailable").equals("1"))
            checkOrders.setVisibility(View.VISIBLE);
        else checkOrders.setVisibility(View.INVISIBLE);
        Picasso.get().load(getIntent().getStringExtra("imageUri")).into(imageView);


        changeBookDetails.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ExpandSellBookActivity.this);
            builder.setTitle("Dialog").setMessage("Keep field empty if changes not needed");
            LinearLayout linearLayout = new LinearLayout(ExpandSellBookActivity.this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            EditText bookAuthorChange = new EditText(ExpandSellBookActivity.this);
            bookAuthorChange.setHint("Enter new Author Name");
            EditText bookPriceChange = new EditText(ExpandSellBookActivity.this);
            bookPriceChange.setHint("Enter new Price");
            bookPriceChange.setInputType(InputType.TYPE_CLASS_NUMBER);
            EditText bookPubChange = new EditText(ExpandSellBookActivity.this);
            bookPubChange.setHint("Enter New Publisher Name");
            builder.setPositiveButton("Make Changes", (dialogInterface, i) -> {
                if(!bookAuthorChange.getText().toString().equals("")){
                    databaseReference.child("authorName").setValue(bookAuthorChange.getText().toString());
                    reference.child("authorName").setValue(bookAuthorChange.getText().toString());
                }
                if(!bookPriceChange.getText().toString().equals("") && !bookPriceChange.getText().toString().equals("0")){
                    databaseReference.child("price").setValue(bookPriceChange.getText().toString());
                    reference.child("price").setValue(bookPriceChange.getText().toString());
                }
                if(!bookPubChange.getText().toString().equals("")){
                    databaseReference.child("publisherName").setValue(bookPubChange.getText().toString());
                    reference.child("publisherName").setValue(bookPubChange.getText().toString());
                }

                Toast.makeText(ExpandSellBookActivity.this, "Changes Saved Successfully", Toast.LENGTH_SHORT).show();
            }).setNegativeButton("Cancel", (dialogInterface, i) -> Toast.makeText(ExpandSellBookActivity.this, "Cancelled", Toast.LENGTH_SHORT).show()).create();
            linearLayout.addView(bookAuthorChange);
            linearLayout.addView(bookPriceChange);
            linearLayout.addView(bookPubChange);
            builder.setView(linearLayout);
            builder.show();
        });
    }
}