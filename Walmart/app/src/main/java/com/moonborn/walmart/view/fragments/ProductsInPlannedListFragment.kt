package com.moonborn.walmart.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.moonborn.walmart.view.adapters.ProductsInPlannedListAdapter
import com.moonborn.walmart.Models.PlannedListModel
import com.moonborn.walmart.R
import com.moonborn.walmart.application.WalmartApplication
import com.moonborn.walmart.model.entities.PlannedListsModel
import com.moonborn.walmart.model.entities.Product
import com.moonborn.walmart.viewmodel.ProductViewModel
import com.moonborn.walmart.viewmodel.ProductViewModelFactory
import kotlinx.android.synthetic.main.fragment_products_in_planned_list.view.*
import kotlinx.android.synthetic.main.items_in_planned_list_content.view.*


class ProductsInPlannedListFragment : Fragment() {

    companion object{
        var selectedPlannedList: PlannedListsModel? = null
        var selectedPlannedListItems: MutableList<String>? = null
    }

    private val mProductViewModel : ProductViewModel by viewModels {
        ProductViewModelFactory((requireActivity().application as WalmartApplication).productsRepository)
    }

    var root_view: View? = null

    val itemsInSelectedList = mutableListOf<Product>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_products_in_planned_list, container, false)

        root_view = rootView
        root_view!!.isFocusable = true



        rootView.profile_btn_products_planned_list_fragment.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        rootView.top_bar_layout_name.text = selectedPlannedList?.ListName

        updateProductsInPlannedList(root_view!!, selectedPlannedListItems!! as MutableList<String>)


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    fun updateProductsInPlannedList(rootView: View, productsInPlannedList: MutableList<String>){
        if(selectedPlannedList!!.ItemsInList.isNotEmpty()){
            val ProductsInPlannedListAdapter = ProductsInPlannedListAdapter(this)
            val ProductsInPlannedListContent = LayoutInflater.from(requireContext()).inflate(R.layout.items_in_planned_list_content, null)
            rootView.products_in_planned_list_scrollview.elevation = 10f
            rootView.products_in_planned_list_scrollview.addView(ProductsInPlannedListContent)
            rootView.products_in_planned_list_recyclerview.adapter = ProductsInPlannedListAdapter
            for (i in productsInPlannedList) {
                mProductViewModel.getProductById(i).observe(viewLifecycleOwner){
                    product -> product.let {
                        itemsInSelectedList.add(product)
                        ProductsInPlannedListAdapter.differ.submitList(itemsInSelectedList)
                        if(rootView.empty_planned_list_layout_products_in_planned_list_fragment.visibility == View.VISIBLE) {
                            rootView.empty_planned_list_layout_products_in_planned_list_fragment.visibility =
                                View.GONE
                            rootView.amount_of_products_in_planned_lists.visibility = View.VISIBLE
                        }
                    }
                }

            }

        }
    }

    override fun onResume() {

//        root_view?.let { updateProductsInPlannedList(it, selectedPlannedListItems!!) }

        val ProductsInPlannedListAdapter = ProductsInPlannedListAdapter(this)
        ProductsInPlannedListAdapter.differ.submitList(null)
        ProductsInPlannedListAdapter.differ.submitList(itemsInSelectedList)

        root_view?.products_in_planned_list_recyclerview?.adapter = ProductsInPlannedListAdapter
        super.onResume()
    }

}