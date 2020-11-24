package com.example.groceryapp.Adapter

/*credit for recycler view adapter:
    https://www.youtube.com/watch?v=E3x6pCZutLA&t=207s
    https://www.youtube.com/watch?v=rBQi_7L-Uc8
*/

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.Model.Product
import com.example.groceryapp.Model.TopProductItem
import com.example.groceryapp.R
import com.squareup.picasso.Picasso


class TopProductAdapter(val context:Context, val topProduct:List<Product>) : RecyclerView.Adapter<TopProductAdapter.TopProductViewHolder>(){

    inner class TopProductViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var productNameText: TextView = itemView.findViewById(R.id.product_title)
        var productImg:ImageView = itemView.findViewById(R.id.image_product)

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
//        holder.productNameText.text = "${topProduct?.name}"
//        val topProductImageLink = "${topProduct?.link}"
        holder.productNameText.text = topProduct.getName()
        val topProductImageLink = topProduct.getLink()
        Picasso.with(context).load(topProductImageLink).into(holder.productImg)

    }


}
