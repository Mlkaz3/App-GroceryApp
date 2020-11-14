package com.example.groceryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class ShopNow : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_now)

        val backButton: ImageButton = findViewById(R.id.back)
        backButton.setOnClickListener {
            finish()
        }

        val cartButton: ImageButton = findViewById(R.id.cart)
        cartButton.setOnClickListener {
            startActivity(Intent(this, Cart::class.java))

        }

    }
}