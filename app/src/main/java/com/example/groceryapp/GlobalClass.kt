package com.example.groceryapp

import android.app.Application
import com.example.groceryapp.Model.CartItem

class GlobalClass:  Application(){
    var _cartList:ArrayList<CartItem> = ArrayList<CartItem>()

    //use when loading items from the server
    //use when user press add to cart
    fun addItem(item: CartItem){
        _cartList.add(item)
    }

    //use when the qty is 0
    fun removeItem(item: CartItem){
        _cartList.remove(item)
    }

    //read the cart item and display to user
    fun getItem(): ArrayList<CartItem>{
        return _cartList
    }


}