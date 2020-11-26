package com.example.groceryapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.groceryapp.Adapter.MySingleton
import com.example.groceryapp.Adapter.ProductAdapter
import com.example.groceryapp.Model.Product
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject


class ShopNow : AppCompatActivity() {

    //declare product array list
    lateinit var adapter: ProductAdapter
    lateinit var productlist: ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_now)

        //initialise
        productlist = ArrayList<Product>()
        adapter = ProductAdapter(this)
        adapter.setProducts(productlist)

        val picassoBuilder = Picasso.Builder(this)
        val picasso = picassoBuilder.build()

        //read product from database
        readProduct()

        val backButton: ImageButton = findViewById(R.id.back)
        backButton.setOnClickListener {
            finish()
        }

        val cartButton: ImageButton = findViewById(R.id.cart)
        cartButton.setOnClickListener {
            startActivity(Intent(this, Cart::class.java))
        }

        //access recyclerview UI
        val recyclerview:RecyclerView = findViewById(R.id.topProduct)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        recyclerview.setHasFixedSize(true)

    }

    private fun readProduct() {
        //read from database
        val url = getString(R.string.url_server) + getString(R.string.url_read_product)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Process the JSON
                try{
                    if(response != null){
                        val strResponse = response.toString()
                        val jsonResponse  = JSONObject(strResponse)
                        val jsonArray: JSONArray = jsonResponse.getJSONArray("products")
                        val size: Int = jsonArray.length()
                        for(i in 0.until(size)){
                            var jsonProduct: JSONObject = jsonArray.getJSONObject(i)
                            var product: Product = Product(jsonProduct.getString("product_img"),jsonProduct.getString("product_name"), jsonProduct.getString("product_category"),
                                jsonProduct.getDouble("product_price"),jsonProduct.getInt("product_stock"))

                            productlist.add(product)
                        }
                        Toast.makeText(applicationContext, "Record found :$size", Toast.LENGTH_LONG).show()

                    }
                }catch (e:Exception){
                    Log.d("Main", "Response: %s".format(e.message.toString()))


                }
            },
            Response.ErrorListener { error ->
                Log.d("Main", "Response: %s".format(error.message.toString()))
            }
        )

        //Volley request policy, only one time request
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            0, //no retry
            1f
        )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

}