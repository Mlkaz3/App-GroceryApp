package com.example.groceryapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.Adapter.ProductAdapter
import com.example.groceryapp.Model.Product
import com.squareup.picasso.Picasso


class ShopNow : AppCompatActivity() {

    //initialise product array list
    val productlist = ArrayList<Product>()
    val adapter = ProductAdapter(this,productlist)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_now)

        val picassoBuilder = Picasso.Builder(this)
        val picasso = picassoBuilder.build()


        val backButton: ImageButton = findViewById(R.id.back)
        backButton.setOnClickListener {
            finish()
        }

        val cartButton: ImageButton = findViewById(R.id.cart)
        cartButton.setOnClickListener {
            startActivity(Intent(this, Cart::class.java))
        }

        //bring fake value inside
        productlist += Product("http://mrfarmergrocer.com/wp-content/uploads/2020/04/Tomato-1.jpg","Tomato(±500g)","Vegetables",3.60,10)
        productlist += Product("http://mrfarmergrocer.com/wp-content/uploads/2020/04/Aust.-Red-Grapes-1.jpg","Aust. Red Grape (±1kg)","Fruits",28.0,10)
        productlist += Product("http://mrfarmergrocer.com/wp-content/uploads/2020/04/Saba-Mackerel-Fillet-1.jpg","Saba Mackerel Fillet (±550g)","Seafood",28.50,10)
        productlist += Product("http://mrfarmergrocer.com/wp-content/uploads/2020/05/33-600x600.jpg","Herbal Free Range Chicken (1 Ekor)","Chickens",39.90,10)
        productlist += Product("http://mrfarmergrocer.com/wp-content/uploads/2020/05/P10101321-page-001-600x450.jpg","LTP Century Egg (4pcs)","Eggs",7.0,10)

        //access recyclerview UI
        val recyclerview:RecyclerView = findViewById(R.id.topProduct)

        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        recyclerview.setHasFixedSize(true)

    }

}