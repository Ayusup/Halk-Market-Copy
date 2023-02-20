package com.moonborn.walmart.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moonborn.walmart.view.adapters.ProductsInWishlistAdapter
import com.moonborn.walmart.Constants
import com.moonborn.walmart.view.activities.MainActivity
import com.moonborn.walmart.R
import com.moonborn.walmart.application.WalmartApplication
import com.moonborn.walmart.databinding.FragmentWishlistBinding
import com.moonborn.walmart.model.entities.Product
import com.moonborn.walmart.viewmodel.ProductViewModel
import com.moonborn.walmart.viewmodel.ProductViewModelFactory
import com.moonborn.walmart.viewmodel.WishlistViewModel
import com.moonborn.walmart.viewmodel.WishlistViewModelFactory
import kotlinx.android.synthetic.main.fragment_cart.view.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.fragment_wishlist.view.*
import kotlinx.android.synthetic.main.wishlist_layout.*
import kotlinx.android.synthetic.main.wishlist_layout_content.*
import kotlinx.android.synthetic.main.wishlist_layout_content.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [WishlistFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WishlistFragment : Fragment() {

    var productsInWishlistAdapter: ProductsInWishlistAdapter? = null

    companion object{
        // To check if I need to update my wishlist(so not to send query each time)
        var wishlist_updated: Boolean = true
    }

    lateinit var mBinding: FragmentWishlistBinding

    var productViewModel: ProductViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    var fragment_view: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentWishlistBinding.inflate(inflater, container, false)

        val rootView = mBinding.root
        fragment_view = rootView

        MainActivity.currentFragment = 3
        val mProductViewModel : ProductViewModel by requireActivity().viewModels {
            ProductViewModelFactory((requireActivity().application as WalmartApplication).productsRepository)
        }
        productViewModel = mProductViewModel
        productsInWishlistAdapter = ProductsInWishlistAdapter(this, R.id.wishlist_fragment)

        rootView.profile_btn_wishlist.setOnClickListener {
            Log.i("MotionLayout", "${(requireActivity() as MainActivity).findViewById<MotionLayout>(R.id.activity_main_layout)}")
            val motionLayout: MotionLayout = (requireActivity() as MainActivity).findViewById(R.id.activity_main_layout)
            motionLayout.transitionToEnd()
        }

        rootView.wishlist_layout_clear_all_btn.setOnClickListener {
            MainActivity.liked_items_with_multiple_parents.clear()
            for (i in Constants.itemsInWishlist){
                MainActivity.update_similar_views_like_btn(i)
            }
            Constants.itemsInWishlist.clear()
            update_wislistLay(rootView, productViewModel!!)

        }


        val wishlistRecyclerView = rootView.wishlist_recyclerview as RecyclerView
        wishlistRecyclerView.adapter = productsInWishlistAdapter
        wishlistRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        wishlistRecyclerView.itemAnimator = null




        return rootView
    }


    override fun onResume() {
        if(wishlist_updated){
            fragment_view?.let { update_wislistLay(it, productViewModel!!) }
            wishlist_updated = false
        }
        super.onResume()

    }

    fun update_wislistLay(rootView: View, mProductViewModel: ProductViewModel){
        if(MainActivity.liked_items_with_multiple_parents.isNotEmpty()) {
            if(Constants.itemsInWishlist.isNotEmpty()) {
                productsInWishlistAdapter!!.differ.submitList(null)
                productsInWishlistAdapter!!.differ.submitList(Constants.itemsInWishlist)
                productsInWishlistAdapter!!.notifyItemRangeRemoved(
                    0,
                    Constants.itemsInWishlist.size
                )

            }else{
                for (i in MainActivity.liked_items_with_multiple_parents) {
                    mProductViewModel.getProductById(i).observe(viewLifecycleOwner) { product ->
                        product?.let {
                            Constants.itemsInWishlist.add(product)
                            update_wislistLay(rootView, mProductViewModel)

                        }
                    }
                }
            }
            rootView.empty_wishlist_layout.visibility = View.GONE
            rootView.amount_of_products_wishlist_layout.visibility = View.VISIBLE

        }else{

            productsInWishlistAdapter!!.differ.submitList(null)
            rootView.amount_of_products_wishlist_layout.visibility = View.GONE
            rootView.empty_wishlist_layout.visibility = View.VISIBLE
        }

        rootView.number_of_products_in_wishlist.text =
            MainActivity.liked_items_with_multiple_parents.size.toString() + " " + resources.getString(
                R.string.number_of_products_in_cart_text
            )

    }


}

