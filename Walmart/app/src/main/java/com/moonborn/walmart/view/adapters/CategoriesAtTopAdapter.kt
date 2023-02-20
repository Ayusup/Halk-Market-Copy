package com.moonborn.walmart.view.adapters

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.moonborn.walmart.view.fragments.CategoriesAtTopFragment
import com.moonborn.walmart.view.fragments.ProductInCategoriesFragment
import com.moonborn.walmart.view.activities.MainActivity
import com.moonborn.walmart.Models.CategoryModel
import com.moonborn.walmart.R
import kotlinx.android.synthetic.main.categories_at_top_model.view.*
import kotlinx.android.synthetic.main.subcategories_at_the_top_recyclerview.view.*


class CategoriesAtTopAdapter(val context: Context, val items: MutableList<CategoryModel>, val container: Int, val rootView: ViewGroup) :
    RecyclerView.Adapter<CategoriesAtTopAdapter.ViewHolder>() {


    var activeSubCategory: View? = null
    var activePullDownArrow: View? = null


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val categoryName = view.category_Name
        val pullDownArrow = view.pull_down_arrow
        val subCategoriesHolder = view.sub_categories_holder
        val subCategoriesAtTopSeeAll = view.sub_category_at_top_see_all


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {



        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.categories_at_top_model,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.categoryName.text = item.getName()


        holder.subCategoriesAtTopSeeAll.setOnClickListener {
            if(context is MainActivity) {
                CategoriesAtTopFragment.selectedCategory = item.getName()
                context.supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.categories_at_top_fragment,
                        ProductInCategoriesFragment(),
                        "categories_at_top_fragment"
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }

        val recyclerViewLayout = LayoutInflater.from(rootView.context).inflate(R.layout.subcategories_at_the_top_recyclerview, null)

        holder.subCategoriesHolder.addView(recyclerViewLayout)

        holder.itemView.setOnClickListener {


            holder.pullDownArrow.startAnimation(AnimationUtils.loadAnimation(context, R.anim.pull_down_arrow_rotate_down))
            holder.pullDownArrow.rotation = 180F






            recyclerViewLayout.subcategories_at_top_recyclerview.adapter = SubCategoriesAtTopAdapter(context, item.getCategoriesInside())
            if(activeSubCategory != holder.itemView || activeSubCategory == null){
                holder.subCategoriesHolder.visibility = View.VISIBLE
                holder.subCategoriesHolder.startAnimation(AnimationUtils.loadAnimation(context, R.anim.sub_category_at_top_expand))

                if(activeSubCategory != null && activeSubCategory != holder.itemView){
                    activeSubCategory!!.sub_categories_holder.startAnimation(AnimationUtils.loadAnimation(context, R.anim.sub_category_at_top_collapse))
                    activeSubCategory!!.pull_down_arrow.startAnimation(AnimationUtils.loadAnimation(context, R.anim.pull_down_arrow_rotate_up))
                    activeSubCategory!!.pull_down_arrow.rotation = 0F

                    val r = Runnable {
                        activeSubCategory!!.sub_categories_holder.visibility = View.GONE
                        activeSubCategory = holder.itemView
                    }
                    Handler().postDelayed(r, 84)
                }else{
                    activeSubCategory = holder.itemView
                }
            } else {
                activeSubCategory!!.sub_categories_holder.startAnimation(AnimationUtils.loadAnimation(context, R.anim.sub_category_at_top_collapse))
                activeSubCategory!!.pull_down_arrow.startAnimation(AnimationUtils.loadAnimation(context, R.anim.pull_down_arrow_rotate_up))
                activeSubCategory!!.pull_down_arrow.rotation = 0F


                val r = Runnable {
                    activeSubCategory!!.sub_categories_holder.visibility = View.GONE
                    activeSubCategory = null
                }
                Handler().postDelayed(r, 84)
            }



        }



    }



    override fun getItemCount(): Int {
        return items.size
    }
}