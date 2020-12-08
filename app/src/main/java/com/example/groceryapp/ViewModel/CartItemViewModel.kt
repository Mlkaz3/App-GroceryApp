package com.example.groceryapp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.groceryapp.Adapter.MySingleton
import com.example.groceryapp.CartActivity
import com.example.groceryapp.Model.CartItem
import com.example.groceryapp.Model.Product
import com.example.groceryapp.R
import org.json.JSONArray
import org.json.JSONObject

//not using ya
class CartItemViewModel : ViewModel() {
    //Tracks for the local user cart item
     var localuserCartList:ArrayList<CartItem> = ArrayList<CartItem>()

    //example of code that works
//    var number: Int = 0
//    fun changeMainText(){
//        number += 1
//    }





//    private val cartitems: MutableLiveData<List<CartItem>> by lazy {
//        MutableLiveData().also {
//            loadCartItems()
//        }
//    }
//
//    fun getCartItems(): LiveData<List<CartItem>> {
//        return cartitems
//    }
//
//    private fun loadCartItems() {
//        // Do an asynchronous operation to fetch users.
//    }
}