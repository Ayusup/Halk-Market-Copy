package com.moonborn.walmart.view.adapters

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moonborn.walmart.Constants
import com.moonborn.walmart.view.activities.MainActivity
import com.moonborn.walmart.R
import com.moonborn.walmart.application.WalmartApplication
import com.moonborn.walmart.model.entities.Product
import com.moonborn.walmart.view.fragments.ProductsInPlannedListFragment
import com.moonborn.walmart.viewmodel.PlannedListViewModel
import com.moonborn.walmart.viewmodel.PlannedListViewModelFactory
import kotlinx.android.synthetic.main.item_in_planned_list_cardview.view.*
import java.text.DecimalFormat

class ProductsInPlannedListAdapter(val fragment: ProductsInPlannedListFragment):
    RecyclerView.Adapter<ProductsInPlannedListAdapter.ViewHolder>(){

    var itemsList = mutableListOf<Product>()

    private val mPlannedListViewModel: PlannedListViewModel by fragment.requireActivity().viewModels {
        PlannedListViewModelFactory((fragment.requireActivity().application as WalmartApplication).plannedListsRepository)
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


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productInPlannedList: RelativeLayout = view.product_in_planned_list
        val productForCart: ImageView = view.product_for_cart
        val productImage: ImageView = view.product_Image_planned_list
        val productName: TextView = view.product_name_planned_list
        val productInStock: TextView = view.product_in_stock_planned_list
        val productPrice: TextView = view.product_price_planned_list
        val productDelete: RelativeLayout = view.product_delete_planned_list
        val productPieces: TextView = view.product_pieces_planned_list
        val productAddInPlannedList: ImageView = view.product_add_planned_list
        val productTakeInPlannedList: ImageView = view.product_take_planned_list
        val priceForPiece: TextView = view.price_for_piece_planned_list_txt
        val checkMask: View = view.check_mask
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        MainActivity.itms = differ.currentList as MutableList<Product>

        return ViewHolder(
            LayoutInflater.from(fragment.requireActivity()).inflate(
                R.layout.item_in_planned_list_cardview,
                parent,
                false
            )
        )
    }

    val itemsToCart: MutableMap<String, Int> = mutableMapOf()

    val dec = DecimalFormat("#,###.00")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = differ.currentList[position]
        var items_num: Int = 1
//        holder.productImage.setImageResource(item.getImage())
//        holder.productName.text = item.getName()
//        holder.produtPrice.text = item.getPrice().toString()

        MainActivity.setproductDataInUI(item, holder.productImage, holder.productName, holder.productPrice, null)


        holder.productInStock.text = fragment.requireContext().resources.getString(R.string.in_stock_text)
        holder.priceForPiece.text = fragment.requireContext().resources.getString(R.string.price_for_piece)
        itemsToCart.put(item.id, 1)

        holder.productAddInPlannedList.setOnClickListener {
            items_num++
            itemsToCart[item.id] = items_num
            holder.productPieces.text = items_num.toString() + " " +fragment.requireContext().resources.getString(R.string.pieces)
            if (holder.productForCart.isSelected == true){
                updateItemInCart(holder.productForCart, item, items_num, true, false)
            }
        }

        holder.productTakeInPlannedList.setOnClickListener {
            if(items_num > 1) {
                items_num--
                itemsToCart[item.id] = items_num
                holder.productPieces.text = items_num.toString() + " " +fragment.requireContext().resources.getString(R.string.pieces)

                if (holder.productForCart.isSelected == true){
                    updateItemInCart(holder.productForCart, item, items_num,  false,true)

                }
            }
        }

        holder.productForCart.setOnClickListener {
            if(holder.productForCart.isSelected == false){
                holder.productForCart.isSelected = true
                updateItemInCart(holder.productForCart, item, items_num, false, false)
//                MainActivity.update_similar_views_add_to_cart(item)
//                MainActivity.update_similar_views_add_to_cart(item)
                holder.checkMask.startAnimation(
                    AnimationUtils.loadAnimation(fragment.requireContext(),
                    R.anim.check_mask_gone
                ))
                val r = Runnable{ holder.checkMask.visibility = View.GONE }
                Handler().postDelayed(r, 174)
            } else {

                holder.productForCart.isSelected = false
                updateItemInCart(holder.productForCart, item, items_num, false, false)
//                MainActivity.update_similar_views_add_to_cart(item)

                holder.checkMask.startAnimation(
                    AnimationUtils.loadAnimation(fragment.requireContext(),
                    R.anim.check_mask_appear
                ))

                val r = Runnable{ holder.checkMask.visibility = View.VISIBLE }
                Handler().postDelayed(r, 174)
            }
        }
        holder.productDelete.setOnClickListener {
            holder.productInPlannedList.visibility = View.GONE
//            Constants.listOfPlannedLists!![PlannedListAdapter.selectedPlannedList].getItemsInList().remove(item)
            ProductsInPlannedListFragment.selectedPlannedList!!.ItemsInList.remove(item.id)
            mPlannedListViewModel.update(ProductsInPlannedListFragment.selectedPlannedList!!)
        }
    }

    fun updateItemInCart(view: View, item: Product, items_num: Int, add: Boolean, take: Boolean){
        var itemNumInCart: Int = 0
        Log.i("itemsAddedToCarto", item.id)
        if (view.isSelected == true){
            if(item.id in MainActivity.items_added_to_cart) {
                Log.i("itemsAddedToCarto", MainActivity.items_added_to_cart.get(item.id)!!.plus(10).toString())
                itemNumInCart = MainActivity.items_added_to_cart.get(item.id)!!
                if(add){
                    itemNumInCart++
                }else if(take){
                    itemNumInCart--
                }else{
                    itemNumInCart += items_num
                }

                MainActivity.items_added_to_cart[item.id] = itemNumInCart
            }else{

                MainActivity.items_added_to_cart.put(item.id, items_num)
                Constants.itemsInCart.add(item)

            }
            MainActivity.update_similar_views_add_to_cart(item)
        }else{
            if(item.id in MainActivity.items_added_to_cart.keys){
                if(MainActivity.items_added_to_cart.get(item.id)!! > items_num){
                    itemNumInCart = MainActivity.items_added_to_cart.get(item.id)!!
                    itemNumInCart -= items_num
                    MainActivity.items_added_to_cart[item.id] = itemNumInCart
                }else{
                    MainActivity.items_added_to_cart.remove(item.id)
                    Log.i("jh", MainActivity.items_added_to_cart.toString())
                    Log.i("jho", item.id)
                    for (i in Constants.itemsInCart){
                        if(i.id == item.id){
                            Constants.itemsInCart.remove(i)
                        }
                    }
                }
            }
            MainActivity.update_similar_views_add_to_cart(item)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
//        if (Constants.listOfPlannedLists[MainActivity.selectedPlannedList].getItemsInList().size > 0){
//            Constants.listOfPlannedLists[MainActivity.selectedPlannedList].getItemsInList().size
//        } else {
//            0
//        }


    }
}