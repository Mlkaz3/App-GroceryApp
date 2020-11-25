package com.example.groceryapp.Adapter
//CREDIT:
//https://www.youtube.com/watch?v=E3x6pCZutLA&t=207s
//https://www.youtube.com/watch?v=rBQi_7L-Uc8


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


class TopProductAdapter(val context:Context, val topProduct:ArrayList<Product>) :
        RecyclerView.Adapter<TopProductAdapter.TopProductViewHolder>(){

    //Cached copy of user
    private var products = emptyList<Product>()

    //to hold one single record only
    inner class TopProductViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var productNameText: TextView = itemView.findViewById(R.id.product_title)
        var productImg:ImageView = itemView.findViewById(R.id.image_product)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopProductViewHolder {
        //to create one single record
        val view = LayoutInflater.from(context).inflate(R.layout.product,parent,false)
        return TopProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return topProduct.size
    }

    internal fun setProduct(products: ArrayList<Product>) {
        this.products = products
    }

    override fun onBindViewHolder(holder: TopProductViewHolder, position: Int) {
        val topProduct = topProduct[position]
//        holder.productNameText.text = "${topProduct?.name}"
//        val topProductImageLink = "${topProduct?.link}"

        //for displaying
        holder.productNameText.text = topProduct.getName()
        val topProductImageLink = topProduct.getLink()
        Picasso.with(context).load(topProductImageLink).into(holder.productImg)

    }


}
