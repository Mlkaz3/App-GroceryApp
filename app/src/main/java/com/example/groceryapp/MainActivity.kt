package com.example.groceryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Accessing UI

        val profileButton:ImageButton = findViewById(R.id.profileButton)
        val recipeButton:ImageButton = findViewById(R.id.recipeButton)

        val cartButton:ImageButton = findViewById(R.id.cartButton)
        cartButton.setOnClickListener{
            startActivity(Intent(this, Cart::class.java))
        }

        val shopnowButton:ImageButton = findViewById(R.id.shopnowButton)
        shopnowButton.setOnClickListener{
            startActivity(Intent(this, ShopNow::class.java))
        }

        val salesButton: ImageButton = findViewById(R.id.salesButton)
        salesButton.setOnClickListener{
            //jump to new activity/fragment
            startActivity(Intent(this, Sales::class.java))

        }

        val websiteButton:ImageButton = findViewById(R.id.websiteButton)
        websiteButton.setOnClickListener{
            startActivity(Intent(this, Website::class.java))
        }

    }
}