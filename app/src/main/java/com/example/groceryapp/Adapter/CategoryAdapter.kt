package com.example.groceryapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.Model.CartItem
import com.example.groceryapp.Model.Product
import com.example.groceryapp.R
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class CategoryAdapter(contexts: Context,private var itemOnClickListener:ProductItemOnClickListener): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    //declare a context
    var context: Context = contexts
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    // Cached copy of user
    private var products = emptyList<Product>()

    //round off function
    val df: DecimalFormat = DecimalFormat("0.00")

    //to hold one single view only
    inner class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val img: ImageView = itemView.findViewById(R.id.img)
        val title: TextView = itemView.findViewById(R.id.name)
        val stock: TextView = itemView.findViewById(R.id.productStock)
        val price: TextView = itemView.findViewById(R.id.productPrice)
        val buttonAddCart: Button = itemView.findViewById(R.id.addCartButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.CategoryViewHolder {
        val itemView = inflater.inflate(R.layout.category,parent,false)
        return CategoryViewHolder(itemView)
    }

    override fun getItemCount() = products.size

    internal fun setProducts(product: List<Product>) {
        this.products = product
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
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

        //step0: retrieve the value and store it in a local variable
        var name = products[position].productName
        var image = products[position].productImage
        var price = products[position].productPrice
        var stock = products[position].productStock
        var category = products[position].productCategory
        var id = products[position].productID
        //things to be added into cart
        var product: Product = Product(id,name,price,category,image,stock)

        //when the user press add to cart button, called AddCart function
        holder.buttonAddCart.setOnClickListener {
            //when the user press add to cart button, called AddCart function
            itemOnClickListener.addCartClicked(product,position)

        }
    }}