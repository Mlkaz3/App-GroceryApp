package com.example.groceryapp.Utils;
import com.example.groceryapp.Retrofit.GroceryAPI;
import com.example.groceryapp.Retrofit.RetrofitClients;

public class Commons {
    private static final String BASE_URL = "https://groceryapptarucproject.000webhostapp.com/connect";

    public static GroceryAPI getAPI(){
        return RetrofitClients.getClientFunction(BASE_URL).create(GroceryAPI.class);
    }
}
