package com.moonborn.walmart.view.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moonborn.walmart.R
import kotlinx.android.synthetic.main.categories_at_top_model.view.category_Name

class SubCategoriesAtTopAdapter(val context: Context, val items: MutableList<String>) :
    RecyclerView.Adapter<SubCategoriesAtTopAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val categoryName = view.category_Name


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  ViewHolder{
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.sub_categories_at_top_model,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.categoryName.text = item



    }

    override fun getItemCount(): Int {
        return items.size
    }




}