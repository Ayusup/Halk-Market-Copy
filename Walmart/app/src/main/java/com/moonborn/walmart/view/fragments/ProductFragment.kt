package com.moonborn.walmart.view.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import com.moonborn.walmart.AddToPlannedDialog
import com.moonborn.walmart.Constants
import com.moonborn.walmart.view.activities.MainActivity
import com.moonborn.walmart.R
import com.moonborn.walmart.application.WalmartApplication
import com.moonborn.walmart.model.entities.Product
import com.moonborn.walmart.viewmodel.WishlistViewModel
import com.moonborn.walmart.viewmodel.WishlistViewModelFactory
import kotlinx.android.synthetic.main.fragment_product.view.*


class ProductFragment : Fragment() {







    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        val mWishlistViewModel : WishlistViewModel by requireActivity().viewModels {
            WishlistViewModelFactory((requireActivity().application as WalmartApplication).wishlistRepository)
        }


        val rootView = inflater.inflate(R.layout.fragment_product, container, false)
        val product = this.arguments?.getParcelable<Product>("product")
        Log.i("ProductlayoutId", "${product!!.image}")

        MainActivity.setproductDataInUI(product, rootView.product_image, rootView.product_name, rootView.product_price_product_layout, rootView.product_layout_like_btn)
        rootView.article.text = "Article: " + product.id


        update_cart_btn(rootView, product)
//        update_like(rootView, product)

        rootView.add_to_cart_product_Layout.setOnClickListener {
            MainActivity.items_added_to_cart.put(product.id, 1)
            Constants.itemsInCart.add(product)
            rootView.add_to_cart_product_Layout.visibility = View.GONE
            rootView.add_to_cart_product_Layout.animation = AnimationUtils.loadAnimation(context,
                R.anim.add_to_cart_disappear_anim
            )
            rootView.add_and_take_layout_product_layout.visibility = View.VISIBLE
            MainActivity.item_num_above_cart()
        }
        rootView.product_take_product_layout.setOnClickListener {
            var item_num = 1
            if(product.id in MainActivity.items_added_to_cart){
                if(MainActivity.items_added_to_cart[product.id]!! > 1){
                    item_num = MainActivity.items_added_to_cart.get(product.id)!!
                    item_num--
                    MainActivity.items_added_to_cart[product.id] = item_num
                    MainActivity.update_similar_views_add_and_take(
                        product
                    )
                    update_add_and_take(rootView, product)
                } else {
                    MainActivity.items_added_to_cart.remove(product.id)
                    if(product in Constants.itemsInCart){
                        Constants.itemsInCart.remove(product)
                    }
                    MainActivity.update_similar_views_add_and_take(
                        product
                    )
                    update_cart_btn(rootView, product)
                }
            }
        }

        rootView.product_layout_like_btn.setOnClickListener {
            if (product.id !in MainActivity.liked_items_with_multiple_parents){
                MainActivity.liked_items_with_multiple_parents.add(product.id)
                MainActivity.update_similar_views_like_btn(product)
                Constants.itemsInWishlist.add(product)
            } else {
                MainActivity.liked_items_with_multiple_parents.remove(product.id)
                MainActivity.update_similar_views_like_btn(product)
                Constants.itemsInWishlist.remove(product)
            }

            update_like(rootView, product)
        }

        rootView.product_layout_planned_btn.setOnClickListener {
            val dialog = AddToPlannedDialog(requireContext())
            dialog.show()
            MainActivity.itemForPlannedList = product
        }

        rootView.product_lay_back_btn.setOnClickListener {


            requireActivity().supportFragmentManager.popBackStack()

        }


        return rootView
    }


    fun update_like(rootView: View, product: Product){
        if(product.id in MainActivity.liked_items_with_multiple_parents){
            rootView.product_layout_like_btn.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.like_btn_animation
            ))
            Thread.sleep(70)
            val r = Runnable { rootView.product_layout_like_btn.isSelected = true}
            Handler().postDelayed(r, 70)
        } else {
            rootView.product_layout_like_btn.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.like_btn_dislike_animation
            ))

            val r = Runnable { rootView.product_layout_like_btn.isSelected = false}
            Handler().postDelayed(r, 70)
        }
    }

    fun update_cart_btn(rootView: View, product: Product){
        if(product.id in MainActivity.items_added_to_cart){
            rootView.add_to_cart_product_Layout.visibility = View.GONE
            rootView.add_and_take_layout_product_layout.visibility = View.VISIBLE
            update_add_and_take(rootView, product)
        } else {
            rootView.add_to_cart_product_Layout.visibility = View.VISIBLE
            rootView.add_and_take_layout_product_layout.visibility = View.GONE
        }
    }
    fun update_add_and_take(rootView: View, product: Product){
        rootView.product_pieces_product_layout.text = MainActivity.items_added_to_cart[product.id].toString()
    }

}