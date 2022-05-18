package com.consumers.librarymanagementsystem.Home.ExpandBooksDetails;

import androidx.annotation.NonNull;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.consumers.librarymanagementsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GetBookDetails extends AppCompatActivity {
    TextView bookName,bookGenre,publsihed,author,bookPrice;
    Button buyThisBook;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    ImageView imageView;
    String URL = "https://fcm.googleapis.com/fcm/send";
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_book_details);
        bookName = findViewById(R.id.bookDetialsBookName);
        bookGenre = findViewById(R.id.bookDetailsGenre);
        bookPrice = findViewById(R.id.bookPriceGetDetails);
        bookPrice.setText("(\u20B9" + getIntent().getStringExtra("price") + ")");
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
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().getRoot().child("Users").child(getIntent().getStringExtra("sellerID")).child("My Books").child(getIntent().getStringExtra("bookName")).child("Orders");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            if(snapshot.hasChild(Objects.requireNonNull(auth.getUid()))){
                                Toast.makeText(GetBookDetails.this, "You have already placed order for this book", Toast.LENGTH_SHORT).show();
                            }else{
                                RequestQueue requestQueue = Volley.newRequestQueue(GetBookDetails.this);
                                JSONObject main = new JSONObject();
                                try {
                                    main.put("to", "/topics/" + getIntent().getStringExtra("sellerID") + "");
                                    JSONObject notification = new JSONObject();
                                    notification.put("title", "Book Order");
//                                    Toast.makeText(GetBookDetails.this, "" + getIntent().getStringExtra("sellerID"), Toast.LENGTH_SHORT).show();
                                    notification.put("body", "" + "Order for your book\n" + getIntent().getStringExtra("bookName"));
                                    main.put("notification", notification);

                                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, main, response -> {

                                    }, error -> Toast.makeText(getApplicationContext(), error.getLocalizedMessage() + "null", Toast.LENGTH_SHORT).show()) {
                                        @Override
                                        public Map<String, String> getHeaders() {
                                            Map<String, String> header = new HashMap<>();
                                            header.put("content-type", "application/json");
                                            header.put("authorization", "key=AAAA76i_om0:APA91bF20tIdfXbz_e3Y0-mLOsTsEsz0s-KhxPpOVz7v_Ew3v_IohB9AzwVSDtSQLlfPxgNzH4Vi-vv3tFBYAHxlkpmSEnRgSuxAc1Gyoe8RnDTbiIgDhs52YeoHs_wwK6AcrJrtqXRM");
                                            return header;
                                        }
                                    };

                                    requestQueue.add(jsonObjectRequest);
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage() + "null", Toast.LENGTH_SHORT).show();
                                }
                                BuyBookClass buyBookClass = new BuyBookClass(sharedPreferences.getString("name",""),auth.getUid() + "",sharedPreferences.getString("contactNumUser",""));
                                databaseReference.child(auth.getUid()).setValue(buyBookClass);
                                Toast.makeText(GetBookDetails.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().getRoot().child("Users").child(auth.getUid()).child("My Orders Placed");
                                reference.child(getIntent().getStringExtra("bookName")).child("sellerID").setValue(getIntent().getStringExtra("sellerID"));
                            }
                        }else{
                            RequestQueue requestQueue = Volley.newRequestQueue(GetBookDetails.this);
                            JSONObject main = new JSONObject();
                            try {
                                main.put("to", "/topics/" + getIntent().getStringExtra("sellerID") + "");
                                JSONObject notification = new JSONObject();
                                notification.put("title", "Book Order");
//                                Toast.makeText(GetBookDetails.this, "" + getIntent().getStringExtra("sellerID"), Toast.LENGTH_SHORT).show();
                                notification.put("body", "" + "Order for your book\n" + getIntent().getStringExtra("bookName"));
                                main.put("notification", notification);

                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, main, response -> {

                                }, error -> Toast.makeText(getApplicationContext(), error.getLocalizedMessage() + "null", Toast.LENGTH_SHORT).show()) {
                                    @Override
                                    public Map<String, String> getHeaders() {
                                        Map<String, String> header = new HashMap<>();
                                        header.put("content-type", "application/json");
                                        header.put("authorization", "key=AAAA76i_om0:APA91bF20tIdfXbz_e3Y0-mLOsTsEsz0s-KhxPpOVz7v_Ew3v_IohB9AzwVSDtSQLlfPxgNzH4Vi-vv3tFBYAHxlkpmSEnRgSuxAc1Gyoe8RnDTbiIgDhs52YeoHs_wwK6AcrJrtqXRM");
                                        return header;
                                    }
                                };

                                requestQueue.add(jsonObjectRequest);
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), e.getLocalizedMessage() + "null", Toast.LENGTH_SHORT).show();
                            }
                            BuyBookClass buyBookClass = new BuyBookClass(sharedPreferences.getString("name",""),auth.getUid() + "",sharedPreferences.getString("contactNumUser",""));
                            databaseReference.child(auth.getUid()).setValue(buyBookClass);
                            Toast.makeText(GetBookDetails.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().getRoot().child("Users").child(auth.getUid()).child("My Orders Placed");
                            reference.child(getIntent().getStringExtra("bookName")).child("sellerID").setValue(getIntent().getStringExtra("sellerID"));

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                BuyBookClass buyBookClass = new BuyBookClass(sharedPreferences.getString("name",""),String.valueOf(auth.getUid()),sharedPreferences.getString("contactNumUser",""));

            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(GetBookDetails.this);
                LinearLayout layout = new LinearLayout(GetBookDetails.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                EditText editText = new EditText(GetBookDetails.this);
                editText.setHint("Enter Number Here");
                editText.setInputType(InputType.TYPE_CLASS_PHONE);
                builder.setTitle("Contact").setMessage("You need to add your contact number before purchasing this book");
                builder.setPositiveButton("Proceed", (dialogInterface, i) -> {
                    if(editText.getText().toString().length() == 10){
                        Toast.makeText(GetBookDetails.this, "Contact Info Saved Successfully", Toast.LENGTH_SHORT).show();
                        editor.putString("contactNumUser",editText.getText().toString());
                        editor.apply();
                    }else
                        Toast.makeText(GetBookDetails.this, "Enter 10 digit Number", Toast.LENGTH_SHORT).show();

                }).setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss()).create();
                layout.addView(editText);
                builder.setView(layout);
                builder.show();
            }
        });
    }
}