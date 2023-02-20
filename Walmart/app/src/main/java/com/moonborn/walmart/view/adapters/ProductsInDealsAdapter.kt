package com.moonborn.walmart.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moonborn.walmart.AddToPlannedDialog
import com.moonborn.walmart.Constants
import com.moonborn.walmart.R
import com.moonborn.walmart.application.WalmartApplication
import com.moonborn.walmart.databinding.CardviewLayoutBinding
import com.moonborn.walmart.model.entities.Product
import com.moonborn.walmart.view.activities.MainActivity
import com.moonborn.walmart.view.fragments.MainFragment
import com.moonborn.walmart.viewmodel.ProductViewModel
import com.moonborn.walmart.viewmodel.ProductViewModelFactory
import com.moonborn.walmart.viewmodel.WishlistViewModel
import com.moonborn.walmart.viewmodel.WishlistViewModelFactory
import kotlinx.android.synthetic.main.cardview_layout.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProductsInDealsAdapter (private val fragment: Fragment, val container: Int) :
    RecyclerView.Adapter<ProductsInDealsAdapter.ViewHolder>() {

//    private var items: List<Product> = listOf()
    val mProductViewModel : ProductViewModel by fragment.requireActivity().viewModels {
        ProductViewModelFactory((fragment.requireActivity().application as WalmartApplication).productsRepository)
    }

    private val mWishlistViewModel : WishlistViewModel by fragment.requireActivity().viewModels {
        WishlistViewModelFactory((fragment.requireActivity().application as WalmartApplication).wishlistRepository)
    }

    companion object{
        var new_products_items = mutableListOf<Product>()
        fun updateList(){
            
        }
    }
//    val dbHandler = fragment.context?.let { DatabaseHandler(it) }

//    fun setFilteredData(list: MutableList<Product>){
//        this.items = list as ArrayList<Product>
//        this.notifyDataSetChanged()
//
//    }


    private val differCallback = object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)



    class ViewHolder(view: CardviewLayoutBinding) : RecyclerView.ViewHolder(view.root) {
        val product = view.product
        val productImage = product.product_img
        val productName = product.product_name
        val productPrice = product.product_price
        val productLike = product.product_like
        val productAddToPlanned = product.add_to_planned
        val productAddToCart = product.add_to_cart
        val productAddToCartLayout = product.add_to_cart_layout
        val productAddAndTakeLayout = product.add_and_take_layout
        val productAdd = product.add
        val productTake = product.take
        val productPieces = product.pieces
        val productPiecesPrice = product.pieces_price

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: CardviewLayoutBinding = CardviewLayoutBinding.inflate(LayoutInflater.from(fragment.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
//        Log.i("ProductItem", item.name)
        if(holder !in item.attachedViews){ item.attachedViews.add(holder)}

//        Log.i("ProductsByDealsAdapter", "${item.attachedViews}")



        MainActivity.setproductDataInUI(item, holder.productImage, holder.productName, holder.productPrice, holder.productLike)

        holder.product.setOnClickListener {
            if (fragment.context is MainActivity){
                (fragment.context as MainActivity).openProductlayout(item, container, MainFragment.mainFragTag)
            }
        }


        MainActivity.update_similar_views_add_to_cart(item)
        if(item.id in MainActivity.items_added_to_cart){
            MainActivity.update_similar_views_add_and_take(item)
        }

//        Log.i("openenenne", "${items}")


//        MainActivity.update_similar_views_like_btn(item, mWishlistViewModel)
        holder.productLike.setOnClickListener{
            if(item.id !in MainActivity.liked_items_with_multiple_parents){
                holder.productLike.startAnimation(AnimationUtils.loadAnimation(fragment.context,
                    R.anim.like_btn_animation
                ))
                val r = Runnable {
                    MainActivity.liked_items_with_multiple_parents.add(item.id)
                    MainActivity.update_similar_views_like_btn(item)
                    Constants.itemsInWishlist.add(item)
                }
                android.os.Handler().postDelayed(r, 70)
            } else {
                holder.productLike.startAnimation(
                    AnimationUtils.loadAnimation(fragment.context,
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
            val dialog = fragment.context?.let { it -> AddToPlannedDialog(it) }
            dialog!!.show()
            MainActivity.itemForPlannedList = item
        }

        holder.productAddToCart.setOnClickListener{
            MainActivity.items_added_to_cart.put(item.id, 1)
            Constants.itemsInCart.add(item)
            MainActivity.update_similar_views_add_to_cart(item)
            Log.i("ItemsInCart2", "${MainActivity.items_added_to_cart}")


        }
        holder.productAdd.setOnClickListener{
            var item_num = 1
            if(item.id in MainActivity.items_added_to_cart){
                item_num = MainActivity.items_added_to_cart.get(item.id)!!
                item_num++
                MainActivity.items_added_to_cart[item.id] = item_num
                MainActivity.update_similar_views_add_and_take(item)
            }
        }
        holder.productTake.setOnClickListener{
            var item_num = 1
            if(item.id in MainActivity.items_added_to_cart){
                if(MainActivity.items_added_to_cart[item.id]!! > 1){
                    item_num = MainActivity.items_added_to_cart.get(item.id)!!
                    item_num--
                    MainActivity.items_added_to_cart[item.id] = item_num
                    MainActivity.update_similar_views_add_and_take(item)

                } else {
                    MainActivity.items_added_to_cart.remove(item.id)
                    if(item in Constants.itemsInCart){
                        Constants.itemsInCart.remove(item)
                    }
                    MainActivity.update_similar_views_add_and_take(item)

                }
            }
        }
    }

    override fun getItemCount(): Int {
//        val selected_deal: String? = MainActivity.deals_see_all_for_recyclerview
//        new_products_items = prodcts_by_deals["new_products"]!!
//        return  prodcts_by_deals["new_products"]!!.size
        return differ.currentList.size
    }

//    fun productList(list : List<Product>){
//        GlobalScope.launch(Dispatchers.Main) {
//            withContext(Dispatchers.IO) {
//                items = list
//            }
//
//
//            notifyDataSetChanged()
//        }
//    }


}
