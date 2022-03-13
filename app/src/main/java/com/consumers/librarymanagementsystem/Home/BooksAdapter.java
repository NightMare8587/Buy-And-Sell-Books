package com.consumers.librarymanagementsystem.Home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.consumers.librarymanagementsystem.R;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.Holder> {
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.books_adapter_layout,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public class Holder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.adapterImageView);
            textView = itemView.findViewById(R.id.bookNameTextAdapter);
        }
    }
}
