package com.moonborn.walmart.view.fragments

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.moonborn.walmart.view.adapters.ProductsInDealsAdapter
import com.moonborn.walmart.view.activities.BaseActivity
import com.moonborn.walmart.R
import com.moonborn.walmart.application.WalmartApplication
import com.moonborn.walmart.model.entities.Product
import com.moonborn.walmart.viewmodel.ProductViewModel
import com.moonborn.walmart.viewmodel.ProductViewModelFactory
import kotlinx.android.synthetic.main.fragment_see_all.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SeeAllFragment : Fragment() {

    companion object{

        const val TOPBARNAME = "top_bar_name"
        const val DEALNAME = "deal_name"
        const val PRODUCTSINSELECTEDDEAL = "products_in_deal_list"

    }
    var dealName: String? = null


    private val mProductViewModel : ProductViewModel by viewModels {
        ProductViewModelFactory((requireActivity().application as WalmartApplication).productsRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val rootView = inflater.inflate(R.layout.fragment_see_all, container, false)



        return rootView
    }

    var SelectedDealAdapter: ProductsInDealsAdapter? = null

    override fun onViewCreated(rootView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(rootView, savedInstanceState)

        BaseActivity.webview(rootView.progress_bar_holder_see_all_layout)
//        rootView.see_all_scrollview.visibility = View.GONE

        val seeAlllayoutContent = LayoutInflater.from(requireContext()).inflate(R.layout.see_all_layout_content, null) as RecyclerView


        dealName = arguments?.getString(DEALNAME)
        var topBarName = arguments?.getInt(TOPBARNAME)
        val productsList = arguments?.getParcelableArrayList<Product>(PRODUCTSINSELECTEDDEAL)

        Log.i("productList", "${arguments?.getParcelableArrayList<Product>(
            PRODUCTSINSELECTEDDEAL
        )}")

        rootView.see_all_deal_name.text = topBarName?.let { resources.getString(it) }

        SelectedDealAdapter = ProductsInDealsAdapter(this,  R.id.see_all_fragment)

        rootView.see_all_scrollview.addView(seeAlllayoutContent)
        seeAlllayoutContent.visibility = View.GONE

        seeAlllayoutContent.adapter = SelectedDealAdapter

        val r = Runnable {

            mProductViewModel.getProductByDeal(dealName!!).observe(viewLifecycleOwner){
                    products -> products.let {
                SelectedDealAdapter!!.differ.submitList(products)
            }
            }
            rootView.see_all_scrollview.visibility = View.VISIBLE
            seeAlllayoutContent.visibility = View.VISIBLE
        }

        Handler().postDelayed(r, 180)



        searchInDeals(
            rootView,
            SelectedDealAdapter as ProductsInDealsAdapter,
            seeAlllayoutContent as RecyclerView,
            productsList!!,
            SelectedDealAdapter as Any
        )


        rootView.see_all_scrollview.visibility = View.VISIBLE






        rootView.search_text_see_all_lay.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    requireActivity().getWindow()
                        .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
                } else {
                    requireActivity().getWindow()
                        .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
                }
            }

        rootView.search_text_see_all_lay.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if(hasFocus){
                requireActivity().getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            } else {
                requireActivity().getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            }

        }



        rootView.back_btn_see_all_fragment.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }


    }

    var searchProductsInDeals: MutableList<Product> = mutableListOf()

    fun searchInDeals(rootView: View, adapter: ProductsInDealsAdapter, content: RecyclerView, productsList: MutableList<Product>, searchProductsAdapter: Any){

        var selectedDealProductList: MutableList<Product> = productsList

        rootView.search_text_see_all_lay.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                rootView.see_all_scrollview.visibility = View.GONE
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                adapter.differ.submitList(null)
                rootView.see_all_scrollview.visibility = View.GONE
            }




            override fun afterTextChanged(s: Editable) {
                val string = s.toString()
                searchProductsInDeals.clear()
                if (string != "") {
//                    GlobalScope.launch(Dispatchers.Main) { //Your Main UI Thread
//
//                        withContext(Dispatchers.IO) {
//                            for (i in selectedDealProductList) {
//                                if (string.lowercase().trim() in i.name.lowercase().trim()) {
//                                    searchProductsInDeals.add(i)
//                                    Log.i("nameee", "${i.name}")
//                                }
//
//                            }

                    if(mProductViewModel.searchProductInDeals(string, dealName!!) != null) {
                        mProductViewModel.searchProductInDeals(string, dealName!!)
                            ?.observe(viewLifecycleOwner) { list ->
                                list.let {
//                                    searchProductsInDeals = list as MutableList<Product>
                                    adapter.differ.submitList(list)

                                }
                            }
                    } else {
                        adapter.differ.submitList(selectedDealProductList)

                        rootView.see_all_scrollview.visibility = View.VISIBLE
                    }

//                        }

//                    }
                } else {
                    adapter.differ.submitList(selectedDealProductList)

                    rootView.see_all_scrollview.visibility = View.VISIBLE


                }

            }

        })




        }

}









//setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//    override fun onQueryTextSubmit(query: String?): Boolean {
////                rootView.search_text_see_all_lay.clearFocus()
//
//
//
//        return false
//
//    }
//
//    override fun onQueryTextChange(query: String?): Boolean {
//        selectedDealProductList.clear()
//        for(i in productsList){
//            if(query.toString() in i.getName()){
//                selectedDealProductList.add(i)
//                Log.i("itemises", "$selectedDealProductList")
//            }
//        }
//
//        adapter.setFilteredData(selectedDealProductList)
//
//        adapter.setFilteredData(selectedDealProductList)
//        return false
//    }
//
//})


