package com.example.groceryapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.groceryapp.Adapter.MySingleton
import com.example.groceryapp.Adapter.ProductAdapter
import com.example.groceryapp.Adapter.ProductItemOnClickListener
import com.example.groceryapp.Model.CartItem
import com.example.groceryapp.Model.Product
import com.example.groceryapp.ViewModel.CartItemViewModel
import org.json.JSONArray
import org.json.JSONObject


class ShopNowActivity : AppCompatActivity() ,ProductItemOnClickListener{

    //declare product array list
    lateinit var adapter: ProductAdapter
    lateinit var productlist: ArrayList<Product>
    lateinit var viewModel: CartItemViewModel
    lateinit var cartID:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_now)

        viewModel = ViewModelProviders.of(this)[CartItemViewModel::class.java]

        //get the card_id from a=main activity
        cartID = intent.getStringExtra("cart_id").toString()
        Log.e("winniecheck",cartID)

        //initialise
        productlist = ArrayList<Product>()
        adapter = ProductAdapter(this,this)
        adapter.setProducts(productlist)

        //read product from database
        readProduct()

        val backButton: ImageButton = findViewById(R.id.back)
        backButton.setOnClickListener {
            finish()
        }

        val cartButton: ImageButton = findViewById(R.id.cart)
        cartButton.setOnClickListener {
            //enable user to view cart
            val intent = Intent(baseContext, CartActivity::class.java)
            intent.putExtra("cart_id", cartID)
            startActivity(intent)
        }

        //reading the category recycler view
        val i = Intent(applicationContext, CategoryActivity::class.java)
        //user choose fruit
        val fruitButton: ImageButton = findViewById(R.id.imageButtonFruits)
        fruitButton.setOnClickListener {
            i.putExtra("categoryChoosen", "fruit")
            i.putExtra("cart_id", cartID.toString())
            startActivity(i)
        }
        val vegetableButton: ImageButton = findViewById(R.id.imageButtonVegetable)
        vegetableButton.setOnClickListener {
            i.putExtra("categoryChoosen", "vegetable")
            i.putExtra("cart_id", cartID.toString())
            startActivity(i)
        }
        val seafoodButton: ImageButton = findViewById(R.id.imageButtonSeafood)
        seafoodButton.setOnClickListener {
            i.putExtra("categoryChoosen", "seafood")
            i.putExtra("cart_id", cartID.toString())
            startActivity(i)
        }
        val chickenButton: ImageButton = findViewById(R.id.imageButtonChicken)
        chickenButton.setOnClickListener {
            i.putExtra("categoryChoosen", "chicken")
            i.putExtra("cart_id", cartID.toString())
            startActivity(i)
        }
        val eggButton: ImageButton = findViewById(R.id.imageButtonEggs)
        eggButton.setOnClickListener {
            i.putExtra("categoryChoosen", "egg")
            i.putExtra("cart_id", cartID.toString())
            startActivity(i)
        }

        //access recyclerview UI
        val recyclerview:RecyclerView = findViewById(R.id.topProduct)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        recyclerview.setHasFixedSize(true)

    }

    //read and display Product
    fun readProduct() {
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
                            var product: Product = Product(jsonProduct.getInt("product_id"),jsonProduct.getString("product_name"), jsonProduct.getDouble("product_price"),jsonProduct.getString("product_category"),jsonProduct.getString("product_img"),
                                jsonProduct.getInt("product_stock"))

                            productlist.add(product)
                            adapter!!.notifyDataSetChanged()
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

    override fun addCartClicked(itemData: Product, position: Int) {
        //action to be done when add to cart function is called
        AddCart(itemData)
    }

    //AddCart function it works!! but sadly cant read from database
    //success and the toast work too
    //ERROR OCURRED: ONLY ABLE TO WRITE ONCE TO DATABASE
    fun AddCart(product: Product) {
        //step1: update local database
        var cartitem:CartItem = CartItem(1,product,product.productPrice)


        //step2: write to database(cart section), update cart item :)
        val url = "https://groceryapptarucproject.000webhostapp.com/grocery/cart/insertcartnoduplicate.php?cart_id="  + cartID + "&product_id=" + product.productID

        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                try{
                    if(response != null){
                        val strResponse = response.toString()
                        val jsonResponse  = JSONObject(strResponse)

                        val success: String = jsonResponse.get("success").toString()

                        if(success == "1"){
                            Toast.makeText(this, product.productName + " added to cart", Toast.LENGTH_LONG).show()

                        }else{
                            Toast.makeText(this, "Unable to ad item to cart.", Toast.LENGTH_LONG).show()
                        }

                    }
                }catch (e:Exception){
                    Log.d("Main", "Response: %s".format(e.message.toString()))

                }

            },
            Response.ErrorListener { error -> Log.d("Main", "Response: %s".format(error.message.toString())) })

        // Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(stringRequest)

    }

}