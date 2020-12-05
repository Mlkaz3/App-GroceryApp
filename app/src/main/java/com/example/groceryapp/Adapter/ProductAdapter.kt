package com.example.groceryapp.Adapter

import android.R.attr.password
import android.accounts.AccountManager.KEY_PASSWORD
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
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.Model.Product
import com.example.groceryapp.R
import com.squareup.picasso.Picasso
import org.json.JSONException
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
        //check the local array list empty or not
        Log.e("winnie", products[0].toString())

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
            //step0: retrieve the value and store it in a local variable
            var name = products[position].productName
            var image = products[position].productImage
            var price = products[position].productPrice
            var stock = products[position].productStock
            var category = products[position].productCategory
            var id = products[position].productID
            //things to be added into cart
            var product: Product = Product(id,name,price,category,image,stock)

            if(stock<=0){
                Toast.makeText(context, "The product is currently out of stock", Toast.LENGTH_LONG).show()
            }else{
                AddCart(product)
            }
        }
    }

    //AddCart function it works!! but sadly cant read from database
    //success and the toast work too
    //ERROR OCURRED: ONLY ABLE TO WRITE ONCE TO DATABASE
    fun AddCart(product: Product) {
        Log.e("Database", "opening")
        //step1: write to database(cart section), update cart item :)
        //url need to add in the string ?product_id = XX
        val url = "https://groceryapptarucproject.000webhostapp.com/grocery/cart/insertcartnoduplicate.php?cart_id=1&product_id=" + product.productID

        val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    // Process the JSON
                    Log.e("winnie", response.toString())
                    try {
                        if (response != null) {
                            Toast.makeText(context, product.productName + " added to cart", Toast.LENGTH_LONG).show()

                        }
                    } catch (e: Exception) {
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
}




