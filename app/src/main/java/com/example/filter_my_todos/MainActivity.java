package com.example.filter_my_todos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.filter_my_todos.network.ApiClient;
import com.example.filter_my_todos.network.ApiInterface;

import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private List<TodoListModelClass> todoListData;
    private List<UserModelClass> userListData;
    private RecyclerView orderRecyclerView;
    private Order_RecyclerAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  getUserData();
      //  getTodoData();

        recyclerViewInit();
        getTodoData();
    }

    private void recyclerViewInit() {
        orderRecyclerView = findViewById(R.id.recyclerViewId);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        orderRecyclerView.setLayoutManager(layoutManager);
//      orderRecyclerView.setHasFixedSize(true);
        orderAdapter = new Order_RecyclerAdapter(todoListData, this);
        orderRecyclerView.setAdapter(orderAdapter);
    }


    // get user Data
    private void getUserData() {

        if (checkConnection()) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<List<UserModelClass>> call = apiInterface.getUserList();
            call.enqueue(new Callback<List<UserModelClass>>() {
                @Override
                public void onResponse(Call<List<UserModelClass>> call, Response<List<UserModelClass>> response) {
                    if (response.code()==200){
                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                         userListData = response.body();
                         Toast.makeText(MainActivity.this,String.valueOf(userListData.size()) , Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<UserModelClass>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Reach Max Time[Order Details]! \nCheck Internet Connection!", Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            });



        }

    }

    // get todo Data
    private void getTodoData() {

        if (checkConnection()) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<List<TodoListModelClass>> call = apiInterface.getTodoList();
            call.enqueue(new Callback<List<TodoListModelClass>>() {
                @Override
                public void onResponse(Call<List<TodoListModelClass>> call, Response<List<TodoListModelClass>> response) {
                    if (response.code()==200){
                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                        todoListData = response.body();
                        orderAdapter = new Order_RecyclerAdapter(todoListData, MainActivity.this);
                        orderRecyclerView.setAdapter(orderAdapter);
                        Toast.makeText(MainActivity.this,String.valueOf(todoListData.size()) , Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<TodoListModelClass>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Reach Max Time[Order Details]! \nCheck Internet Connection!", Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            });



        }

    }



    public boolean checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (activeNetwork == null) {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();

            return false;

        } else {
            return true;
        }
    }



}