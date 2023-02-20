package com.moonborn.walmart.view.adapters

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moonborn.walmart.Constants
import com.moonborn.walmart.view.activities.MainActivity
import com.moonborn.walmart.Models.PlannedListModel
import com.moonborn.walmart.R
import com.moonborn.walmart.model.entities.PlannedListsModel
import kotlinx.android.synthetic.main.planned_list_model_dialogview.view.*

class PlannedListInDialogAdapter(val context: Context) :
    RecyclerView.Adapter<PlannedListInDialogAdapter.ViewHolder>() {



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val addPlannedListDialogView = view.add_to_planned_list_dialogview
        val plannedListNameDialogview = view.planned_list_name_dialogview
        val checkMark = view.check
        val checkMaskForAnim = view.check_mask

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.planned_list_model_dialogview,
                parent,
                false
            )
        )

    }

    private val differCallback = object : DiffUtil.ItemCallback<PlannedListsModel>(){
        override fun areItemsTheSame(oldItem: PlannedListsModel, newItem: PlannedListsModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PlannedListsModel, newItem: PlannedListsModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = differ.currentList[position]
        holder.plannedListNameDialogview.text = item.ListName

        holder.addPlannedListDialogView.setOnClickListener{
            if(holder.addPlannedListDialogView.isSelected == false){
                holder.addPlannedListDialogView.isSelected = true
                holder.checkMaskForAnim.startAnimation(AnimationUtils.loadAnimation(context,
                    R.anim.check_mask_gone
                ))
                val r = Runnable{ holder.checkMaskForAnim.visibility = View.GONE }
                Handler().postDelayed(r, 174)

                MainActivity.selectedPlannedLists.add(item)

            } else {
                holder.checkMaskForAnim.startAnimation(AnimationUtils.loadAnimation(context,
                    R.anim.check_mask_appear
                ))

                val r = Runnable{ holder.checkMaskForAnim.visibility = View.VISIBLE }
                Handler().postDelayed(r, 174)
                holder.addPlannedListDialogView.isSelected = false
                MainActivity.selectedPlannedLists.remove(item)
//                if (item in Constants.listOfPlannedLists){
//                    Constants.listOfPlannedLists[item.getListId()].RemoveItemFromList(MainActivity.itemForPlannedList!!)
//                }
            }

        }

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return differ.currentList.size

    }

}