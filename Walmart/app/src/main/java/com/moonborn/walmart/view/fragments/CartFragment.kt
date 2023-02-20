package com.moonborn.walmart.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.moonborn.walmart.view.adapters.ProductsInCartAdapter
import com.moonborn.walmart.Constants
import com.moonborn.walmart.view.activities.MainActivity
import com.moonborn.walmart.R
import com.moonborn.walmart.application.WalmartApplication
import com.moonborn.walmart.viewmodel.CartViewModel
import com.moonborn.walmart.viewmodel.CartViewModelFactory
import com.moonborn.walmart.viewmodel.ProductViewModel
import com.moonborn.walmart.viewmodel.ProductViewModelFactory
import kotlinx.android.synthetic.main.cart_layout.*
import kotlinx.android.synthetic.main.cart_layout_content.*
import kotlinx.android.synthetic.main.cart_layout_content.view.*
import kotlinx.android.synthetic.main.fragment_cart.view.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.fragment_wishlist.view.*
import java.text.DecimalFormat

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private val mProductViewModel : ProductViewModel by viewModels {
        ProductViewModelFactory((requireActivity().application as WalmartApplication).productsRepository)
    }

    companion object{
        //To check if I need to update my cart list(so not to send query each time)
        var cart_updated: Boolean = true
        var checkout_price: Double = 0.00
        fun updateCheckOutPrice(rootView: ViewGroup){
            val dec = DecimalFormat("#,###.00")
//            checkout_price = 0.00
//            for(i in MainActivity.items_added_to_cart){
//                checkout_price += Constants.getProductByID().get(i.key)!!.price * i.value
//            }

//            rootView!!.check_out_price_txt.text = rootView.resources.getString(R.string.checkout_price_text) + " " + dec.format(
//                checkout_price
//            ) + " TMT"

        }
    }

    var cartLayout: View? = null

    var productsInCartAdapter: ProductsInCartAdapter? = null

    private val mCartViewModel : CartViewModel by viewModels {
        CartViewModelFactory((requireActivity().application as WalmartApplication).cartRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment


        val rootView = inflater.inflate(R.layout.fragment_cart, container, false)
        MainActivity.currentFragment = 2
        cartLayout = rootView



        val ProductsInCartContent = LayoutInflater.from(requireContext()).inflate(R.layout.cart_layout_content, null)
        rootView.cart_layout_scrollview.addView(ProductsInCartContent)

        productsInCartAdapter = ProductsInCartAdapter(this,  rootView as ViewGroup)


        rootView.products_in_cart_recyclerview.adapter = productsInCartAdapter
        rootView.products_in_cart_recyclerview.layoutManager = LinearLayoutManager(requireContext())
        rootView.products_in_cart_recyclerview.itemAnimator = null


        updateCart(rootView)

        Log.i("itemsInCart", "${MainActivity.items_in_cart}")


        rootView!!.profile_btn_cart.setOnClickListener {
            Log.i("MotionLayout", "${(requireActivity() as MainActivity).findViewById<MotionLayout>(R.id.activity_main_layout)}")
            val motionLayout: MotionLayout = (requireActivity() as MainActivity).findViewById(R.id.activity_main_layout)
            motionLayout.transitionToEnd()
        }

        rootView.cart_layout_clear_all_btn.setOnClickListener {
            MainActivity.items_added_to_cart.clear()
            for (i in Constants.itemsInCart){
                MainActivity.update_similar_views_add_to_cart(i)
            }
            Constants.itemsInCart.clear()
            updateCart(rootView)

        }

        updateCheckOutPrice(rootView as ViewGroup)


        return rootView
    }




    override fun onResume() {
        Log.i("itemsAddedToCart", MainActivity.items_added_to_cart.toString())
        if(CartFragment.cart_updated){
            cartLayout?.let { updateCart(it) }
            cartLayout?.let { updateCheckOutPrice(it as ViewGroup) }
            Log.i("cartFragmentFocused", "true")
            cart_updated = false

        }

        super.onResume()
    }

    fun updateCart(rootView: View){
        if(MainActivity.items_added_to_cart.isNotEmpty()) {
            if(Constants.itemsInCart.isNotEmpty()) {
                productsInCartAdapter!!.differ.submitList(null)
                productsInCartAdapter!!.differ.submitList(Constants.itemsInCart)
                productsInCartAdapter!!.notifyItemRangeRemoved(0, Constants.itemsInCart.size)
            } else {
                Constants.itemsInCart.clear()
                for (i in MainActivity.items_added_to_cart) {
                    mProductViewModel.getProductById(i.key).observe(viewLifecycleOwner) { product ->
                        product?.let {
                            Constants.itemsInCart.add(product)
                            updateCart(rootView)
                        }
                    }
                }
            }
            rootView.empty_cart_layout.visibility = View.GONE
            rootView.amount_of_products_cart_layout.visibility = View.VISIBLE
        }else{
            rootView.empty_cart_layout.visibility = View.VISIBLE
            rootView.amount_of_products_cart_layout.visibility = View.GONE

        }
        rootView.number_of_products_in_cart.text =
            MainActivity.items_added_to_cart.size.toString() + " " + resources.getString(
                R.string.number_of_products_in_cart_text
            )

    }

}