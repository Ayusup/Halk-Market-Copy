package com.moonborn.walmart.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.moonborn.walmart.*
import com.moonborn.walmart.application.WalmartApplication
import com.moonborn.walmart.view.fragments.CategroiesFragment
import com.moonborn.walmart.model.entities.Product
import com.moonborn.walmart.view.activities.MainActivity
import com.moonborn.walmart.viewmodel.ProductViewModel
import com.moonborn.walmart.viewmodel.ProductViewModelFactory
import com.moonborn.walmart.viewmodel.WishlistViewModel
import com.moonborn.walmart.viewmodel.WishlistViewModelFactory
//import com.moonborn.walmart.R
import kotlinx.android.synthetic.main.cardview_layout.view.*

class ProductsByCategoryOrBrandAdapter(val fragment: Fragment, val items: MutableList<Product>, val container: Int) :
    RecyclerView.Adapter<ProductsByCategoryOrBrandAdapter.ViewHolder>() {

    val products_by_categories = Constants.sort_by_category()
    val w_category = CategroiesFragment.selected_category
    val mproductViewModel : ProductViewModel by fragment.requireActivity().viewModels {
        ProductViewModelFactory((fragment.requireActivity().application as WalmartApplication).productsRepository)
    }

    private val mWishlistViewModel : WishlistViewModel by fragment.requireActivity().viewModels {
        WishlistViewModelFactory((fragment.requireActivity().application as WalmartApplication).wishlistRepository)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val product = view.product
        val productImage = view.product_img
        val productName = view.product_name
        val productPrice = view.product_price
        val productLike = view.product_like
        val productAddToPlanned = view.add_to_planned
        val productAddToCart = view.add_to_cart
        val productAddToCartLayout = view.add_to_cart_layout
        val productAddAndTakeLayout = view.add_and_take_layout
        val productAdd = view.add
        val productTake = view.take
        val productPieces = view.pieces
        val productPiecesPrice = view.pieces_price



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(fragment.requireContext()).inflate(
                R.layout.cardview_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        item.attachedViews.add(holder)

        Log.i("productCategoryyyy", "${item.name}")

//        holder.productImage.setImageResource(item.getImage())
//        holder.productName.text = item.getName()
//        holder.productPrice.text = item.getPrice().toString() + " TMT"

        MainActivity.setproductDataInUI(item, holder.productImage, holder.productName, holder.productPrice, holder.productLike)



        MainActivity.update_similar_views_add_to_cart(item)


        holder.product.setOnClickListener {
            if (fragment.requireActivity() is MainActivity){
                (fragment.requireActivity() as MainActivity).openProductlayout(item, container, CategroiesFragment.categoriesFragTag)
            }
        }

//        MainActivity.update_similar_views_like_btn(item, mWishlistViewModel)

        holder.productLike.setOnClickListener{
            if(item.id !in MainActivity.liked_items_with_multiple_parents){
                holder.productLike.startAnimation(
                    AnimationUtils.loadAnimation(fragment.context,
                    R.anim.like_btn_animation
                ))

//                holder.productLike.visibility = View.VISIBLE
                val r = Runnable {
                    MainActivity.liked_items_with_multiple_parents.add(item.id)
                    MainActivity.update_similar_views_like_btn(item)
                    Constants.itemsInWishlist.add(item)
                }
                android.os.Handler().postDelayed(r, 70)
            } else {
                holder.productLike.startAnimation(
                    AnimationUtils.loadAnimation(fragment.requireContext(),
                    R.anim.like_btn_dislike_animation
                ))
                val r = Runnable {
                    MainActivity.liked_items_with_multiple_parents.remove(item.id)
                    MainActivity.update_similar_views_like_btn(item)
                    Constants.itemsInWishlist.remove(item)
                }
                android.os.Handler().postDelayed(r, 70)
            }
        }

        holder.productAddToPlanned.setOnClickListener {
            val dialog = AddToPlannedDialog(fragment.requireContext())
            dialog.show()
            MainActivity.itemForPlannedList = item
        }

        holder.productAddToCart.setOnClickListener{

            MainActivity.items_added_to_cart.put(item.id, 1)
            Constants.itemsInCart.add(item)
            MainActivity.update_similar_views_add_to_cart(
                item
            )
            Log.i("ItemsInCart2", "${Constants.itemsInCart}")


        }
        holder.productAdd.setOnClickListener{
            var item_num = 1
            if(item.id in MainActivity.items_added_to_cart){
                item_num = MainActivity.items_added_to_cart.get(item.id)!!
                item_num++
                MainActivity.items_added_to_cart[item.id] = item_num
                MainActivity.update_similar_views_add_and_take(
                    item
                )
            }
        }
        holder.productTake.setOnClickListener{
            var item_num = 1
            if(item.id in MainActivity.items_added_to_cart){
                if(MainActivity.items_added_to_cart[item.id]!! > 1){
                    item_num = MainActivity.items_added_to_cart.get(item.id)!!
                    item_num--
                    MainActivity.items_added_to_cart[item.id] = item_num
                    MainActivity.update_similar_views_add_and_take(
                        item
                    )
                } else {
                    MainActivity.items_added_to_cart.remove(item.id)
                    if(item in Constants.itemsInCart){
                        Constants.itemsInCart.remove(item)
                    }
                    MainActivity.update_similar_views_add_and_take(
                        item
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int {
//        return products_by_categories[w_category]!!.size
        return items.size
    }


}