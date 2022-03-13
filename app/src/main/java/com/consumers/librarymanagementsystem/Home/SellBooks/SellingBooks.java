package com.consumers.librarymanagementsystem.Home.SellBooks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.consumers.librarymanagementsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class SellingBooks extends AppCompatActivity {
    EditText nameOfBook,price;
    Spinner spinner;
    String nameOfBooks,prices;
    boolean imageUploaded = false;
    Uri filepath;
    FirebaseAuth dishAuth = FirebaseAuth.getInstance();
    FirebaseStorage storage;
    StorageReference storageReference;
    Button image,sellBook;
    DatabaseReference databaseReference;
    DatabaseReference reference;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String genre = "motivational";
    private static final String[] paths = {"horror", "motivational", "fictional", "Sci-fi", "Biography", "Romance", "Mystery", "Self Help"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling_books);
        nameOfBook = findViewById(R.id.editTextTextPersonName);
        price = findViewById(R.id.priceOfSellingBook);
        spinner = findViewById(R.id.spinner);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().getRoot().child("BooksDatabase");
        reference = FirebaseDatabase.getInstance().getReference().getRoot().child("Users").child(Objects.requireNonNull(auth.getUid()));
        sellBook = findViewById(R.id.continueButtonSelling);
        image = findViewById(R.id.uploadCoverPageImage);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SellingBooks.this,
                android.R.layout.simple_spinner_item,paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(1);
//        spinner.setOnItemClickListener((adapterView, view, i, l) -> {

//        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        genre = "horror";
                        break;
                    case 1:
                        genre = "motivational";
                        break;
                    case 2:
                        genre = "fictional";
                        break;
                    case 3:
                        genre = "scifi";
                        break;
                    case 4:
                        genre = "bio";
                        break;
                    case 5:
                        genre = "romance";
                        break;
                    case 6:
                        genre = "mystery";
                        break;
                    case 7:
                        genre = "selfHelp";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        image.setOnClickListener(click -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture Using"), 1);
        });

        sellBook.setOnClickListener(click -> {
            if(nameOfBook.length() == 0)
                Toast.makeText(this, "Field can't be empty", Toast.LENGTH_SHORT).show();
            else{
                if(price.length() == 0)
                {
                    Toast.makeText(this, "Enter Some Price", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!imageUploaded)
                {
                    Toast.makeText(this, "Upload Cover Page Of Book", Toast.LENGTH_SHORT).show();
                    return;
                }

                nameOfBooks = nameOfBook.getText().toString();
                prices = price.getText().toString();

                new UploadInBackground().execute();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null){
            filepath = data.getData();
            imageUploaded = true;
        }
    }

    public class UploadInBackground extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                StorageReference reference = storageReference.child(dishAuth.getUid() + "/" + "Books" + "/" + nameOfBooks);
                reference.putFile(filepath).addOnCompleteListener(task -> {
                    Toast.makeText(SellingBooks.this, "Upload Complete", Toast.LENGTH_SHORT).show();
                    StorageReference reference1 = storageReference.child(dishAuth.getUid() + "/" + "Books" + "/"  + nameOfBooks);
                    reference1.getDownloadUrl().addOnSuccessListener(uri -> {
                        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo",MODE_PRIVATE);
                        BooksInfoDB booksInfoDB = new BooksInfoDB(genre,uri+"",sharedPreferences.getString("name",""),dishAuth.getUid() + "",sharedPreferences.getString("email",""),prices);
                        databaseReference.child(genre).child(nameOfBooks).setValue(booksInfoDB);
                        MyBookDB myBookDB = new MyBookDB(genre,prices,"0");
                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().getRoot().child("Users").child(dishAuth.getUid()).child("My Books");
                        myRef.child(nameOfBooks).setValue(myBookDB);
                        finish();
                    });
                }).addOnFailureListener(e -> Toast.makeText(SellingBooks.this, "Failed. Try again later", Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                Toast.makeText(SellingBooks.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
            return null;
        }
    }
}