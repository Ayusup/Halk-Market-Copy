package com.moonborn.walmart.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moonborn.walmart.Constants
import com.moonborn.walmart.view.fragments.CartFragment
import com.moonborn.walmart.view.fragments.MainFragment
import com.moonborn.walmart.view.activities.MainActivity
import com.moonborn.walmart.R
import com.moonborn.walmart.model.entities.Product
import kotlinx.android.synthetic.main.item_in_cart_cardview.view.*
import java.text.DecimalFormat

class ProductsInCartAdapter(val fragment: CartFragment, val rootView: ViewGroup) :
    RecyclerView.Adapter<ProductsInCartAdapter.ViewHolder>() {

    val dec = DecimalFormat("#,###.00")
//    val dbHandler = DatabaseHandler(context)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val product = view.product_cart
        val productInCart = view.product_in_cart
        val productImage = view.product_Image_cart
        val productName = view.product_name_cart
        val productInStock = view.product_in_stock_cart
        val produtPrice = view.product_price_cart
        val productDelete = view.product_delete_cart
        val productPieces = view.product_pieces_cart
        val productAddInCart = view.product_add_cart
        val productTakeInCart = view.product_take_cart
        val productTotalPrice = view.product_total_price_cart
        val productTotalAmount = view.total_amount_txt
        val priceForPiece = view.price_for_piece_txt
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(fragment.requireContext()).inflate(
                R.layout.item_in_cart_cardview,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = differ.currentList[position]
        var items_num: Int = 0
        Log.i("Items In cart from DB", "${differ.currentList}")
//        holder.productImage.setImageResource(item.getImage())
//        holder.productName.text = item.getName()
//        holder.produtPrice.text = item.getPrice().toString()

        MainActivity.setproductDataInUI(item, holder.productImage, holder.productName, holder.produtPrice, null)

        holder.product.setOnClickListener {
            if (fragment.requireContext() is MainActivity){
                (fragment.requireContext() as MainActivity).openProductlayout(item, rootView.id, MainFragment.mainFragTag)
            }
        }

        holder.productInStock.text = fragment.requireContext().resources.getString(R.string.in_stock_text)
        holder.productDelete.text = fragment.requireContext().resources.getString(R.string.delete_btn_text)
        holder.productTotalAmount.text = fragment.requireContext().resources.getString(R.string.total_price_text)
        holder.priceForPiece.text = fragment.requireContext().resources.getString(R.string.price_for_piece)
        if(item.id in MainActivity.items_added_to_cart){
            holder.productPieces.text = MainActivity.items_added_to_cart.get(item.id).toString() + " " + fragment.requireContext().resources.getString(
                R.string.pieces
            )
            holder.productTotalPrice.text =
                dec.format(MainActivity.items_added_to_cart.get(item.id)!! * item.price).toString() + " TMT"
        }
        holder.productAddInCart.setOnClickListener {
            items_num = MainActivity.items_added_to_cart[item.id]!!
            items_num++
            MainActivity.items_added_to_cart[item.id] = items_num
            holder.productPieces.text = MainActivity.items_added_to_cart.get(item.id).toString() + " " + fragment.requireContext().resources.getString(
                R.string.pieces
            )
            holder.productTotalPrice.text =
                dec.format(MainActivity.items_added_to_cart.get(item.id)!! * item.price).toString() + " TMT"
            MainActivity.update_similar_views_add_and_take(
                item
            )
//            dbHandler.updateItemInQuantityDB(item, items_num)
            CartFragment.updateCheckOutPrice(rootView as ViewGroup)
            CartFragment.cart_updated = false
        }
        holder.productTakeInCart.setOnClickListener {
            if(MainActivity.items_added_to_cart[item.id]!! > 1){
                items_num = MainActivity.items_added_to_cart.get(item.id)!!
                items_num--
                MainActivity.items_added_to_cart[item.id] = items_num
                holder.productPieces.text = MainActivity.items_added_to_cart.get(item.id).toString() + " " + fragment.requireContext().resources.getString(
                    R.string.pieces
                )
                holder.productTotalPrice.text =
                    dec.format(MainActivity.items_added_to_cart.get(item.id)!! * item.price).toString() + " TMT"
                MainActivity.update_similar_views_add_and_take(
                    item
                )
                Log.i("item.Views", item.attachedViews.toString())
//                dbHandler.updateItemInQuantityDB(item, items_num)
                CartFragment.updateCheckOutPrice(rootView)

            }
            else {
                MainActivity.items_added_to_cart.remove(item.id)
                if(item in Constants.itemsInCart){
                    Constants.itemsInCart.remove(item)
                }
                if(fragment.requireContext() is MainActivity){
                    (fragment.requireContext() as MainActivity).remove_item_from_cart_layout(this)
                }
                MainActivity.update_similar_views_add_and_take(
                    item
                )
                CartFragment.updateCheckOutPrice(rootView)

            }
            CartFragment.cart_updated = false
        }

        holder.productDelete.setOnClickListener {
            MainActivity.items_added_to_cart.remove(item.id)
            if(item in Constants.itemsInCart){
                Constants.itemsInCart.remove(item)
            }
            if(fragment.requireContext() is MainActivity){
                (fragment.requireContext() as MainActivity).remove_item_from_cart_layout(this)
            }
            MainActivity.update_similar_views_add_and_take(
                item
            )
//            dbHandler.deleteItemFromDB(item)

            CartFragment.updateCheckOutPrice(rootView)
            CartFragment.cart_updated = false
        }

//        holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.slide_left)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}