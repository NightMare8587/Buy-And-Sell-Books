package com.consumers.librarymanagementsystem.Home.Frags.MyOrders;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.consumers.librarymanagementsystem.Home.ExpandBooksDetails.GetBookDetails;
import com.consumers.librarymanagementsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class showAdapterOrders extends RecyclerView.Adapter<showAdapterOrders.holder> {
    List<String> buyerID = new ArrayList<>();
    List<String> buyerName = new ArrayList<>();
    List<String> buyerContactNum = new ArrayList<>();
    String URL = "https://fcm.googleapis.com/fcm/send";
    String bookName;

    public showAdapterOrders(List<String> buyerID, List<String> buyerName, List<String> buyerContactNum,String bookName) {
        this.buyerID = buyerID;
        this.bookName = bookName;
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
            RequestQueue requestQueue = Volley.newRequestQueue(click.getContext());
            JSONObject main = new JSONObject();
            try {
                main.put("to", "/topics/" + buyerID.get(position) + "");
                JSONObject notification = new JSONObject();
                notification.put("title", "Order Accepted");
                FirebaseAuth auth = FirebaseAuth.getInstance();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().getRoot().child("Users").child(auth.getUid()).child("My Books").child(bookName).child("Orders");
                databaseReference.child(buyerID.get(position)).removeValue();
//                                    Toast.makeText(GetBookDetails.this, "" + getIntent().getStringExtra("sellerID"), Toast.LENGTH_SHORT).show();
                notification.put("body", "" + "Order for your book " + bookName +" is accepted");
                main.put("notification", notification);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, main, response -> {

                }, error -> Toast.makeText(click.getContext(), error.getLocalizedMessage() + "null", Toast.LENGTH_SHORT).show()) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> header = new HashMap<>();
                        header.put("content-type", "application/json");
                        header.put("authorization", "key=AAAA76i_om0:APA91bF20tIdfXbz_e3Y0-mLOsTsEsz0s-KhxPpOVz7v_Ew3v_IohB9AzwVSDtSQLlfPxgNzH4Vi-vv3tFBYAHxlkpmSEnRgSuxAc1Gyoe8RnDTbiIgDhs52YeoHs_wwK6AcrJrtqXRM");
                        return header;
                    }
                };

                requestQueue.add(jsonObjectRequest);
            } catch (Exception e) {
                Toast.makeText(click.getContext(), e.getLocalizedMessage() + "null", Toast.LENGTH_SHORT).show();
            }
        });

        holder.reject.setOnClickListener(click -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(click.getContext());
            builder.setTitle("Reason for Cancel").setMessage("Enter reason for order cancellation below");
            LinearLayout layout = new LinearLayout(click.getContext());
            layout.setOrientation(LinearLayout.VERTICAL);
            EditText editText = new EditText(click.getContext());
            editText.setHint("Enter Reason Here");
            editText.setMaxLines(200);
            builder.setPositiveButton("Cancel Order", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(editText.getText().toString().equals(""))
                        Toast.makeText(click.getContext(), "Field can't be empty", Toast.LENGTH_SHORT).show();
                    else{
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().getRoot().child("Users").child(auth.getUid()).child("My Books").child(bookName).child("Orders");
                        databaseReference.child(buyerID.get(position)).removeValue();
                        RequestQueue requestQueue = Volley.newRequestQueue(click.getContext());
                        JSONObject main = new JSONObject();
                        try {
                            main.put("to", "/topics/" + buyerID.get(position) + "");
                            JSONObject notification = new JSONObject();
                            notification.put("title", "Order Rejected");
//                                    Toast.makeText(GetBookDetails.this, "" + getIntent().getStringExtra("sellerID"), Toast.LENGTH_SHORT).show();
                            notification.put("body", "" + "Order for your book " + bookName + " is Rejected\n" + editText.getText().toString());
                            main.put("notification", notification);

                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, main, response -> {

                            }, error -> Toast.makeText(click.getContext(), error.getLocalizedMessage() + "null", Toast.LENGTH_SHORT).show()) {
                                @Override
                                public Map<String, String> getHeaders() {
                                    Map<String, String> header = new HashMap<>();
                                    header.put("content-type", "application/json");
                                    header.put("authorization", "key=AAAA76i_om0:APA91bF20tIdfXbz_e3Y0-mLOsTsEsz0s-KhxPpOVz7v_Ew3v_IohB9AzwVSDtSQLlfPxgNzH4Vi-vv3tFBYAHxlkpmSEnRgSuxAc1Gyoe8RnDTbiIgDhs52YeoHs_wwK6AcrJrtqXRM");
                                    return header;
                                }
                            };

                            requestQueue.add(jsonObjectRequest);
                        } catch (Exception e) {
                            Toast.makeText(click.getContext(), e.getLocalizedMessage() + "null", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }).setNegativeButton("No, Wait", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).create();
            layout.addView(editText);
            builder.setView(layout);
            builder.show();
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
