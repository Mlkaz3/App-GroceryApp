package com.example.groceryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class Recipe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val backButton: ImageButton = findViewById(R.id.backA)
        backButton.setOnClickListener{
            finish()
        }

        //change the Cart to some other activty later
        val veganRecipe: ImageButton = findViewById(R.id.vegan)
        veganRecipe.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        val nonVeganRecipe: ImageButton = findViewById(R.id.nonvegan)
        nonVeganRecipe.setOnClickListener{
            startActivity(Intent(this, CartActivity::class.java))
        }
    }
}