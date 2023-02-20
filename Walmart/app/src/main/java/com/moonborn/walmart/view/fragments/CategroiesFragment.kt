package com.moonborn.walmart.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.moonborn.walmart.view.adapters.CategorieslayoutAdapter
import com.moonborn.walmart.view.activities.BaseActivity
import com.moonborn.walmart.Constants
import com.moonborn.walmart.view.activities.MainActivity
import com.moonborn.walmart.R
import com.moonborn.walmart.firebase.FirestoreClass
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.categories.*
import kotlinx.android.synthetic.main.categories.view.*
import kotlinx.android.synthetic.main.categories_recyclerview.*
import kotlinx.android.synthetic.main.categories_recyclerview.view.*
import kotlinx.android.synthetic.main.fragment_cart.view.*
import kotlinx.android.synthetic.main.fragment_categories_at_top.view.*
import kotlinx.android.synthetic.main.fragment_categroies.*
import kotlinx.android.synthetic.main.fragment_categroies.view.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.see_all.view.*
import kotlinx.coroutines.*


/**
 * A simple [Fragment] subclass.
 * Use the [CategroiesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategroiesFragment : Fragment() {



    var progressbar: WebView? = null
    var RecyclerView: RecyclerView? = null

    companion object{
        var selected_category: String = ""
        var selectedBaseCategory: String = ""

        //Tag for opening productFragment from CategoriesFragment
        var categoriesFragTag: String = "fromCategories"

        //Which category is selected in categories layoyt, for recycler view
        fun selected_category(selected_category: String, context: MainActivity){
            if(MainActivity.sorted_by_categories[selected_category] != null){
//            categoriesProductsContent?.let { categories_products_scrollview.addView(it) }


            }

            Log.i("SelectedCategory", "${MainActivity.sorted_by_categories[selected_category]}")

            var productInCategoriesFragment = ProductInCategoriesFragment()

            context.supportFragmentManager.beginTransaction()
                .replace(R.id.categories_fragment, productInCategoriesFragment, "categories_fragment")
                .addToBackStack(null)
                .commit()

        }

    }
    var categoriesProductsContent: View? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_categroies, container, false)


        BaseActivity.webview(rootView.categories_content_layout)

        setHasOptionsMenu(true)

        MainActivity.currentFragment = 1


        return rootView
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val loadCategories = GlobalScope.launch {
            val categoryObjects = FirestoreClass().loadCategories(
                view as ViewGroup,
                view.categories_scrollview,
                view.categories_fragment_recyclerview as RecyclerView, requireContext(), this@CategroiesFragment
            )
            Constants.categories = categoryObjects

            Log.i("ConstantsCatego", "${Constants.categories}")

        }

        view.categories_profile_btn.setOnClickListener {
            Log.i("MotionLayout", "${(requireActivity() as MainActivity).findViewById<MotionLayout>(R.id.activity_main_layout)}")
            val motionLayout: MotionLayout = (requireActivity() as MainActivity).findViewById(R.id.activity_main_layout)
            motionLayout.transitionToEnd()
        }

        categoriesProductsContent = LayoutInflater.from(requireContext()).inflate(R.layout.selected_brand_or_category_content, null)

        Log.i("categoriesProductsContent", "${categoriesProductsContent}")


        val CategoriesAdapter = CategorieslayoutAdapter(requireContext(), Constants.categories)
        val recyclerView = view.categories_fragment_recyclerview as RecyclerView
        recyclerView.adapter = CategoriesAdapter


    }

    override fun onResume() {
        Log.i("Categoriesssss", "${Constants.categories}")
        val recyclerView = categories_fragment_recyclerview as RecyclerView


        super.onResume()
    }

}