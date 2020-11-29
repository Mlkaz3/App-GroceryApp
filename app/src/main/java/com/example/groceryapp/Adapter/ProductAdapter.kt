package com.example.groceryapp.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.groceryapp.Model.CartItem
import com.example.groceryapp.Model.Product
import com.example.groceryapp.R
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.text.DecimalFormat


class ProductAdapter(contexts: Context): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    //declare a context
    var context:Context = contexts
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    // Cached copy of user
    private var products = emptyList<Product>()

    //round off function
    val df: DecimalFormat = DecimalFormat("0.00")

    //to hold one single view only
    inner class ProductViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val img: ImageView = itemView.findViewById(R.id.image_product)
        val title: TextView = itemView.findViewById(R.id.product_title)
        val stock: TextView = itemView.findViewById(R.id.stock)
        val price:TextView = itemView.findViewById(R.id.price)
        val buttonAddCart: Button = itemView.findViewById(R.id.buttonAddCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = inflater.inflate(R.layout.product,parent,false)
        return ProductViewHolder(itemView)
    }

    override fun getItemCount() = products.size

    internal fun setProducts(product: List<Product>) {
        this.products = product
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = products[position]
        Picasso.with(context).load(currentItem.productImage).into(holder.img)
        Picasso.with(context).isLoggingEnabled = true
        holder.title.text = currentItem.productName
        //check for stock amount
        if(currentItem.productStock > 0){
            holder.stock.text = "In Stock"
        }
        else{
            holder.stock.text = "Out of Stock"
        }
        holder.price.text = "RM" + df.format(currentItem.productPrice).toString()
        //when the user press add to cart button, called AddCart function
        holder.buttonAddCart.setOnClickListener {
            AddCart(position)
        }
    }

    //AddCart function
    fun AddCart(position: Int){

        //step0: retrieve the value and store it in a local variable
        var name = products[position].productName
        var image = products[position].productImage
        var price = products[position].productPrice
        var stock = products[position].productStock
        var item:CartItem = CartItem(1,name,"customer1cart",price,image)

        //step1: check product stock availability
        if(stock<=0){
            Toast.makeText(context, "The product is currently out of stock", Toast.LENGTH_LONG).show()
        }
        else{
            //step2: write to database(cart section), update cart item :)
            val url = "https://groceryapptarucproject.000webhostapp.com/grocery/writetodatabase.php/" + "?product_name=" + item.product_name +
                    "&quantity=" + item.productQty + "&cart_id=" + item.cart_id + "&product_img=" + item.product_img +
                    "&product_price=" + item.product_price
            val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.POST, url, null,
                    Response.Listener { response ->
                        // Process the JSON
                        try{
                            if(response != null){
                                val strResponse = response.toString()
                                val jsonResponse  = JSONObject(strResponse)
                                val success: String = jsonResponse.get("success").toString()

                                if(success == "1"){
                                    Toast.makeText(context, item.product_name + " added to cart", Toast.LENGTH_LONG).show()

                                }else{
                                    Toast.makeText(context, "Unable to ad item to cart.", Toast.LENGTH_LONG).show()
                                }

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
            MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest)
        }


            Toast.makeText(context, item.product_name + " adding to cart", Toast.LENGTH_LONG).show()


            //step3: write to database(product section), update stock amount
        }



    }
