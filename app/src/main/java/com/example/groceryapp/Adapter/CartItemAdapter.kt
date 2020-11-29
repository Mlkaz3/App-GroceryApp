package com.example.groceryapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.Model.CartItem
import com.example.groceryapp.Model.Product
import com.example.groceryapp.R
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class CartItemAdapter(contexts: Context, private val cartItemList: ArrayList<CartItem>,private val itemOnClickListener: CartItemOnClickListener): RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>() {

    //declare a context
    var context: Context = contexts

    //round off function
    val df: DecimalFormat = DecimalFormat("0.00")

    //to hold one single view only
    inner class CartItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val img: ImageView = itemView.findViewById(R.id.imageView)
        val title: TextView = itemView.findViewById(R.id.product_cart)
        val price: TextView = itemView.findViewById(R.id.price_cart)
        var qty:TextView = itemView.findViewById(R.id.qty)
        val addButton: Button = itemView.findViewById(R.id.buttonAdd)
        val minusButton: Button = itemView.findViewById(R.id.buttonMinus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.cartproduct,parent,false)
        return CartItemViewHolder(itemView)
    }

    override fun getItemCount() = cartItemList.size

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        //getting the current item
        val currentItem = cartItemList[position]

        //load img of the current item using picasso
        Picasso.with(context).load(currentItem.product_img).into(holder.img)

        //load the tittle, price and qty of current item in arrayList
        holder.title.text = currentItem.product_name
        holder.price.text = "RM" + df.format(currentItem.product_price).toString()
        holder.qty.text = currentItem.productQty.toString()

        //add button onclick listener
        holder.addButton.setOnClickListener {
            itemOnClickListener.addQtyClicked(currentItem,position)
        }

        //subtract button onclick listener
        holder.minusButton.setOnClickListener {
            itemOnClickListener.minusQtyClicked(currentItem,position)
        }
    }
}