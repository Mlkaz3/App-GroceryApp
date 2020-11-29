package com.example.groceryapp.Model

data class CartItem (
        var productQty:Int,
        val product_name:String,
        val cart_id:String,
        val product_price:Double,
        val product_img:String
)