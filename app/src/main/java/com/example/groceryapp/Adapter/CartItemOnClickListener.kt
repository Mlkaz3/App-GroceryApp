package com.example.groceryapp.Adapter

import com.example.groceryapp.Model.CartItem
import java.text.FieldPosition

interface CartItemOnClickListener {
    fun addQtyClicked(itemData: CartItem, position: Int)
    fun minusQtyClicked(itemData:CartItem,position: Int)

}