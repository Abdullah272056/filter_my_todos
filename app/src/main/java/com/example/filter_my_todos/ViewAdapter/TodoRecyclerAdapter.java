package com.example.filter_my_todos.ViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filter_my_todos.Model.TodoListModelClass;
import com.example.filter_my_todos.R;

import java.util.List;


public class TodoRecyclerAdapter extends RecyclerView.Adapter<TodoRecyclerAdapter.MyViewHolder> {

    private List<TodoListModelClass> contact;
    private final Context mContext;



    public TodoRecyclerAdapter(List<TodoListModelClass> contacts, Context context) {
        this.contact = contacts;
        this.mContext = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //set data with view
        holder.userId_tv.setText("User Id: "+contact.get(position).getUserId() );
        holder.id_tv.setText("Id: "+contact.get(position).getId() );
        holder.title_tv.setText("Title: "+contact.get(position).getTitle() );
        holder.completed_check_Box.setChecked(contact.get(position).getCompleted());


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


        TextView userId_tv, id_tv,title_tv;
        CheckBox completed_check_Box;


        MyViewHolder(View itemView) {
            super(itemView);
            //recycler view item's view finding
            userId_tv = itemView.findViewById(R.id.userIDTextViewId);
            id_tv = itemView.findViewById(R.id.idTextViewId);
            title_tv = itemView.findViewById(R.id.titleTextViewId);
            completed_check_Box = itemView.findViewById(R.id.checkBoxId);

        }
    }






}
