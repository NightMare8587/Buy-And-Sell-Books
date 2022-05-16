package com.consumers.librarymanagementsystem.Home.Frags;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.consumers.librarymanagementsystem.Home.ExpandBooksDetails.GetBookDetails;
import com.consumers.librarymanagementsystem.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchFragAdapter extends RecyclerView.Adapter<SearchFragAdapter.Holder> {
    List<String> genreList = new ArrayList<>();
    List<String> bookNameList = new ArrayList<>();
    List<String> bookAuthorList = new ArrayList<>();
    List<String> bookImageUri = new ArrayList<>();
    List<String> sellerName = new ArrayList<>();
    List<String> sellerID = new ArrayList<>();
    List<String> publisherName = new ArrayList<>();
    List<String> contactNum = new ArrayList<>();
    List<String> price = new ArrayList<>();

    public SearchFragAdapter(List<String> genreList, List<String> bookNameList, List<String> bookAuthorList, List<String> bookImageUri, List<String> sellerName, List<String> sellerID, List<String> publisherName, List<String> contactNum, List<String> price) {
        this.genreList = genreList;
        this.bookNameList = bookNameList;
        this.bookAuthorList = bookAuthorList;
        this.bookImageUri = bookImageUri;
        this.sellerName = sellerName;
        this.sellerID = sellerID;
        this.publisherName = publisherName;
        this.contactNum = contactNum;
        this.price = price;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.search_books_card_view,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Picasso.get().load(bookImageUri.get(position)).into(holder.imageView);
        holder.name.setText(bookNameList.get(position));
        holder.genre.setText(genreList.get(position));
        holder.cardView.setOnClickListener(click -> {
            Intent intent = new Intent(click.getContext(), GetBookDetails.class);
            intent.putExtra("genre",genreList.get(position));
            intent.putExtra("price",price.get(position));
            intent.putExtra("authorName",bookAuthorList.get(position));
            intent.putExtra("bookName",bookNameList.get(position));
            intent.putExtra("imageUri",bookImageUri.get(position));
            intent.putExtra("sellerName",sellerName.get(position));
            intent.putExtra("sellerID",sellerID.get(position));
            intent.putExtra("contactNum",contactNum.get(position));
            intent.putExtra("publisherName",publisherName.get(position));
            click.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }
    public class Holder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name,genre;
        CardView cardView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewSearchFrag);
            name = itemView.findViewById(R.id.bookNameSearchFrag);
            genre = itemView.findViewById(R.id.bookGenreTextView);
            cardView = itemView.findViewById(R.id.searchCardView);
        }
    }
}
