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
        val salesButton: ImageButton = findViewById(R.id.salesButton)
        val profileButton:ImageButton = findViewById(R.id.profileButton)
        val recipeButton:ImageButton = findViewById(R.id.recipeButton)
        val cartButton:ImageButton = findViewById(R.id.cartButton)
        val websiteButton:ImageButton = findViewById(R.id.websiteButton)
        val shopnowButton:ImageButton = findViewById(R.id.shopnowButton)

        salesButton.setOnClickListener{
            //jump to new activity/fragment
            startActivity(Intent(this, Sales::class.java))

        }

        websiteButton.setOnClickListener{
            startActivity(Intent(this, Website::class.java))
        }

    }
}