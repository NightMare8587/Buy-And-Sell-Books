package com.consumers.librarymanagementsystem.Home.Frags.MyOrders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.consumers.librarymanagementsystem.R;

import java.util.ArrayList;
import java.util.List;

public class showAdapterOrders extends RecyclerView.Adapter<showAdapterOrders.holder> {
    List<String> buyerID = new ArrayList<>();
    List<String> buyerName = new ArrayList<>();
    List<String> buyerContactNum = new ArrayList<>();

    public showAdapterOrders(List<String> buyerID, List<String> buyerName, List<String> buyerContactNum) {
        this.buyerID = buyerID;
        this.buyerName = buyerName;
        this.buyerContactNum = buyerContactNum;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.show_orders_card_view,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.name.setText(buyerName.get(position));
        holder.contact.setText(buyerContactNum.get(position));
        holder.accept.setOnClickListener(click -> {

        });

        holder.reject.setOnClickListener(click -> {

        });
    }

    @Override
    public int getItemCount() {
        return buyerID.size();
    }
    public class holder extends RecyclerView.ViewHolder{
        Button call,accept,reject;
        TextView name,contact;
        public holder(@NonNull View itemView) {
            super(itemView);
            call = itemView.findViewById(R.id.callNowContactCardView);
            accept = itemView.findViewById(R.id.acceptOrderCard);
            reject = itemView.findViewById(R.id.declineOrderCard);
            name = itemView.findViewById(R.id.buyerNameTextViewCard);
            contact = itemView.findViewById(R.id.buyerContactNumCardView);
        }
    }
}
