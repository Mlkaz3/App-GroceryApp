package com.example.groceryapp.Adapter

/*credit for recycler view adapter:
    https://www.youtube.com/watch?v=E3x6pCZutLA&t=207s
*/

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.Model.Product

class TopProductAdapter(val context:Context, val topProduct:List<Product>) : RecyclerView.Adapter<TopProductAdapter.TopProductViewHolder>(){

    inner class TopProductViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val productNameText: TextView = itemView.findViewById(R.id.product_title)
        val productImg : TextView = itemView.findViewById(R.id.image_product)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product,parent,false)
        return TopProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return topProduct.size
    }

    override fun onBindViewHolder(holder: TopProductViewHolder, position: Int) {
        val topProduct = topProduct[position]
        holder.productNameText.text = topProduct.getName()
        holder.productImg.text = topProduct.getLink()


    }
}