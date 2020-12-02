package com.example.groceryapp.Model

data class Product(
        val productID:Int,
        val productName:String,
        val productPrice: Double,
        val productCategory:String,
        val productImage: String,
        val productStock:Int)