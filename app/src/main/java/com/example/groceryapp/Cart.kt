package com.example.groceryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.Adapter.CartItemAdapter
import com.example.groceryapp.Model.CartItem
import com.example.groceryapp.Model.Product

class Cart : AppCompatActivity() {

    //initialise product array list
    val userCartList = ArrayList<CartItem>()
    val adapter = CartItemAdapter(this,userCartList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val backButton: ImageButton = findViewById(R.id.back2)
        backButton.setOnClickListener {
            finish()
        }

        var product0:Product = Product("http://mrfarmergrocer.com/wp-content/uploads/2020/04/Tomato-1.jpg","Tomato(±500g)","Vegetables",3.60,10)
        userCartList += CartItem(2, product0)
        userCartList += CartItem(2, product0)
        userCartList += CartItem(2, product0)
        userCartList += CartItem(2, product0)
        userCartList += CartItem(2, product0)

        //access recyclerview UI
        val recyclerview: RecyclerView = findViewById(R.id.cart_rv)

        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        recyclerview.setHasFixedSize(true)






    }
}