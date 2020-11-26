package com.example.groceryapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.Model.Product
import com.example.groceryapp.R
import com.squareup.picasso.Picasso
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

    }

}