package com.moonborn.walmart.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.moonborn.walmart.view.adapters.BrandsAdapter
import com.moonborn.walmart.view.activities.BaseActivity
import com.moonborn.walmart.Constants
import com.moonborn.walmart.R
import com.moonborn.walmart.application.WalmartApplication
import com.moonborn.walmart.firebase.FirestoreClass
import com.moonborn.walmart.viewmodel.ProductViewModel
import com.moonborn.walmart.viewmodel.ProductViewModelFactory
import kotlinx.android.synthetic.main.fragment_brands.view.*


class BrandsFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_brands, container, false)

        BaseActivity.webview(rootView.progress_bar_holder_brands_fragment)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        view.brands_back_btn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        if(Constants.brandsList.isEmpty()){
            FirestoreClass().loadBrands(requireContext(), view.brands_scrollview as ViewGroup, view.brands_fragment_recyclerview,  view.brands_fragment_recyclerview as RecyclerView)
        }else{
            val recyclerview = view.brands_fragment_recyclerview as RecyclerView
            recyclerview.adapter = BrandsAdapter(requireContext(), Constants.brandsList)
        }

    }

}