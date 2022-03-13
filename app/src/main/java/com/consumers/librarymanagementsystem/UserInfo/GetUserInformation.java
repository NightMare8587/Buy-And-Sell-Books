package com.consumers.librarymanagementsystem.UserInfo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.consumers.librarymanagementsystem.Home.HomeScreen;
import com.consumers.librarymanagementsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class GetUserInformation extends AppCompatActivity {
    CheckBox horror,romance,scifi,fictional,bio,selfhelp,motivational,mystry;
    boolean horrorB,romanceB,scifiB,fictionalB,bioB,selfB,motiB,mysB;
    Button continueB;
    int count = 1;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_information);
        initialise();
        continueB = findViewById(R.id.ContinueButton);
        SharedPreferences BooksPref = getSharedPreferences("BooksPref",MODE_PRIVATE);
        if(BooksPref.contains("booksPref")){
            startActivity(new Intent(GetUserInformation.this, HomeScreen.class));
            finish();
        }
        SharedPreferences.Editor editor = BooksPref.edit();
        editor.putString("horror","true");
        continueB.setOnClickListener(click -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(GetUserInformation.this);
            builder.setTitle("Confirmation").setMessage("Do you wanna continue with your preferences")
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                        if(count <= 4){
                            editor.putString("booksPref","yes");
                            editor.apply();
                            storeInfo();
                        }else
                            Toast.makeText(this, "Max Preferences are 4", Toast.LENGTH_SHORT).show();
                    }).setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
        });
        horror.setOnCheckedChangeListener((compoundButton, b) -> {
            if(compoundButton.isChecked())
            {
                editor.putString("horror","true");
                editor.apply();
                horrorB = true;
                count++;
            }else{
                editor.remove("horror");
                editor.apply();
                horrorB = false;
                count--;
            }
        });

        fictional.setOnCheckedChangeListener((compoundButton, b) -> {
            if(compoundButton.isChecked())
            {
                editor.putString("fictional","true");
                editor.apply();
                fictionalB = true;
                count++;
            }else{
                editor.remove("fictional");
                editor.apply();
                fictionalB = false;
                count--;
            }
        });
        scifi.setOnCheckedChangeListener((compoundButton, b) -> {
            if(compoundButton.isChecked())
            {
                editor.putString("scifi","true");
                editor.apply();
                scifiB = true;
                count++;
            }else{
                editor.remove("scifi");
                editor.apply();
                scifiB = false;
                count--;
            }
        });
        romance.setOnCheckedChangeListener((compoundButton, b) -> {
            if(compoundButton.isChecked())
            {
                editor.putString("romance","true");
                editor.apply();
                romanceB = true;
                count++;
            }else{
                editor.remove("romance");
                editor.apply();
                romanceB = false;
                count--;
            }
        });
        mystry.setOnCheckedChangeListener((compoundButton, b) -> {
            if(compoundButton.isChecked())
            {
                editor.putString("mystery","true");
                editor.apply();
                mysB = true;
                count++;
            }else{
                editor.remove("mystery");
                editor.apply();
                mysB = false;
                count--;
            }
        });
        bio.setOnCheckedChangeListener((compoundButton, b) -> {
            if(compoundButton.isChecked())
            {
                editor.putString("bio","true");
                editor.apply();
                bioB = true;
                count++;
            }else{
                editor.remove("bio");
                editor.apply();
                motiB = false;
                count--;
            }
        });
        selfhelp.setOnCheckedChangeListener((compoundButton, b) -> {
            if(compoundButton.isChecked())
            {
                editor.putString("selfHelp","true");
                editor.apply();
                selfB = true;
                count++;
            }else{
                editor.remove("selfHelp");
                editor.apply();
                selfB = false;
                count--;
            }
        });
        motivational.setOnCheckedChangeListener((compoundButton, b) -> {
            if(compoundButton.isChecked())
            {
                editor.putString("motivational","true");
                editor.apply();
                motiB = true;
                count++;
            }else{
                editor.remove("motivational");
                editor.apply();
                motiB = false;
                count--;
            }
        });
    }

    private void storeInfo() {
        if(horrorB){
            databaseReference.child("BooksPref").child("horror").setValue("yes");
        }
        if(romanceB){
            databaseReference.child("BooksPref").child("romance").setValue("yes");
        }
        if(scifiB){
            databaseReference.child("BooksPref").child("scifi").setValue("yes");
        }
        if(motiB){
            databaseReference.child("BooksPref").child("motivational").setValue("yes");
        }
        if(mysB){
            databaseReference.child("BooksPref").child("mystery").setValue("yes");
        }
        if(bioB){
            databaseReference.child("BooksPref").child("bio").setValue("yes");
        }
        if(selfB){
            databaseReference.child("BooksPref").child("selfHelp").setValue("yes");
        }
        if(fictionalB){
            databaseReference.child("BooksPref").child("fictional").setValue("yes");
        }
        Toast.makeText(this, "Preferences Saved Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(GetUserInformation.this,HomeScreen.class));
        finish();
    }

    private void initialise() {
        horrorB = true;
        scifiB = false;
        mysB = false;
        selfB = false;
        motiB = false;
        romanceB = false;
        fictionalB = false;
        bioB = false;
        horror = findViewById(R.id.horror);
        romance = findViewById(R.id.romance);
        scifi = findViewById(R.id.SciFi);
        fictional = findViewById(R.id.Fictional);
        bio = findViewById(R.id.Biography);
        selfhelp = findViewById(R.id.selfHelp);
        motivational = findViewById(R.id.Motivational);
        mystry = findViewById(R.id.Mystery);
        databaseReference = FirebaseDatabase.getInstance().getReference().getRoot().child("Users").child(Objects.requireNonNull(auth.getUid()));
    }
}