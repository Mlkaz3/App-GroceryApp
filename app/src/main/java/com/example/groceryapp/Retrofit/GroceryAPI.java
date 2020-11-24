package com.example.groceryapp.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GroceryAPI {
    @GET("getmenu.php")
    Call<List<TopProduct>> getTopProduct();


}
