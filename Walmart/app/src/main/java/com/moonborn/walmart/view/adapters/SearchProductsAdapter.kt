//package com.moonborn.walmart.view.adapters
//
//import android.content.Context
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.AsyncListDiffer
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.moonborn.walmart.R
//import com.moonborn.walmart.databases.DatabaseHandler
//import com.moonborn.walmart.model.entities.Product
//import com.moonborn.walmart.view.activities.MainActivity
//import kotlinx.android.synthetic.main.cardview_layout.view.*
//
//class SearchProductsAdapter(val context: Context) :
//RecyclerView.Adapter<SearchProductsAdapter.ViewHolder>() {
//
//    val dbHandler = DatabaseHandler(context)
//
//    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val productImage = view.product_img
//        val productName = view.product_name
//        val productPrice = view.product_price
//        val productLike = view.product_like
//        val productAddToPlanned = view.add_to_planned
//        val productAddToCart = view.add_to_cart
//        val productAddToCartLayout = view.add_to_cart_layout
//        val productAddAndTakeLayout = view.add_and_take_layout
//        val productAdd = view.add
//        val productTake = view.take
//        val productPieces = view.pieces
//        val productPiecesPrice = view.pieces_price
//
//
//    }
//
//
//
//
////
////    fun setFilteredData(list: MutableList<Product>){
////        this.items = list as ArrayList<Product>
////        this.notifyDataSetChanged()
////        Log.i("itemdss", "${list}")
////        Log.i("itemds", "${items}")
////    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//
//        return ViewHolder(
//            LayoutInflater.from(context).inflate(
//                R.layout.cardview_layout,
//                parent,
//                false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: SearchProductsAdapter.ViewHolder, position: Int) {
//        val item = differ.currentList[position]
////        MainActivity.itemsWithMultipleParents[item.id]!!.add(holder)
//
//        Log.i("ProductsByDealsAdapter", "${items}")
//
////        holder.productImage.setImageResource(item.getImage())
////        holder.productName.text = item.getName()
////        holder.productPrice.text = item.getPrice().toString() + " TMT"
//
//        MainActivity.setproductDataInUI(item, holder.productImage, holder.productName, holder.productPrice, null)
//
////        holder.product.setOnClickListener {
////            if (fragment.context is MainActivity){
////                (fragment.context as MainActivity).openProductlayout(item, container, MainFragment.mainFragTag)
////            }
////        }
//
//
////        MainActivity.update_similar_views_add_to_cart(item, MainActivity.itemsWithMultipleParents)
////        if(item.getId() in MainActivity.items_added_to_cart){
////            MainActivity.update_similar_views_add_and_take(
////                item,
////                MainActivity.itemsWithMultipleParents
////            )
////        }
////
////        Log.i("productsFromDbInOnCreate", "${MainActivity.itemsWithMultipleParents}")
////        Log.i("openenenne", "${items}")
////
////        MainActivity.update_similar_views_add_to_cart(item, MainActivity.itemsWithMultipleParents)
////        MainActivity.update_similar_views_like_btn(item, MainActivity.itemsWithMultipleParents)
////
////        holder.productLike.setOnClickListener{
////            if(item.getId() !in MainActivity.liked_items_with_multiple_parents){
//////                holder.productLike.visibility = View.GONE
////                holder.productLike.startAnimation(AnimationUtils.loadAnimation(fragment.context,
////                    R.anim.like_btn_animation
////                ))
////
//////                holder.productLike.visibility = View.VISIBLE
////                val r = Runnable {
////                    MainActivity.liked_items_with_multiple_parents.add(item.getId())
////                    MainActivity.update_similar_views_like_btn(
////                        item,
////                        MainActivity.itemsWithMultipleParents
////                    )
////                    Constants.itemsInWishlist.add(item)
////                    dbHandler!!.addItemToWishlistDB(item)
////                }
////                android.os.Handler().postDelayed(r, 70)
////            } else {
////                holder.productLike.startAnimation(AnimationUtils.loadAnimation(fragment.context,
////                    R.anim.like_btn_dislike_animation
////                ))
////                val r = Runnable {
////                    MainActivity.liked_items_with_multiple_parents.remove(item.getId())
////                    MainActivity.update_similar_views_like_btn(
////                        item,
////                        MainActivity.itemsWithMultipleParents
////                    )
////                    Constants.itemsInWishlist.remove(item)
////                    dbHandler!!.deleteItemFromWishlistDB(item)
////                }
////                android.os.Handler().postDelayed(r, 70)
////            }
////        }
////
////        holder.productAddToPlanned.setOnClickListener {
////            val dialog = fragment.context?.let { it1 -> AddToPlannedDialog(it1) }
////            dialog!!.show()
////            MainActivity.itemForPlannedList = item
////        }
////
////        holder.productAddToCart.setOnClickListener{
////
////            MainActivity.items_added_to_cart.put(item.getId(), 1)
////            Constants.itemsInCart.add(item)
////            MainActivity.update_similar_views_add_to_cart(
////                item,
////                MainActivity.itemsWithMultipleParents
////            )
////            Log.i("ItemsInCart2", "${Constants.itemsInCart}")
////            //         TODO save the Datamodel to the database
////
////            dbHandler!!.addItemToDB(item)
////
////        }
////        holder.productAdd.setOnClickListener{
////            var item_num = 1
////            if(item.getId() in MainActivity.items_added_to_cart){
////                item_num = MainActivity.items_added_to_cart.get(item.getId())!!
////                item_num++
////                MainActivity.items_added_to_cart[item.getId()] = item_num
////                MainActivity.update_similar_views_add_and_take(
////                    item,
////                    MainActivity.itemsWithMultipleParents
////                )
////            }
////            dbHandler!!.updateItemInQuantityDB(item, item_num)
////        }
////        holder.productTake.setOnClickListener{
////            var item_num = 1
////            if(item.getId() in MainActivity.items_added_to_cart){
////                if(MainActivity.items_added_to_cart[item.getId()]!! > 1){
////                    item_num = MainActivity.items_added_to_cart.get(item.getId())!!
////                    item_num--
////                    MainActivity.items_added_to_cart[item.getId()] = item_num
////                    MainActivity.update_similar_views_add_and_take(
////                        item,
////                        MainActivity.itemsWithMultipleParents
////                    )
////                    dbHandler!!.updateItemInQuantityDB(item, item_num)
////                } else {
////                    MainActivity.items_added_to_cart.remove(item.getId())
////                    if(item in Constants.itemsInCart){
////                        Constants.itemsInCart.remove(item)
////                    }
////                    MainActivity.update_similar_views_add_and_take(
////                        item,
////                        MainActivity.itemsWithMultipleParents
////                    )
////                    dbHandler!!.deleteItemFromDB(item)
////                }
////            }
////        }
//    }
//
//
//
//
//
//    override fun getItemCount(): Int {
//        return differ.currentList.size
//    }
//}
//
////override fun onBindViewHolder(holder: SearchProductsAdapter.ViewHolder, position: Int) {
////    val item = items[position]
////    MainActivity.itemsWithMultipleParents[item.getId()]!!.add(holder)
////    holder.itemView.visibility = View.GONE
////
//////        holder.productImage.setImageResource(item.getImage())
//////        holder.productName.text = item.getName()
//////        holder.productPrice.text = item.getPrice().toString() + " TMT"
////
//////        MainActivity.setproductDataInUI(item, holder.productImage, holder.productName, holder.productPrice)
////
////
////    MainActivity.update_similar_views_add_to_cart(item, MainActivity.itemsWithMultipleParents)
////    if(item.getId() in MainActivity.items_added_to_cart){
////        MainActivity.update_similar_views_add_and_take(
////            item,
////            MainActivity.itemsWithMultipleParents
////        )
////    }
////
////
////    MainActivity.update_similar_views_like_btn(item, MainActivity.itemsWithMultipleParents)
////
////    var r = Runnable {
////        holder.itemView.animation = AnimationUtils.loadAnimation(context,
////            R.anim.search_layout_item_slide_up
////        )
////        holder.itemView.visibility = View.VISIBLE}
////    Handler().postDelayed(r, ((position*100).toLong()))
////
////    holder.productLike.setOnClickListener{
////        if(item.getId() !in MainActivity.liked_items_with_multiple_parents){
////            holder.productLike.startAnimation(AnimationUtils.loadAnimation(context,
////                R.anim.like_btn_animation
////            ))
////
//////                holder.productLike.visibility = View.VISIBLE
////            val r = Runnable {
////                MainActivity.liked_items_with_multiple_parents.add(item.getId())
////                MainActivity.update_similar_views_like_btn(
////                    item,
////                    MainActivity.itemsWithMultipleParents
////                )
////                Constants.itemsInWishlist.add(item)
////                dbHandler.addItemToWishlistDB(item)
////            }
////            android.os.Handler().postDelayed(r, 70)
////        } else {
////            holder.productLike.startAnimation(AnimationUtils.loadAnimation(context,
////                R.anim.like_btn_dislike_animation
////            ))
////            val r = Runnable {
////                MainActivity.liked_items_with_multiple_parents.remove(item.getId())
////                MainActivity.update_similar_views_like_btn(
////                    item,
////                    MainActivity.itemsWithMultipleParents
////                )
////                Constants.itemsInWishlist.remove(item)
////                dbHandler.deleteItemFromWishlistDB(item)
////            }
////            android.os.Handler().postDelayed(r, 70)
////        }
////    }
////
////    holder.productAddToPlanned.setOnClickListener {
////        val dialog = AddToPlannedDialog(context)
////        dialog.show()
////        MainActivity.itemForPlannedList = item
////    }
////
////    holder.productAddToCart.setOnClickListener{
////
////        MainActivity.items_added_to_cart.put(item.getId(), 1)
////        Constants.itemsInCart.add(item)
////        MainActivity.update_similar_views_add_to_cart(
////            item,
////            MainActivity.itemsWithMultipleParents
////        )
////        Log.i("ItemsInCart2", "${Constants.itemsInCart}")
////        //         TODO save the Datamodel to the database
////
////        dbHandler.addItemToDB(item)
////
////    }
////    holder.productAdd.setOnClickListener{
////        var item_num = 1
////        if(item.getId() in MainActivity.items_added_to_cart){
////            item_num = MainActivity.items_added_to_cart.get(item.getId())!!
////            item_num++
////            MainActivity.items_added_to_cart[item.getId()] = item_num
////            MainActivity.update_similar_views_add_and_take(
////                item,
////                MainActivity.itemsWithMultipleParents
////            )
////        }
////        dbHandler.updateItemInQuantityDB(item, item_num)
////    }
////    holder.productTake.setOnClickListener{
////        var item_num = 1
////        if(item.getId() in MainActivity.items_added_to_cart){
////            if(MainActivity.items_added_to_cart[item.getId()]!! > 1){
////                item_num = MainActivity.items_added_to_cart.get(item.getId())!!
////                item_num--
////                MainActivity.items_added_to_cart[item.getId()] = item_num
////                MainActivity.update_similar_views_add_and_take(
////                    item,
////                    MainActivity.itemsWithMultipleParents
////                )
////                dbHandler.updateItemInQuantityDB(item, item_num)
////            } else {
////                MainActivity.items_added_to_cart.remove(item.getId())
////                if(item in Constants.itemsInCart){
////                    Constants.itemsInCart.remove(item)
////                }
////                MainActivity.update_similar_views_add_and_take(
////                    item,
////                    MainActivity.itemsWithMultipleParents
////                )
////                dbHandler.deleteItemFromDB(item)
////            }
////        }
////    }
////}