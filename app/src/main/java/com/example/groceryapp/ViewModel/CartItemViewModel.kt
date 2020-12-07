package com.example.groceryapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groceryapp.Model.CartItem

class CartItemViewModel : ViewModel() {
    //Tracks for the user cart item
    lateinit var cartitems:ArrayList<CartItem>


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