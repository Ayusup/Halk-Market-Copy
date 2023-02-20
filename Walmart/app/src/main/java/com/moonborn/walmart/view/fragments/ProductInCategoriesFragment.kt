package com.moonborn.walmart.view.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.moonborn.walmart.view.adapters.ProductsByCategoryOrBrandAdapter
import com.moonborn.walmart.view.activities.BaseActivity
import com.moonborn.walmart.Constants
import com.moonborn.walmart.view.fragments.CategroiesFragment.Companion.selected_category
import com.moonborn.walmart.R
import com.moonborn.walmart.firebase.FirestoreClass
import com.moonborn.walmart.model.entities.Product
import kotlinx.android.synthetic.main.active_sub_categories_model.view.*
import kotlinx.android.synthetic.main.cardview_layout.view.*
import kotlinx.android.synthetic.main.categories.view.*
import kotlinx.android.synthetic.main.fragment_categroies.*
import kotlinx.android.synthetic.main.fragment_product_in_categories.*
import kotlinx.android.synthetic.main.fragment_product_in_categories.view.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.search_layout_content.view.*
import kotlinx.android.synthetic.main.selected_brand_or_category_content.view.*
import kotlinx.android.synthetic.main.suggestions_recyclerview.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [ProductInCategoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductInCategoriesFragment : Fragment() {

    var suggestionsView: RecyclerView? = null
    var selectedCategory = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_product_in_categories, container, false)

        selectedCategoryContent.clear()
        Companion.active_categories.clear()

        BaseActivity.webview(rootView.progress_bar_holder_products_in_categories)

        if(tag == "categories_at_top_fragment"){
            selectedCategory = CategoriesAtTopFragment.selectedCategory
        } else{
            selectedCategory = CategroiesFragment.selected_category
        }


        Log.i("baseCategory", "$baseCategory")

//        // Set up suggestions for the layout
//        if (Constants.categoriesNames[selected_category] != null && Constants.categoriesNames[selected_category]!!.isNotEmpty()){
//            subCategories = Constants.categoriesNames[selected_category]!!
//            val suggestionsAdapter = SuggestionsAdapter(requireContext(), subCategories, rootView.findViewById(R.id.categories_recyclerview_holder_layout))
//            val suggestions = rootView.suggestions_recyclerView as RecyclerView
//            suggestions.adapter= suggestionsAdapter
//        }




        rootView.categories_content_layout_back_btn.setOnClickListener {
//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.categories_fragment, CategroiesFragment(), "")
//                .addToBackStack(null)
//                .commit()

            requireActivity().supportFragmentManager.popBackStack()

        }



        return rootView

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i in Constants.pList){
            val productCategory = i.category.split(":")
            if(productCategory[0] == selectedCategory){
                selectedCategoryContent.add(i)
                Log.i("productCategory", "${i.category}")
                Log.i("productCategoryy", "${selectedCategory}")
                Log.i("productCategoryyy", "${productCategory[0]}")

            }
        }
        Companion.active_categories.put(selectedCategory, selectedCategoryContent)
        baseCategory = selectedCategoryContent


        val searchedItemsInCategoryAdapter = ProductsByCategoryOrBrandAdapter(this, searchedItemsInCategoryList, R.layout.fragment_product_in_categories)

        val selectedCategoryRecyclerview = LayoutInflater.from(requireContext()).inflate(R.layout.selected_brand_or_category_content, null) as RecyclerView

//        selectedCategoryContent = MainActivity.sorted_by_categories[selected_category]!!
        selectedCategoryRecyclerview.adapter = ProductsByCategoryOrBrandAdapter(this, selectedCategoryContent, R.id.categories_recyclerview_holder_layout)
        Log.i("productCategoryyy", "$selectedCategoryContent")
        view.selected_categories_products_scrollview.addView(selectedCategoryRecyclerview)
        view.progress_bar_holder_products_in_categories.visibility = View.GONE

        view.products_in_categories_top_bar_name.text = selectedCategory
        Log.i("products_in_categories_top_bar_name", "${view.products_in_categories_top_bar_name.text}")


        view.search_text_categories_lay.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                view.selected_categories_products_scrollview.removeView(selectedCategoryRecyclerview)

//                rootView.selected_categories_products_scrollview.addView(selectedCategoryRecyclerview)
//                rootView.selected_categories_products_scrollview.adapter = searchedItemsInCategoryAdapter
            }

            override fun afterTextChanged(s: Editable) {
                val string = s.toString()
                searchedItemsInCategoryList.clear()
                if(string != ""){
                    for(i in selectedCategoryContent){
                        if(string.lowercase() in i.name.lowercase()){
                            searchedItemsInCategoryList.add(i)
                        }
                    }

                }
                view.selected_categories_products_scrollview.removeView(selectedCategoryRecyclerview)
                view.selected_categories_products_scrollview.addView(selectedCategoryRecyclerview)
                view.selected_categories_recyclerview.adapter = searchedItemsInCategoryAdapter


            }
        })

    }


    companion object{
        var baseCategory = mutableListOf<Product>()
        var active_categories = mutableMapOf<String, MutableList<Product>>()
        var selectedCategoryContent = mutableListOf<Product>()
        val searchedItemsInCategoryList: MutableList<Product> = mutableListOf()
        var subCategories: MutableList<String> = mutableListOf()


        fun chooseSubCategory(rootView: ViewGroup, category: String, baseCategory: MutableList<Product>){
            var fireStoreClass = FirestoreClass()


            val progressBar = WebView(rootView.context)
            progressBar.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            progressBar.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    if (url != null) {
                        view?.loadUrl(url)
                    }
                    return true
                }
            }

            progressBar.loadUrl("file:///android_asset/dots.html")


            BaseActivity.loadView(rootView.products_in_categories_parent_view, rootView.products_in_categories_scrollview, progressBar, true)
            selected_category = category



            fireStoreClass.loadSubCategory(rootView, category, progressBar)




            active_categories.put(category, selectedCategoryContent)

            for (i in Constants.pList){
                val productCategory = i.category.split(":")
                if(selected_category in productCategory && i.id !in selectedCategoryContent.associateBy { it.id }){
                    selectedCategoryContent.add(i)
                }
            }

            if(active_categories.size > 1){

                val activeCategoryView = LayoutInflater.from(rootView.context).inflate(R.layout.active_sub_categories_model, null)
                activeCategoryView.active_sub_category_nameView.text = category
                rootView.active_categories.addView(activeCategoryView)
//                rootView.active_categories.active_sub_category_nameView.text = category
            }
            Log.i("active_categories", "${rootView.active_categories.childCount}")


            rootView.selected_categories_recyclerview.adapter!!.notifyDataSetChanged()
            rootView.products_in_categories_top_bar_name.text = selected_category

            Log.i("sub", "$subCategories")



        }


    }


}