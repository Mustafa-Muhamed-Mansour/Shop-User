package com.shop_user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shop_user.R
import com.shop_user.databinding.ItemSellerBinding
import com.shop_user.interfaces.ClickedOnSeller
import com.shop_user.model.SellerModel

class SellerAdapter(private var sellerModel: ArrayList<SellerModel>, private var clickedOnSeller: ClickedOnSeller): RecyclerView.Adapter<SellerAdapter.SellerVH>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellerVH
    {
        return SellerVH(ItemSellerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SellerVH, position: Int)
    {
        val model: SellerModel = sellerModel[position]

        Glide
            .with(holder.itemView.context)
            .load(model.image)
            .placeholder(R.drawable.ic_seller)
            .error(R.drawable.ic_seller)
            .into(holder.bind.imgItemSeller)
        holder.bind.txtAddressItemSeller.text = model.address

        holder.itemView.setOnClickListener {

            clickedOnSeller.clickedOnSeller(model)
        }
    }

    override fun getItemCount(): Int
    {
        return sellerModel.size
    }

    class SellerVH(binding: ItemSellerBinding) : RecyclerView.ViewHolder(binding.root)
    {

        val bind = binding
    }
}