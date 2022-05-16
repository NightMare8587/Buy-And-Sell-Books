package com.consumers.librarymanagementsystem.Home.Frags;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.consumers.librarymanagementsystem.Home.BooksAdapter;
import com.consumers.librarymanagementsystem.Home.ExpandBooksDetails.GetBookDetails;
import com.consumers.librarymanagementsystem.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AllBooksAdp extends RecyclerView.Adapter<AllBooksAdp.Holder> {
    List<String> image;
    List<String> bookName = new ArrayList<>();
    List<String> genre = new ArrayList<>();
    List<String> price = new ArrayList<>();
    List<String> sellerEmail = new ArrayList<>();
    List<String> sellerID = new ArrayList<>();
    List<String> sellerName = new ArrayList<>();
    List<String> authorName = new ArrayList<>();
    List<String> publisherName = new ArrayList<>();
    List<String> contactNumber = new ArrayList<>();

    public AllBooksAdp(List<String> image, List<String> bookName, List<String> genre, List<String> price, List<String> sellerEmail, List<String> sellerID, List<String> sellerName,List<String> authorName,List<String> publisherName,List<String> contactNumber) {
        this.image = image;
        this.bookName = bookName;
        this.contactNumber = contactNumber;
        this.publisherName = publisherName;
        this.authorName = authorName;
        this.genre = genre;
        this.price = price;
        this.sellerEmail = sellerEmail;
        this.sellerID = sellerID;
        this.sellerName = sellerName;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.books_adapter_layout,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Picasso.get().load(image.get(position)).into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {
                holder.progressBar.setVisibility(View.INVISIBLE);
            }
        });
        holder.textView.setText(bookName.get(position));
        holder.cardView.setOnClickListener(click -> {
            Intent intent = new Intent(click.getContext(), GetBookDetails.class);
            intent.putExtra("genre",genre.get(position));
            intent.putExtra("price",price.get(position));
            intent.putExtra("authorName",authorName.get(position));
            intent.putExtra("bookName",bookName.get(position));
            intent.putExtra("imageUri",image.get(position));
            intent.putExtra("sellerName",sellerName.get(position));
            intent.putExtra("sellerID",sellerID.get(position));
            intent.putExtra("contactNum",contactNumber.get(position));
            intent.putExtra("publisherName",publisherName.get(position));
            click.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bookName.size();
    }
    public class Holder extends RecyclerView.ViewHolder{
        ImageView imageView;
        ProgressBar progressBar;
        TextView textView;
        CardView cardView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.adapterImageView);
            textView = itemView.findViewById(R.id.bookNameTextAdapter);
            progressBar = itemView.findViewById(R.id.progressBar);
            cardView = itemView.findViewById(R.id.bookADPcardView);
        }
    }
}
