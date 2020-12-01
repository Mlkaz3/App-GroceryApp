package com.example.groceryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.groceryapp.Adapter.CategoryAdapter
import com.example.groceryapp.Adapter.MySingleton
import com.example.groceryapp.Adapter.ProductAdapter
import com.example.groceryapp.Model.Product
import org.json.JSONArray
import org.json.JSONObject

class Category : AppCompatActivity() {

    //declare product array list
    lateinit var adapter: CategoryAdapter
    lateinit var productlist: ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        //initialise
        productlist = ArrayList<Product>()
        adapter = CategoryAdapter(this)
        adapter.setProducts(productlist)

        val backButton: ImageButton = findViewById(R.id.backShopNow)
        backButton.setOnClickListener {
            finish()
        }

        //Update UI
        val textView10: TextView = findViewById(R.id.textView10)
        val fruitURL: String = "https://groceryapptarucproject.000webhostapp.com/grocery/product/readfruit.php"
        val vegetableURL: String = "https://groceryapptarucproject.000webhostapp.com/grocery/product/readvegetable.php"
        val seafoodURL: String = "https://groceryapptarucproject.000webhostapp.com/grocery/product/readseafood.php"
        val chickenURL: String = "https://groceryapptarucproject.000webhostapp.com/grocery/product/readchicken.php"
        val eggURL: String = "https://groceryapptarucproject.000webhostapp.com/grocery/product/readegg.php"
        val categoryChoosen = intent.getStringExtra("categoryChoosen")
        if(categoryChoosen == "fruit"){
            //read product from database
            readCategoryProduct(fruitURL, categoryChoosen)
            textView10.text = "Category > Fruits"
        }
        else if(categoryChoosen == "vegetable"){
            readCategoryProduct(vegetableURL, categoryChoosen)
            textView10.text = "Category > Vegetables"
        }
        else if(categoryChoosen == "seafood"){
            readCategoryProduct(seafoodURL, categoryChoosen)
            textView10.text = "Category > Seafood"
        }
        else if(categoryChoosen == "chicken"){
            readCategoryProduct(chickenURL, categoryChoosen)
            textView10.text = "Category > Chickens"
        }
        else if(categoryChoosen == "egg"){
            readCategoryProduct(eggURL, categoryChoosen)
            textView10.text = "Category > Eggs"
        }

        //access recyclerview UI
        val recyclerview: RecyclerView = findViewById(R.id.categorycv)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        recyclerview.setHasFixedSize(true)
        Log.e("read category","IS HERE RUN? WHY UI NOT WORKING THO..X")

    }

    private fun readCategoryProduct(URL:String, categoryChoose: String) {
        Log.e("read category","entering this function")
        val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, URL, null,
                Response.Listener { response ->
                    // Process the JSON
                    try{
                        if(response != null){
                            val strResponse = response.toString()
                            val jsonResponse  = JSONObject(strResponse)
                            val jsonArray: JSONArray = jsonResponse.getJSONArray(categoryChoose)
                            val size: Int = jsonArray.length()
                            for(i in 0.until(size)){
                                var jsonProduct: JSONObject = jsonArray.getJSONObject(i)
                                var product: Product = Product(jsonProduct.getInt("product_id"),jsonProduct.getString("product_name"), jsonProduct.getDouble("product_price"),jsonProduct.getString("product_category"),jsonProduct.getString("product_img"),
                                        jsonProduct.getInt("product_stock"))
                                Log.e("read category",jsonProduct.toString())
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
        Log.e("read category",jsonObjectRequest.toString())

    }

}