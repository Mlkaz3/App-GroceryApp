package com.example.groceryapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.groceryapp.Adapter.CartItemAdapter
import com.example.groceryapp.Adapter.CartItemOnClickListener
import com.example.groceryapp.Adapter.MySingleton
import com.example.groceryapp.Model.CartItem
import com.example.groceryapp.Model.Product
import com.example.groceryapp.ViewModel.CartItemViewModel
import org.json.JSONArray
import org.json.JSONObject


class CartActivity : AppCompatActivity(), CartItemOnClickListener{

    //declare product array list
    var userCartList:ArrayList<CartItem> = ArrayList<CartItem>()
    lateinit var adapter: CartItemAdapter
    var subtotalCal:Double = 0.0
    var totalCal:Double = 0.0
    lateinit var cartID:String
    lateinit var userID:String
    lateinit var localCartList:GlobalClass

    //onCreate()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        //get the card_id from a=main activity
        cartID = intent.getStringExtra("cart_id").toString()
        userID = intent.getStringExtra("user_id").toString()
        Log.e("winniecheck",cartID.toString())
        Log.e("winniecheck",userID.toString())

        val backButton: ImageButton = findViewById(R.id.back2)
        backButton.setOnClickListener {
            finish()
        }

        val intent = Intent(this, CheckoutActivity::class.java)
        val checkoutButton:Button = findViewById(R.id.checkoutButton)
        checkoutButton.setOnClickListener {
            val total:TextView = findViewById(R.id.total)
            intent.putExtra("amount", total.text)
            intent.putExtra("cart_id", cartID)
            intent.putExtra("user_id", userID)
            startActivity(intent)
        }

        //read latest cart
        val refreshButton:ImageButton = findViewById(R.id.refresh)
        refreshButton.setOnClickListener {
            Toast.makeText(applicationContext, "Refreshed.", Toast.LENGTH_LONG).show()

        }


    }

    //onStart()
    override fun onStart() {
        super.onStart()

        //initialise
        userCartList = ArrayList<CartItem>(userCartList)
        adapter = CartItemAdapter(this,this)
        adapter.setProducts(userCartList)

        readCart()

        //access recyclerview UI
        val recyclerview: RecyclerView = findViewById(R.id.cart_rv)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        recyclerview.setHasFixedSize(true)


    }

    //during onPause() the array remains as the updated value, but once it went to onStart, thn it's not update
    override fun onPause(){
        super.onPause()
        Log.e("check cart size", userCartList.size.toString())

    }

    override fun onResume(){
        super.onResume()

    }

    //when user press add to cart
    //credit to : https://www.youtube.com/watch?v=vz26K2xrO6I&feature=youtu.be
    //BUG: CHANGES MADE HERE WILL NOT STORE INTO THE ARRAY UPSTAIRS
    //PLANNING: WRITE A FUNCTION AND CALL IT INSIDE THE ONSTART()
    override fun addQtyClicked(itemData: CartItem, position:Int) {
        //TO DO: add some code to chg the local variable usercartlist
        itemData.productQty += 1
        adapter.notifyItemChanged(position)
        Log.e("cart changes", itemData.productQty.toString())

        //write the value to database
        updateQty(itemData)

    }

    //TO DO: override the onResume() method to call this function
    private fun readCart() {
        //this code is use to read user 1 cart, where cart_id = 1
        val url = "https://groceryapptarucproject.000webhostapp.com/grocery/cart/readusercart.php?cart_id=" + cartID
        val cartamount: TextView = findViewById(R.id.cartamount)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Process the JSON
                try{
                    if(response != null){
                        val strResponse = response.toString()
                        val jsonResponse  = JSONObject(strResponse)
                        //due to the server array
                        val jsonArray: JSONArray = jsonResponse.getJSONArray("cart"+ cartID)
                        val size: Int = jsonArray.length()
                        for(i in 0.until(size)){
                            var jsonCartItem: JSONObject = jsonArray.getJSONObject(i)

                            var cartitem: CartItem = CartItem(jsonCartItem.getInt("qty"),Product(jsonCartItem.getInt("product_id"),jsonCartItem.getString("product_name"),jsonCartItem.getDouble("product_price"),
                                jsonCartItem.getString("product_category"), jsonCartItem.getString("product_img"),
                                jsonCartItem.getInt("product_stock")),jsonCartItem.getDouble("subtotal"))

                            userCartList.add(cartitem)
                            adapter!!.notifyDataSetChanged()
                            localCartList.addItem(cartitem)
                            Log.e("winniecheck1",localCartList.toString())

                            subtotalCal += cartitem.subtotal
                            Log.e("winnie",subtotalCal.toString())
                        }
                        Toast.makeText(applicationContext, "Record found :$size", Toast.LENGTH_LONG).show()
                        cartamount.text = "$size total items"

                        //accessing ui
                        val subtotal:TextView = findViewById(R.id.subtotal)
                        val shipping:TextView = findViewById(R.id.shipping)
                        val total:TextView = findViewById(R.id.total)

                        //calculation variable
                        var shippingCal:Double =0.0

                        //perform simple calculation
                        if(subtotalCal >80){
                            //free shipping fees
                            totalCal = subtotalCal
                        }
                        else{
                            shippingCal = 10.0
                            totalCal = shippingCal + subtotalCal
                        }

                        subtotal.text = subtotalCal.toString()
                        shipping.text = shippingCal.toString()
                        total.text = totalCal.toString()
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




    override fun minusQtyClicked(itemData: CartItem, position:Int) {
        //this adapter we changing the local cart list only
        if(itemData.productQty == 1){
            Toast.makeText(this, "item delete from cart", Toast.LENGTH_LONG).show()
            //how to delete the record in view
        }else{
            itemData.productQty -= 1
        }
        adapter.notifyItemChanged(position)
        Log.e("cart changes", itemData.productQty.toString())

        //perform calculation


        //write the value to database
        updateQty(itemData)
    }

    //WORKING, BUT IF READ FROM DATABASE IS NOT UPDATE THEN HERE CONSIDER AS BUG
    fun updateQty(itemData: CartItem){
        //write to database(cart section), update cart item qty
        val url = "https://groceryapptarucproject.000webhostapp.com/grocery/cart/updatequantity.php?cart_id=" + cartID + "&product_id="+ itemData.productInfo.productID.toString() + "&qty=" + itemData.productQty
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try{
                    if(response != null){
                        Toast.makeText(this, itemData.productInfo.productName + " quantity updated!", Toast.LENGTH_LONG).show()

                    }
                }catch (e:Exception){
                    Log.d("Main", "Response: %s".format(e.message.toString()))

                }
            },
            Response.ErrorListener { error ->
                Log.d("Main", "Response: %s".format(error.message.toString()))

            })

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