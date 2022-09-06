package com.shop_user.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shop_user.R
import com.shop_user.databinding.ItemProductOfSellerBinding
import com.shop_user.interfaces.ClickedOnProductOfSeller
import com.shop_user.model.ProductModel

class ProductOfSellerAdapter(private var productModel: ArrayList<ProductModel>, private var clickedOnProductOfSeller: ClickedOnProductOfSeller): RecyclerView.Adapter<ProductOfSellerAdapter.ProductsOfSellerViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsOfSellerViewHolder
    {
        return ProductsOfSellerViewHolder(ItemProductOfSellerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductsOfSellerViewHolder, position: Int)
    {
        val model: ProductModel = productModel[position]

        Glide
            .with(holder.itemView.context)
            .load(model.image)
            .placeholder(R.drawable.ic_product)
            .error(R.drawable.ic_product)
            .into(holder.bind.imgItemProduct)
        holder.bind.txtTitleItemProduct.text = model.title
        holder.bind.txtPriceItemProduct.text = model.price + " EGP"

        holder.itemView.setOnClickListener {

            clickedOnProductOfSeller.clickedOnProductOfSeller(model)
        }
    }

    override fun getItemCount(): Int
    {
        return productModel.size
    }

    class ProductsOfSellerViewHolder(binding: ItemProductOfSellerBinding) : RecyclerView.ViewHolder(binding.root)
    {
        var bind = binding
    }

}