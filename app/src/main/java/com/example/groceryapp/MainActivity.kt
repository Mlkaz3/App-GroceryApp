package com.example.groceryapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //returning 1
        val cartID = intent.getStringExtra("cart_id")
        Log.e("winniecheck",cartID.toString())

        //Accessing UI
        //profile page
        val profileButton:ImageButton = findViewById(R.id.profileButton)
        profileButton.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

        //recipe
        val recipeButton:ImageButton = findViewById(R.id.recipeButton)
        recipeButton.setOnClickListener{
            startActivity(Intent(this, RecipeActivity::class.java))
        }

        //view cart
        val cartButton:ImageButton = findViewById(R.id.cartButton)
        cartButton.setOnClickListener{
            //pass the user cart id into the cart activities
            val intent = Intent(baseContext, CartActivity::class.java)
            intent.putExtra("cart_id", cartID)
            startActivity(intent)
        }

        //shop now
        val shopnowButton:ImageButton = findViewById(R.id.shopnowButton)
        shopnowButton.setOnClickListener{
            //pass the user cart id into the shopnow activities
            val intent = Intent(baseContext, ShopNowActivity::class.java)
            intent.putExtra("cart_id", cartID)
            startActivity(intent)

        }

        //view promo
        val salesButton: ImageButton = findViewById(R.id.salesButton)
        salesButton.setOnClickListener{
            //jump to new activity/fragment
            startActivity(Intent(this, SalesActivity::class.java))

        }

        //view website
        val websiteButton:ImageButton = findViewById(R.id.websiteButton)
        websiteButton.setOnClickListener{
            startActivity(Intent(this, WebsiteActivity::class.java))
        }

    }
}