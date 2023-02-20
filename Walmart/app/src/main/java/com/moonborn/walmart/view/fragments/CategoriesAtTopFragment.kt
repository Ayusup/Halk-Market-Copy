package com.moonborn.walmart.view.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.moonborn.walmart.view.adapters.CategoriesAtTopAdapter
import com.moonborn.walmart.view.activities.BaseActivity
import com.moonborn.walmart.Constants
import com.moonborn.walmart.R
import com.moonborn.walmart.firebase.FirestoreClass
import kotlinx.android.synthetic.main.fragment_categories_at_top.view.*


class CategoriesAtTopFragment : Fragment() {

    companion object{
        var selectedCategory = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val rootView = inflater.inflate(R.layout.fragment_categories_at_top, container, false)


        BaseActivity.webview(rootView.categories_at_top_content_layout as ViewGroup)



        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if(Constants.categories.isEmpty()){
            FirestoreClass().loadCategories(view.categories_recyclerview_at_top_holder as ViewGroup,
                view.categories_recyclerview_at_top,
                view.categories_recyclerview_at_top as RecyclerView, requireContext(), this)

        }
        val categoriesRecyclerView = view.categories_recyclerview_at_top as RecyclerView
        val CategoriesInTopAdapter = CategoriesAtTopAdapter(requireContext(), Constants.categories, view.id, view as ViewGroup)
        categoriesRecyclerView.adapter = CategoriesInTopAdapter



        Log.i("Focusedddd", "${Constants.categories}")
        view.categories_at_top_btn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

    }

}