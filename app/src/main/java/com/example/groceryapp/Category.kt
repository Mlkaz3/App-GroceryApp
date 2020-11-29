package com.example.groceryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.Adapter.ProductAdapter
import com.example.groceryapp.Model.Product

class Category : AppCompatActivity() {

    //declare product array list
    lateinit var adapter: ProductAdapter
    lateinit var productlist: ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        //initialise
        productlist = ArrayList<Product>()
        adapter = ProductAdapter(this)
        adapter.setProducts(productlist)

        val backButton: ImageButton = findViewById(R.id.backShopNow)
        backButton.setOnClickListener {
            finish()
        }

        //read product from database
        readCategoryProduct()

        //access recyclerview UI
        val recyclerview: RecyclerView = findViewById(R.id.categorycv)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        recyclerview.setHasFixedSize(true)
    }

    private fun readCategoryProduct() {
        TODO("Not yet implemented")
    }
}