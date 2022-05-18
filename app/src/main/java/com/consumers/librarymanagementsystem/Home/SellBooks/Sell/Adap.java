package com.consumers.librarymanagementsystem.Home.SellBooks.Sell;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adap extends RecyclerView.Adapter<Adap.Holder> {
    List<String> bookName = new ArrayList<>();
    List<String> bookGenre = new ArrayList<>();
    List<String> bookPrice = new ArrayList<>();
    List<String> purchaseCount = new ArrayList<>();
    List<String> imageUri = new ArrayList<>();
    List<String> orders = new ArrayList<>();

    public Adap(List<String> bookName, List<String> bookGenre, List<String> bookPrice, List<String> purchaseCount,List<String> imageUri,List<String> orders) {
        this.bookName = bookName;
        this.orders= orders;
        this.imageUri = imageUri;
        this.bookGenre = bookGenre;
        this.bookPrice = bookPrice;
        this.purchaseCount = purchaseCount;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adap_layout,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.name.setText(bookName.get(position));
        holder.genre.setText(bookGenre.get(position));
        holder.purchaseCount.setText(purchaseCount.get(position));
        Picasso.get().load(imageUri.get(position)).into(holder.imageView);
        if(orders.get(position).equals("1")){
            holder.ordersText.setVisibility(View.VISIBLE);
        }else
            holder.ordersText.setVisibility(View.INVISIBLE);


        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Dialog").setMessage("Choose one option from below").setPositiveButton("Delete Book", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().getRoot().child("Users").child(auth.getUid()).child("My Books").child(bookName.get(position));
                        databaseReference.removeValue();
                        databaseReference = FirebaseDatabase.getInstance().getReference().getRoot().child("BooksDatabase").child(bookGenre.get(position)).child(bookName.get(position));
                        databaseReference.removeValue();
                        Toast.makeText(view.getContext(), "Book Removed Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
                builder.show();
                return true;
            }
        });
        holder.cardView.setOnClickListener(click -> {
            if(orders.get(position).equals("1")){
                Intent intent = new Intent(click.getContext(),ExpandSellBookActivity.class);
                intent.putExtra("bookName",bookName.get(position));
                intent.putExtra("bookGenre",bookGenre.get(position));
                intent.putExtra("imageUri",imageUri.get(position));
                intent.putExtra("purchase",purchaseCount.get(position));
                intent.putExtra("orderAvailable","1");
                click.getContext().startActivity(intent);
            }else{
                Intent intent = new Intent(click.getContext(),ExpandSellBookActivity.class);
                intent.putExtra("bookName",bookName.get(position));
                intent.putExtra("bookGenre",bookGenre.get(position));
                intent.putExtra("imageUri",imageUri.get(position));
                intent.putExtra("purchase",purchaseCount.get(position));
                intent.putExtra("orderAvailable","0");
                click.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookName.size();
    }
    public class Holder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name,genre,purchaseCount,ordersText;
        CardView cardView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewAdapFrag);
            name = itemView.findViewById(R.id.bookNameAdapFrag);
            genre = itemView.findViewById(R.id.bookGenreAdap);
            cardView = itemView.findViewById(R.id.adapLayoutCard);
            ordersText = itemView.findViewById(R.id.myOrderTextView);
            purchaseCount = itemView.findViewById(R.id.purchaseCountAdap);
        }
    }
}
