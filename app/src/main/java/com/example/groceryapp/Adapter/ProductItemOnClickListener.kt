package com.example.groceryapp.Adapter
import com.example.groceryapp.Model.Product

interface ProductItemOnClickListener {
    fun addCartClicked(itemData: Product, position: Int)
}