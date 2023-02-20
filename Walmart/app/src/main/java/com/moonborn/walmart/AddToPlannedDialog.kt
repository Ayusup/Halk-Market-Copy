package com.moonborn.walmart

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.children
import com.moonborn.walmart.databinding.DialogAddToPlannedBinding
import com.moonborn.walmart.view.activities.CreatePlannedList
import com.moonborn.walmart.view.activities.MainActivity
import com.moonborn.walmart.view.adapters.PlannedListInDialogAdapter
import com.moonborn.walmart.view.fragments.ProductsInPlannedListFragment
import com.moonborn.walmart.viewmodel.PlannedListViewModel
import kotlinx.android.synthetic.main.dialog_add_to_planned.*
import kotlinx.android.synthetic.main.planned_list_in_dialogview_content.*


class AddToPlannedDialog(context: Context): Dialog(context) {

    val binding: DialogAddToPlannedBinding = DialogAddToPlannedBinding.inflate(layoutInflater)

    companion object{
        var selectedLists:MutableList<Int> = mutableListOf()
    }


    var mPlannedListViewModel: PlannedListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(binding.root)
        // All the rest of the code goes here
        MainActivity.selectedPlannedLists!!.clear()




        val PlannedListInDialogAdapter = PlannedListInDialogAdapter(context)


        var PlannedListInDialogContent = LayoutInflater.from(context).inflate(R.layout.planned_list_in_dialogview_content, null)


        if(Constants.listOfPlannedLists.size > 0){

            add_to_planned_list_dialogview_scrollview.addView(PlannedListInDialogContent)
            planned_list_in_dialogview_content_recyclerview.adapter = PlannedListInDialogAdapter

            PlannedListInDialogAdapter.differ.submitList(Constants.listOfPlannedLists)
        }

        var cretePlannedListView = create_planned_list_dialogview
        if(Constants.listOfPlannedLists.size >= 5){
            if(create_planned_list_dialogview in dialogview_plannedlists_parent.children){

                dialogview_plannedlists_parent.removeView(cretePlannedListView)
                create_planned_list_alternate_holder.addView(cretePlannedListView)
            }
        }else{
            if(create_planned_list_dialogview in create_planned_list_alternate_holder.children){
                create_planned_list_alternate_holder.removeView(cretePlannedListView)
                dialogview_plannedlists_parent.addView(cretePlannedListView)
            }
            planned_lists_in_dialog_scrollview.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        }


        create_planned_list_dialogview.setOnClickListener {
            add_to_planned_list_dialogview_scrollview.removeView(PlannedListInDialogContent)
            val intent = Intent(context, CreatePlannedList::class.java)
            context.startActivity(intent)
            intent.putExtra("dialog", "Dialog")
            dismiss()

        }



//        if(context is MainActivity) {
//            val viewModel: PlannedListViewModel by (binding.root.context as MainActivity).viewModels {
//                PlannedListViewModelFactory(((binding.root.context as MainActivity).application as WalmartApplication).plannedListsRepository)
//            }
//            mPlannedListViewModel = viewModel
//        }else if(context is CreatePlannedList){
//            val viewModel: PlannedListViewModel by (binding.root.context as CreatePlannedList).viewModels {
//                PlannedListViewModelFactory(((binding.root.context as CreatePlannedList).application as WalmartApplication).plannedListsRepository)
//            }
//            mPlannedListViewModel = viewModel
//        }

        add_btn.setOnClickListener{

            if(MainActivity.selectedPlannedLists != null && MainActivity.selectedPlannedLists.isNotEmpty()){
                for(i in MainActivity.selectedPlannedLists!!){
//                    for(j in Constants.listOfPlannedLists){
//                        if (j.id == i.id){
                            if(MainActivity.itemForPlannedList!!.id !in i.ItemsInList){
                                i.ItemsInList += MainActivity.itemForPlannedList!!.id
                                Log.i("i.ItemsInList", "${i.ListName}")
                                MainActivity.mPlannedListViewModel?.update(i)
                                CreatePlannedList.plannedListAdapter?.differ?.submitList(null)
                                CreatePlannedList.plannedListAdapter?.differ?.submitList(Constants.listOfPlannedLists)
                            }

//                        }

//                    }
                    if(i.id == ProductsInPlannedListFragment.selectedPlannedList?.id){
                        ProductsInPlannedListFragment.selectedPlannedListItems = i.ItemsInList as MutableList<String>
                    }

                }
                MainActivity.selectedPlannedLists.clear()
                dismiss()
            }



        }

        cancel_btn.setOnClickListener {
            dismiss()
        }

    }

}