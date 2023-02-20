package com.moonborn.walmart.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.moonborn.walmart.SOM
import com.moonborn.walmart.databinding.SliderItemsBinding

class SOMAdapter (val context: Context ,somList: MutableList<SOM>,
                  viewPager2: ViewPager2)
    :RecyclerView.Adapter<SOMAdapter.SOMViewHolder>() {

    private val somList: List<SOM>
    private val viewPager2: ViewPager2


    init {
        this.somList = somList
        this.viewPager2 = viewPager2
    }


    inner class SOMViewHolder(private val binding: SliderItemsBinding)
        :RecyclerView.ViewHolder(binding.root){
        fun bindItem(som: SOM){
            binding.iconIv.setImageResource(som.photo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SOMViewHolder {

        return SOMViewHolder(
            SliderItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SOMViewHolder, position: Int) {
        holder.bindItem(somList[position])

    }




    override fun getItemCount(): Int {
        return somList.size
    }

    private val runnable = Runnable {
        somList.addAll(somList)
        notifyDataSetChanged()
    }
}