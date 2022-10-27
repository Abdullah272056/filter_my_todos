package com.example.filter_my_todos.ViewAdapter;

//public class SpinAdapter {
//}

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.filter_my_todos.Model.UserModelClass;

import java.util.List;


public class SpinAdapter extends ArrayAdapter<UserModelClass> {

    private Context context;
    private UserModelClass[] values;



    public SpinAdapter(Context context, int textViewResourceId,
                       List<UserModelClass> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values.toArray(new UserModelClass[0]);
    }

    @Override
    public int getCount(){
        return values.length;
    }

    @Override
    public UserModelClass getItem(int position){
        return values[position];
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);

        // label text which user can see
        label.setText(values[position].getName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        // label text which user can see
        label.setText(values[position].getName());

        return label;
    }
}
