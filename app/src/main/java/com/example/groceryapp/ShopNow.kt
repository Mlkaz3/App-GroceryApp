package com.example.groceryapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.Utils.Commons
import io.reactivex.disposables.CompositeDisposable

class ShopNow : AppCompatActivity() {
    val mService = Commons.getAPI()
    val composite = CompositeDisposable()
    val lst_topProduct: RecyclerView = findViewById(R.id.topProduct)

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

        lst_topProduct.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        lst_topProduct.setHasFixedSize(true)

    }
}