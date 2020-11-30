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
import com.example.groceryapp.Adapter.MySingleton
import com.example.groceryapp.Adapter.ProductAdapter
import com.example.groceryapp.Model.Product
import org.json.JSONArray
import org.json.JSONObject

class Category : AppCompatActivity() {

    //declare product array list
    lateinit var adapter: ProductAdapter
    lateinit var productlist: ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        //initialise
        productlist = ArrayList<Product>()
        adapter = ProductAdapter(this)
        adapter.setProducts(productlist)

        val backButton: ImageButton = findViewById(R.id.backShopNow)
        backButton.setOnClickListener {
            finish()
        }

        val fruitURL: String = "https://groceryapptarucproject.000webhostapp.com/grocery/product/readfruit.php"
        val vegetableURL: String = "https://groceryapptarucproject.000webhostapp.com/grocery/product/readfruit.php"
        val seafoodURL: String = "https://groceryapptarucproject.000webhostapp.com/grocery/product/readfruit.php"
        val chickenURL: String = "https://groceryapptarucproject.000webhostapp.com/grocery/product/readfruit.php"
        val eggURL: String = "https://groceryapptarucproject.000webhostapp.com/grocery/product/readfruit.php"
        val categoryChoosen = intent.getStringExtra("categoryChoosen")
        if(categoryChoosen == "fruit"){
            //read product from database
            readCategoryProduct(fruitURL)
        }
        else if(categoryChoosen == "vegetable"){

        }
        else if(categoryChoosen == "seafood"){

        }
        else if(categoryChoosen == "chicken"){

        }
        else if(categoryChoosen == "egg"){

        }

        //access recyclerview UI
        val recyclerview: RecyclerView = findViewById(R.id.categorycv)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        recyclerview.setHasFixedSize(true)
    }

    private fun readCategoryProduct(fruitURL:String) {
        val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, fruitURL, null,
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