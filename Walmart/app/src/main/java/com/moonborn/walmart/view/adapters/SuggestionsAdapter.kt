package com.moonborn.walmart.view.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moonborn.walmart.view.fragments.ProductInCategoriesFragment
import com.moonborn.walmart.R
import kotlinx.android.synthetic.main.fragment_product_in_categories.view.*
import kotlinx.android.synthetic.main.suggestion_view_model.view.*

class SuggestionsAdapter(val context: Context, val items: MutableList<String>, val rootView: ViewGroup) :
    RecyclerView.Adapter<SuggestionsAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val suggestionTxt = view.suggestion_text
        val suggestionView = view.suggestion_view

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.suggestion_view_model,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.suggestionTxt.text = item

        holder.itemView.setOnClickListener {
            ProductInCategoriesFragment.chooseSubCategory(rootView, item, ProductInCategoriesFragment.baseCategory)
            var suggestionsView: RecyclerView = rootView.suggestions_recyclerView as RecyclerView
            suggestionsView.adapter = SuggestionsAdapter(context,
                ProductInCategoriesFragment.subCategories, rootView.findViewById(R.id.categories_recyclerview_holder_layout))
            Log.i("subCategories", "${ProductInCategoriesFragment.subCategories}")

        }


    }

    override fun getItemCount(): Int {
        return items.size
    }

}