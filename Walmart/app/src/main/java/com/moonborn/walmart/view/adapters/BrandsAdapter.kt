package com.moonborn.walmart.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.moonborn.walmart.view.fragments.ProductsInBrandFragment
import com.moonborn.walmart.view.activities.MainActivity
import com.moonborn.walmart.Models.BrandModel
import com.moonborn.walmart.R
import kotlinx.android.synthetic.main.brand_view_model.view.*

class BrandsAdapter(val context: Context, val items: MutableList<BrandModel>):
    RecyclerView.Adapter<BrandsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val brandView = view.brand_layout_holder
        val name = view.brand_name
        val image = view.brand_image

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.brand_view_model,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.name.text = item.getName()

        Glide
            .with(context)
            .load(item.getImage())
            .fitCenter()
            .placeholder(R.drawable.walmart_lgo)
            .dontAnimate()
            .into(holder.image)


        holder.brandView.setOnClickListener {
            if(context is MainActivity){
                ProductsInBrandFragment.selectedBrand = item.getName()
                context.supportFragmentManager.beginTransaction()
                    .replace(R.id.brands_fragment, ProductsInBrandFragment(), "findThisFragment")
                    .addToBackStack(null)
                    .commit()
            }
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
}