package com.example.filter_my_todos.network;

import com.example.filter_my_todos.Model.TodoListModelClass;
import com.example.filter_my_todos.Model.UserModelClass;

import java.util.List;
import retrofit2.http.GET;
import retrofit2.Call;


public interface ApiInterface{

    //get user list
    @GET("users")
   // Call<UserModelClass> getUserList();
    Call<List<UserModelClass>> getUserList();

    //get todo list
    @GET("todos")
    Call<List<TodoListModelClass>> getTodoList();

}
