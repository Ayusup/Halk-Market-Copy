package com.moonborn.walmart.view.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.moonborn.walmart.model.entities.Product
import com.moonborn.walmart.view.fragments.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TabPageAdapter(val activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = fragmentsList.size

    var fragmentsList = listOf<Fragment>()
//
//    private val differCallback = object : DiffUtil.ItemCallback<Fragment>(){
//        override fun areItemsTheSame(oldItem: Fragment, newItem: Fragment): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: Fragment, newItem: Fragment): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//    val differ = AsyncListDiffer(this, differCallback)


    fun updateList(list: List<Fragment>){
        GlobalScope.launch(Dispatchers.Main) { //Your Main UI Thread

            withContext(Dispatchers.IO) {
                fragmentsList = list

            }
            notifyDataSetChanged()
        }
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentsList[position]
//        when(position){
//            0 -> {
//                MainFragment()
//            }
//            1 -> CategroiesFragment()
//            2 -> CartFragment()
//            3 -> WishlistFragment()
//            4 -> PlannedListsFragment()
//
//            else -> MainFragment()
//        }
    }

    private fun getReadableTabPosition(position: Int) = position + 1
}