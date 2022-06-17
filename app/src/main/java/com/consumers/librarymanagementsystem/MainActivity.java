package com.consumers.librarymanagementsystem;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.consumers.librarymanagementsystem.UserInfo.GetUserInformation;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    GoogleSignInAccount account;
    GoogleSignInClient client;
    GoogleSignInOptions gso;
    DatabaseReference reference;
    FirebaseAuth loginAuth = FirebaseAuth.getInstance();
    SignInButton signInButton;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FirebaseUser currentUser;
    String key = "1029328314989-ffneht0ddk3ctl1d7ebp3tpleo1b0lab.apps.googleusercontent.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        checkPermissions();
        if(currentUser != null){
            startActivity(new Intent(MainActivity.this,GetUserInformation.class));
            finish();
        }
        sharedPreferences = getSharedPreferences("loginInfo",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        signInButton = findViewById(R.id.sign_in_button);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(key)
                .requestEmail()
                .build();

        client = GoogleSignIn.getClient(MainActivity.this,gso);

        signInButton.setOnClickListener(click -> {
            Intent signIn = client.getSignInIntent();
            startActivityForResult(signIn,3);
        });
    }
    private void checkPermissions() {
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE+ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) + ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }else{
                checkPermissions();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // code 101 for location request && code 3 is for google sign in
         if(requestCode == 3){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            Toast.makeText(this, "Sign In", Toast.LENGTH_SHORT).show();
            createFirebaseAuthID(account.getIdToken());
            getSignInInformation();

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, e.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
        }
    }



    private void createFirebaseAuthID(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        loginAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithCredential:success");
                        loginAuth = FirebaseAuth.getInstance();
                        FirebaseUser user = loginAuth.getCurrentUser();
                        assert user != null;
                        loginAuth.updateCurrentUser(user);
                        reference = FirebaseDatabase.getInstance().getReference().getRoot();
                        reference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(!snapshot.hasChild(Objects.requireNonNull(loginAuth.getUid()))){
//                                        reference.child("Users").child(Objects.requireNonNull(loginAuth.getUid())).setValue(user);
                                    reference = FirebaseDatabase.getInstance().getReference().getRoot().child("Users").child(user.getUid());
                                    GoogleSignInDB googleSignInDB = new GoogleSignInDB(account.getDisplayName(),account.getEmail());
                                    reference.setValue(googleSignInDB);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        startActivity(new Intent(MainActivity.this, GetUserInformation.class));
                        finish();
//                            updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithCredential:failure", task.getException());
//                            updateUI(null);
                    }
                });
    }
    private void getSignInInformation() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            editor.putString("email",personEmail);
            editor.putString("name",personName);
            editor.apply();
            Log.i("info",personName+ " " + personEmail + " " + personFamilyName);
        }
    }
}