package com.consumers.librarymanagementsystem.Home.Frags;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.consumers.librarymanagementsystem.Home.SellBooks.Sell.SellYourBooks;
import com.consumers.librarymanagementsystem.MainActivity;
import com.consumers.librarymanagementsystem.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class MyAccountFrag extends Fragment {
    List<String> list = new ArrayList<>();
    ListView listView;
    GoogleSignInClient googleSignInClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_account_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list.add("Sell Books");
        list.add("My Preferences");
        list.add("Logout");
        listView = view.findViewById(R.id.myAccountListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(requireContext(),gso);
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            switch (i){
                case 0:
                    startActivity(new Intent(view.getContext(), SellYourBooks.class));
                    break;
                case 1:
                    Toast.makeText(view1.getContext(), "My Pref", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setTitle("Logout")
                            .setMessage("Do you wanna logout?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    FirebaseAuth auth = FirebaseAuth.getInstance();
                                    auth.signOut();
                                    googleSignInClient.signOut().addOnCompleteListener((Activity) getContext(), task -> {

                                    });
                                    SharedPreferences settings = view1.getContext().getSharedPreferences("loginInfo", MODE_PRIVATE);
                                    settings.edit().clear().commit();

                                    SharedPreferences intro = view1.getContext().getSharedPreferences("BooksPref", MODE_PRIVATE);
                                    intro.edit().clear().commit();

                                    startActivity(new Intent(getActivity(), MainActivity.class));
                                    getActivity().finish();
                                }
                            })
                            .setNegativeButton("No", (dialogInterface, i1) -> dialogInterface.dismiss()).create();

                    builder.show();
                    break;
            }
        });
    }
}
