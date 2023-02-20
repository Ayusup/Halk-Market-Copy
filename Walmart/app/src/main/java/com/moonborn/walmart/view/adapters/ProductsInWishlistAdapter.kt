package com.moonborn.walmart.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moonborn.walmart.Constants
import com.moonborn.walmart.view.fragments.MainFragment
import com.moonborn.walmart.view.activities.MainActivity
import com.moonborn.walmart.R
import com.moonborn.walmart.application.WalmartApplication
import com.moonborn.walmart.model.entities.PlannedListsModel
import com.moonborn.walmart.model.entities.Product
import com.moonborn.walmart.viewmodel.ProductViewModel
import com.moonborn.walmart.viewmodel.ProductViewModelFactory
import com.moonborn.walmart.viewmodel.WishlistViewModel
import com.moonborn.walmart.viewmodel.WishlistViewModelFactory
import kotlinx.android.synthetic.main.item_in_cart_cardview.view.*
import kotlinx.android.synthetic.main.item_in_cart_cardview.view.price_for_piece_txt
import kotlinx.android.synthetic.main.item_in_wishlist_cardview.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DecimalFormat

class ProductsInWishlistAdapter(val fragment: Fragment, val container: Int) :
    RecyclerView.Adapter<ProductsInWishlistAdapter.ViewHolder>() {

//    private var items = listOf<Product>()

    val mproductViewModel : ProductViewModel by fragment.requireActivity().viewModels {
        ProductViewModelFactory((fragment.requireActivity().application as WalmartApplication).productsRepository)
    }

    private val mWishlistViewModel : WishlistViewModel by fragment.requireActivity().viewModels {
        WishlistViewModelFactory((fragment.requireActivity().application as WalmartApplication).wishlistRepository)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }


    }

    val differ = AsyncListDiffer(this, differCallback)


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

    val dec = DecimalFormat("#,###.00")
//    val dbHandler = DatabaseHandler(context)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val product = view.product_wishlist
        val productInCart = view.product_in_cart
        val productImage = view.product_Image_wishlist
        val productName = view.product_name_wishlist
        val productInStock = view.product_in_stock_wishlist
        val productPrice = view.product_price_wishlist
        val productDelete = view.product_delete_wishlist
        val productAddToCart = view.add_to_cart_wishlist
        val productAddAndTakeLayout = view.add_and_take_layout_wishlist
        val productPieces = view.product_pieces_wishlist
        val productAddInWishlist = view.product_add_wishlist
        val productTakeInWishlist = view.product_take_wislist
        val priceForPiece = view.price_for_piece_txt
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(fragment.requireContext()).inflate(
                R.layout.item_in_wishlist_cardview,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = differ.currentList[position]
        var items_num: Int = 0
//        holder.productImage.setImageResource(item.getImage())
//        holder.productName.text = item.getName()
//        holder.productPrice.text = item.getPrice().toString()

        MainActivity.setproductDataInUI(item, holder.productImage, holder.productName, holder.productPrice, null)

        holder.product.setOnClickListener {
            if (fragment.requireActivity() is MainActivity){
                (fragment.requireActivity() as MainActivity).openProductlayout(item, container, MainFragment.mainFragTag)
            }
        }

        holder.productInStock.text = fragment.requireContext().resources.getString(R.string.in_stock_text)
        holder.productDelete.text = fragment.requireContext().resources.getString(R.string.delete_btn_text)
        holder.priceForPiece.text = fragment.requireContext().resources.getString(R.string.price_for_piece)
        if(item.id in MainActivity.items_added_to_cart){
            holder.productAddToCart.visibility = View.GONE
            holder.productAddAndTakeLayout.visibility = View.VISIBLE
            holder.productPieces.text = MainActivity.items_added_to_cart.get(item.id).toString() + " " + fragment.requireContext().resources.getString(
                R.string.pieces
            )
        }
        holder.productAddToCart.setOnClickListener {
            MainActivity.items_added_to_cart.put(item.id, 1)
            Constants.itemsInCart.add(item)
            holder.productAddToCart.visibility = View.GONE
            holder.productAddAndTakeLayout.visibility = View.VISIBLE
            MainActivity.update_similar_views_add_to_cart(
                item
            )
//            val dbHandler = DatabaseHandler(context)
//            dbHandler.addItemToDB(item)

        }
        holder.productAddInWishlist.setOnClickListener {
            items_num = MainActivity.items_added_to_cart[item.id]!!
            items_num++
            MainActivity.items_added_to_cart[item.id] = items_num
            holder.productPieces.text = MainActivity.items_added_to_cart.get(item.id).toString() + " " + fragment.requireContext().resources.getString(
                R.string.pieces
            )
            MainActivity.update_similar_views_add_and_take(
                item
            )
        }
        holder.productTakeInWishlist.setOnClickListener {
            if(MainActivity.items_added_to_cart[item.id]!! > 1){
                items_num = MainActivity.items_added_to_cart.get(item.id)!!
                items_num--
                MainActivity.items_added_to_cart[item.id] = items_num
                holder.productPieces.text = MainActivity.items_added_to_cart.get(item.id).toString() + " " + fragment.requireContext().resources.getString(
                    R.string.pieces
                )
                MainActivity.update_similar_views_add_and_take(
                    item
                )

            } else {
                MainActivity.items_added_to_cart.remove(item.id)
                if(item in Constants.itemsInCart){
                    Constants.itemsInCart.remove(item)
                }
                holder.productAddAndTakeLayout.visibility = View.GONE
                holder.productAddToCart.visibility = View.VISIBLE
                MainActivity.update_similar_views_add_and_take(
                    item
                )
            }
        }

        holder.productDelete.setOnClickListener {
            MainActivity.liked_items_with_multiple_parents.remove(item.id)
            if(item in Constants.itemsInWishlist){
                Constants.itemsInWishlist.remove(item)
            }
            if(fragment.requireActivity() is MainActivity){
                (fragment.requireActivity() as MainActivity).remove_item_from_wishlist_layout(this)
            }
            MainActivity.update_similar_views_like_btn(item)

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}