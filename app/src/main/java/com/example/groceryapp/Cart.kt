package com.example.groceryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.groceryapp.Adapter.CartItemAdapter
import com.example.groceryapp.Adapter.MySingleton
import com.example.groceryapp.Model.CartItem
import com.example.groceryapp.Model.Product
import org.json.JSONArray
import org.json.JSONObject

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

        readCart()

        //access recyclerview UI
        val recyclerview: RecyclerView = findViewById(R.id.cart_rv)

        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        recyclerview.setHasFixedSize(true)

    }

    private fun readCart() {
        //read from database
        val url = getString(R.string.url_server) + getString(R.string.url_read_cart)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Process the JSON
                try{
                    if(response != null){
                        val strResponse = response.toString()
                        val jsonResponse  = JSONObject(strResponse)
                        val jsonArray: JSONArray = jsonResponse.getJSONArray("cartitems")
                        val size: Int = jsonArray.length()
                        for(i in 0.until(size)){
                            var jsonCartItem: JSONObject = jsonArray.getJSONObject(i)
                            //  val productQty:Int,
                            //                     val product_name:String,
                            //                     val cart_id:String,
                            //                     val product_price:Double,
                            //                     val product_img:String
                            var cartitem: CartItem = CartItem(jsonCartItem.getInt("quantity"),jsonCartItem.getString("product_name"),
                                    jsonCartItem.getString("cart_id"), jsonCartItem.getDouble("product_price"),
                                    jsonCartItem.getString("product_img"))

                            userCartList.add(cartitem)
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