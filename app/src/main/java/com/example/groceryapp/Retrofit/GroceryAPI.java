package com.example.groceryapp.Retrofit;

import android.database.Observable;
import com.example.groceryapp.Model.Category;
import java.util.List;
import retrofit2.http.GET;

public interface GroceryAPI {
    @GET("getmenu.php")
    Observable<List<Category>> getMenu();
}
