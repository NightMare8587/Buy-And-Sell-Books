package com.consumers.librarymanagementsystem.Home.Frags.MyOrders;



import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.consumers.librarymanagementsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CheckClass extends RecyclerView.Adapter<CheckClass.Holder> {
    List<String> bookName = new ArrayList<>();
    List<String> sellerID = new ArrayList<>();

    public CheckClass(List<String> bookName,List<String> sellerID) {
        this.bookName = bookName;
        this.sellerID = sellerID;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.check_class_card_view,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.textView.setText(bookName.get(position));
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Dialog").setMessage("Choose one option").setPositiveButton("Delete Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().getRoot().child("Users").child(auth.getUid()).child("My Orders Placed").child(bookName.get(position));
                        databaseReference.removeValue();
                        databaseReference = FirebaseDatabase.getInstance().getReference().getRoot().child("Users").child(sellerID.get(position)).child("My Books").child(bookName.get(position)).child("Orders").child(auth.getUid());
                        databaseReference.removeValue();
                        Toast.makeText(view.getContext(), "Order Cancelled Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create().show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookName.size();
    }
    public class Holder extends RecyclerView.ViewHolder{
        TextView textView;
        CardView cardView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.bookNameCheckClassCard);
            cardView = itemView.findViewById(R.id.checkClassCardID);
        }
    }
}
