package com.moonborn.walmart.view.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.moonborn.walmart.view.fragments.CategroiesFragment
import com.moonborn.walmart.view.activities.MainActivity
import com.moonborn.walmart.Models.CategoryModel
import com.moonborn.walmart.R
import kotlinx.android.synthetic.main.categories_model.view.*


class CategorieslayoutAdapter(val context: Context, val items: MutableList<CategoryModel>) :
    RecyclerView.Adapter<CategorieslayoutAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name = view.category_txt
        val image = view.category_img

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.categories_model,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        Log.i("categoryAdapterItem", "${item.getName()}")

        holder.name.text = item.getName()

        val url = "https://firebasestorage.googleapis.com/v0/b/walmart-b34bb.appspot.com/o/market_products.jpg?alt=media&token=f962dba8-7590-4c2a-a92b-fabff29b1268"
        Glide
            .with(context)
            .load(url)
            .fitCenter()
            .transform(RoundedCorners(context.resources.getDimension(R.dimen.category_Views_corner_radius).toInt()))
            .placeholder(R.drawable.food_products)
            .dontAnimate()
            .into(holder.image)
            .view.background = context.resources.getDrawable(R.drawable.categories_cardview_bg)



        holder.itemView.setOnClickListener {
            if (context is MainActivity){
                CategroiesFragment.selected_category(item.getName(), context)
                CategroiesFragment.selected_category = item.getName()
                CategroiesFragment.selectedBaseCategory = item.getName()
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


}