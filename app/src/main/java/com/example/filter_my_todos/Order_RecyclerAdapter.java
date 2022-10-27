package com.example.filter_my_todos;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class Order_RecyclerAdapter extends RecyclerView.Adapter<Order_RecyclerAdapter.MyViewHolder> {

    private List<TodoListModelClass> contact;
    private final Context mContext;

    String userToken;

    public Order_RecyclerAdapter(List<TodoListModelClass> contacts, Context context) {
        this.contact = contacts;
        this.mContext = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.orderId.setText(contact.get(position).getId() );


    }

    @Override
    public int getItemCount() {
        if (contact == null) {
            return 0;
        } else if (contact.size() == 0) {
            return 0;
        } else {
            return contact.size();
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView orderListItem;
        TextView orderId, totalAmount,dateTime;
        ImageView img_successStatus;

        MyViewHolder(View itemView) {
            super(itemView);

          orderListItem = itemView.findViewById(R.id.orderListItem);
            orderId = itemView.findViewById(R.id.orderIdOrderList);
//            totalAmount = itemView.findViewById(R.id.totalAmountOrderItem);
          // tv_mobileNumber = itemView.findViewById(R.id.tv_mobileNumber);
//            dateTime = itemView.findViewById(R.id.dateTimeOrderItem);
//            img_successStatus = itemView.findViewById(R.id.img_successStatus);
        }
    }

    public void notifyChangeData(List<TodoListModelClass> response) {
        contact = response;
        notifyDataSetChanged();
    }

    private boolean checkConnection() {
        ConnectivityManager manager = (ConnectivityManager)
                mContext.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (activeNetwork == null) {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_SHORT).show();

            return false;
        }
        return true;
    }


}
