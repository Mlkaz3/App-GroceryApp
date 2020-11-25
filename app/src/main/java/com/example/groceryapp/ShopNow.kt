package com.example.groceryapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.Adapter.TopProductAdapter
import com.example.groceryapp.Model.Product
import org.json.JSONException


class ShopNow : AppCompatActivity() {
    val lst_topProduct: RecyclerView = findViewById(R.id.topProduct)
    var JSON_URL:String ="https://groceryapptarucproject.000webhostapp.com/connect/getmenu.php"

    //Initialize variables and UI
    var products = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_now)

        var adapter = TopProductAdapter(this,products)
        adapter.setProduct(products)

        val backButton: ImageButton = findViewById(R.id.back)
        backButton.setOnClickListener {
            finish()
        }

        val cartButton: ImageButton = findViewById(R.id.cart)
        cartButton.setOnClickListener {
            startActivity(Intent(this, Cart::class.java))
        }

//        fetchTopProduct()

    }

    private fun fetchTopProduct() {
        val queue = Volley.newRequestQueue(this)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            JSON_URL,
            null,
            Response.Listener { response ->
                for (i in 0 until response.length()) {
                    try {
                        val topProductObject = response.getJSONObject(i)
                        val productName = topProductObject.getString("Name")
                        val productImage = topProductObject.getString("Link")
                        val product:Product = Product(productName,productImage)
                        products.add(product)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
//                lst_topProduct.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//                lst_topProduct.setHasFixedSize(true)
//                TopProductAdapter = TopProductAdapter(applicationContext, products)
//                lst_topProduct.setAdapter(TopProductAdapter)
            },
            Response.ErrorListener {  })

        queue.add(jsonArrayRequest)

    }
}