package com.moonborn.walmart.view.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.moonborn.walmart.view.activities.BaseActivity
import com.moonborn.walmart.view.activities.MainActivity
import com.moonborn.walmart.R
import com.moonborn.walmart.application.WalmartApplication
import com.moonborn.walmart.model.entities.Product
import com.moonborn.walmart.view.adapters.ProductsInDealsAdapter
import com.moonborn.walmart.viewmodel.ProductViewModel
import com.moonborn.walmart.viewmodel.ProductViewModelFactory
import kotlinx.android.synthetic.main.cardview_layout.view.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.fragment_search.view.search_content_scrollview
import kotlinx.android.synthetic.main.fragment_search.view.search_layout_top_bar_back_btn
import kotlinx.android.synthetic.main.fragment_search.view.search_text
import kotlinx.android.synthetic.main.fragment_see_all.view.*
import kotlinx.android.synthetic.main.search_layout.view.*
import kotlinx.android.synthetic.main.search_layout_content.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    val search_products = mutableListOf<Product>()

    private val mProductViewModel : ProductViewModel by viewModels {
        ProductViewModelFactory((requireActivity().application as WalmartApplication).productsRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)


        return rootView
    }

    override fun onViewCreated(rootView: View, savedInstanceState: Bundle?) {

        BaseActivity.webview(rootView.progress_bar_holder_search_layout)

        val searchProductsAdapter = ProductsInDealsAdapter(this, R.id.search_fragment)

//        searchProductsAdapter.productList(search_products)
        searchProductsAdapter.differ.submitList(search_products)
        val SearchProductsContent = LayoutInflater.from(requireContext()).inflate(R.layout.search_layout_content, null)
//        SearchProductsContent.setBackgroundColor(resources.getColor(R.color.black))

        rootView.search_content_scrollview.addView(SearchProductsContent)
        SearchProductsContent.search_products_recyclerview.adapter = searchProductsAdapter



        rootView.search_layout_top_bar_back_btn.setOnClickListener {
//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_main_container, MainFragment(), "findThisFragment")
//                .addToBackStack(null)
//                .commit()

            requireActivity().supportFragmentManager.popBackStack()

//            requireActivity().supportFragmentManager.beginTransaction().remove(SearchFragment()).commitAllowingStateLoss();
        }



        rootView.search_text.onFocusChangeListener =
            OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    requireActivity().getWindow()
                        .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
                } else {
                    requireActivity().getWindow()
                        .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
                }
            }

        rootView.search_text.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                rootView.search_content_scrollview.visibility = View.GONE
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                rootView.search_content_scrollview.visibility = View.GONE
            }




            override fun afterTextChanged(s: Editable) {
                val string = s.toString()
                if(string != ""){

                    mProductViewModel.getFilteredList(string).observe(viewLifecycleOwner){
                        list -> list.let {
                        searchProductsAdapter.differ.submitList(list){
                            rootView.search_content_scrollview.visibility = View.VISIBLE
                        }
                    }


                    }
                } else {
                    searchProductsAdapter.differ.submitList(null)
                    rootView.search_content_scrollview.visibility = View.VISIBLE
                }
            }
        })



        super.onViewCreated(rootView, savedInstanceState)
    }

}

