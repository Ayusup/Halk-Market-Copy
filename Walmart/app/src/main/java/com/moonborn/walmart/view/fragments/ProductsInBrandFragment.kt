package com.moonborn.walmart.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moonborn.walmart.view.adapters.ProductsByCategoryOrBrandAdapter
import com.moonborn.walmart.view.activities.BaseActivity
import com.moonborn.walmart.Constants
import com.moonborn.walmart.R
import com.moonborn.walmart.model.entities.Product
import kotlinx.android.synthetic.main.fragment_products_in_brand.view.*


class ProductsInBrandFragment : Fragment() {


    companion object{
        var selectedBrand: String? = null
    }

    var productsInSelectedCategory: MutableList<Product> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_products_in_brand, container, false)

        rootView.products_in_brand_scrollview.visibility = View.GONE
        BaseActivity.webview(rootView.progress_bar_holder_products_in_brands_fragment)


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(selectedBrand != null){
            for(i in Constants.pList){
                if(i.brand == selectedBrand){
                    productsInSelectedCategory.add(i)
                }
            }
        }

        view.products_in_brand_top_bar_name.text = selectedBrand

        view.brand_content_layout_back_btn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        val ProductsInBrandsAdapter = ProductsByCategoryOrBrandAdapter(this, productsInSelectedCategory, R.id.products_in_brad_fragment)
        val selectedCategoryRecyclerview = LayoutInflater.from(requireContext()).inflate(R.layout.selected_brand_or_category_content, null) as RecyclerView

        view.products_in_brand_scrollview.addView(selectedCategoryRecyclerview)
        selectedCategoryRecyclerview.adapter = ProductsInBrandsAdapter
        view.products_in_brand_scrollview.visibility = View.VISIBLE


    }

    override fun onDestroy() {
        super.onDestroy()
        selectedBrand = null
    }

}