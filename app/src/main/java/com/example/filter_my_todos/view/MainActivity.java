package com.example.filter_my_todos.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.filter_my_todos.Model.TodoListModelClass;
import com.example.filter_my_todos.Model.UserModelClass;
import com.example.filter_my_todos.R;
import com.example.filter_my_todos.ViewAdapter.SpinAdapter;
import com.example.filter_my_todos.ViewAdapter.TodoRecyclerAdapter;
import com.example.filter_my_todos.network.ApiClient;
import com.example.filter_my_todos.network.ApiInterface;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<TodoListModelClass> todoListData;
    private List<TodoListModelClass> todoFilterListData;
    private List<UserModelClass> userListData;
    private RecyclerView todoRecyclerView;
    private TodoRecyclerAdapter todoAdapter;

    Spinner spinner_user;

    private SpinAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner_user=findViewById(R.id.spinnerUserId);
        getUserData();

        recyclerViewInit();
        getTodoData();
    }

    //init recyclerview and set adapter
    private void recyclerViewInit() {
        todoRecyclerView = findViewById(R.id.recyclerViewId);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        todoRecyclerView.setLayoutManager(layoutManager);
//      orderRecyclerView.setHasFixedSize(true);
        todoAdapter = new TodoRecyclerAdapter(todoListData, this);
        todoRecyclerView.setAdapter(todoAdapter);
    }


    // get all user Data from server
    private void getUserData() {

        if (checkConnection()) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<List<UserModelClass>> call = apiInterface.getUserList();
            call.enqueue(new Callback<List<UserModelClass>>() {
                @Override
                public void onResponse(Call<List<UserModelClass>> call, Response<List<UserModelClass>> response) {
                    if (response.code()==200){
                         userListData = response.body();


                         //spinner adapter init and set with spinner
                        adapter = new SpinAdapter(MainActivity.this,
                                R.layout.spinner_item,
                                userListData);
                        spinner_user.setAdapter(adapter); // Set the custom adapter to the spinner
                        // You can create an anonymous listener to handle the event when is selected an spinner item
                        spinner_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view,
                                                       int position, long id) {

                                UserModelClass user = adapter.getItem(position);

                                dataFilter(String.valueOf(user.getId()));

                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapter) {  }
                        });

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

    // get todo Data from server
    private void getTodoData() {

        if (checkConnection()) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<List<TodoListModelClass>> call = apiInterface.getTodoList();
            call.enqueue(new Callback<List<TodoListModelClass>>() {
                @Override
                public void onResponse(Call<List<TodoListModelClass>> call, Response<List<TodoListModelClass>> response) {
                    if (response.code()==200){
                        todoListData = response.body();


                        //copy all data
                        todoFilterListData=todoListData;
                        //todo adapter init and set with recycler view
                        todoAdapter = new TodoRecyclerAdapter(todoFilterListData, MainActivity.this);
                        todoRecyclerView.setAdapter(todoAdapter);
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    void dataFilter(String id){

        Predicate<TodoListModelClass> byUserId = todoListModelClass -> Objects.equals(todoListModelClass.getUserId(), id);


        todoFilterListData = todoListData.stream().filter(byUserId)
                .collect(Collectors.toList());

        todoAdapter = new TodoRecyclerAdapter(todoFilterListData, MainActivity.this);
        todoRecyclerView.setAdapter(todoAdapter);
        //Toast.makeText(MainActivity.this,String.valueOf(todoFilterListData.size()) , Toast.LENGTH_SHORT).show();




    }



}