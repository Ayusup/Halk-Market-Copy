package com.moonborn.walmart.view.adapters

import android.content.Context
import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Parcelable
import android.view.*
import androidx.activity.viewModels
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moonborn.walmart.Constants
import com.moonborn.walmart.view.activities.CreatePlannedList
import com.moonborn.walmart.view.fragments.ProductsInPlannedListFragment
import com.moonborn.walmart.view.activities.MainActivity
import com.moonborn.walmart.R
import com.moonborn.walmart.application.WalmartApplication
import com.moonborn.walmart.model.entities.PlannedListsModel
import com.moonborn.walmart.view.fragments.PlannedListsFragment
import com.moonborn.walmart.viewmodel.PlannedListViewModel
import com.moonborn.walmart.viewmodel.PlannedListViewModelFactory
import kotlinx.android.synthetic.main.fragment_planned_lists.view.*
import kotlinx.android.synthetic.main.planned_list_model.view.*
import kotlinx.android.synthetic.main.planned_lists_layout.*


class PlannedListAdapter(val fragment: PlannedListsFragment,  val rootView: ViewGroup) :
    RecyclerView.Adapter<PlannedListAdapter.ViewHolder>() {

    var xDown: Float = 0F
    var yDown: Float = 0F
    var movedX: Float = 0F
//    val dbHandler = DatabaseHandler(context)

    private val differCallback = object : DiffUtil.ItemCallback<PlannedListsModel>(){
        override fun areItemsTheSame(oldItem: PlannedListsModel, newItem: PlannedListsModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PlannedListsModel, newItem: PlannedListsModel): Boolean {
            return oldItem == newItem
        }


    }

    val differ = AsyncListDiffer(this, differCallback)


    private val mPlannedListViewModel: PlannedListViewModel by fragment.requireActivity().viewModels {
        PlannedListViewModelFactory((fragment.requireActivity().application as WalmartApplication).plannedListsRepository)
    }


    fun selectPlannedList(context: Context, item:PlannedListsModel) {
        if(context is MainActivity) {
            ProductsInPlannedListFragment.selectedPlannedList = item
            if(item.ItemsInList != null){
                ProductsInPlannedListFragment.selectedPlannedListItems = item.ItemsInList
            } else {
                ProductsInPlannedListFragment.selectedPlannedListItems = mutableListOf()
            }

            context.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.planned_list_fragment, ProductsInPlannedListFragment(),
                    "planned_list_fragment"
                )
                .addToBackStack(null)
                .commit()
        }
    }



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val plannedListEdit = view.btn_edit_planned_list
        val plannedListDelete = view.btn_delete_planned_list
        val plannedListName = view.List_name
        val numOfProductsInside = view.number_of_products_inside
        val plannedListItemParentLayout = view.planned_list_item_parent_layout
        val animeMode: Int = 0
        val plannedList = view.planned_list
        val plannedListBg = view.planned_list_bg
        val plannedListDate = view.planned_list_date
        val plannedListMonth = view.planned_list_month
        val plannedListView = view.planned_list_view
        val llLeft = view.linearLayout1
        val LLRight = view.linearLayout2
        var leftPos: Float = view.linearLayout1.left.toFloat()
        var rightPos: Float = view.linearLayout2.right.toFloat()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                 LayoutInflater.from(fragment.requireActivity()).inflate(
                     R.layout.planned_list_model,
                     parent,
                     false
                 )
             )

    }

    val START_MODE = 0
    val END_MODE = 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = differ.currentList[position]



        var date = item.ListDate.split(" ")
        holder.plannedListDate.text = date.get(0)
        var month = date[1].split(" ")
        holder.plannedListMonth.text = month.get(0)

        holder.plannedListName.text = item.ListName
        holder.numOfProductsInside.text = item.ItemsInList.size.toString() + " " + fragment.requireContext().resources.getString(
            R.string.number_of_products_in_cart_text
        )


        if(item.ListImportance == "high"){
            holder.plannedList.setImageResource(R.drawable.high_importance_planned_list_layout)
//            = context.resources.getDrawable(R.drawable.high_importance_planned_list_layout)
            holder.plannedListBg.background = fragment.requireContext().resources.getDrawable(R.drawable.high_importance_planned_list_layout_bg)
        } else if(item.ListImportance == "normal"){
            holder.plannedList.setImageResource(R.drawable.normal_importance_planned_list_layout)
//            background = context.resources.getDrawable(R.drawable.normal_importance_planned_list_layout)
            holder.plannedListBg.background = fragment.requireContext().resources.getDrawable(R.drawable.normal_importance_planned_list_bg)
        } else if(item.ListImportance == "low"){
            holder.plannedList.setImageResource(R.drawable.low_importance_planned_list_layout)
//            background = context.resources.getDrawable(R.drawable.low_importance_planned_list_layout)
            holder.plannedListBg.background = fragment.requireContext().resources.getDrawable(R.drawable.low_importance_pllanned_list_layout_bg)
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            holder.plannedListEdit.setRenderEffect(RenderEffect.createBlurEffect(100F, 100F, Shader.TileMode.MIRROR))
            holder.plannedList.setRenderEffect(RenderEffect.createBlurEffect(100F, 100F, Shader.TileMode.MIRROR))
        }


        holder.plannedList.setOnClickListener {
            if (fragment.requireActivity() is MainActivity){
                fragment.requireActivity().amount_of_planned_lists.requestDisallowInterceptTouchEvent(true)
            }

        }

        holder.plannedListEdit.setOnClickListener {
            if(fragment.requireActivity() is MainActivity){
                val intent = Intent(fragment.requireActivity(), CreatePlannedList::class.java)
                intent.putExtra("id", "${item.id}")
                intent.putExtra("name", "${item.ListName}")
                intent.putExtra("importance", "${item.ListImportance}")
                intent.putExtra("date", "${item.ListDate}")
                intent.putExtra("item", item as Parcelable)
                CreatePlannedList.plannedListAdapter = this

                fragment.requireActivity().startActivity(intent)
            }

        }

        var plannedListToDelete: PlannedListsModel? = null

        holder.plannedListDelete.setOnClickListener {
//            for(i in Constants.listOfPlannedLists){
//                if(item.id == i.id){
//                    plannedListToDelete = i
//                    break
//                }
//            }
//            if(plannedListToDelete != null){
//                Constants.listOfPlannedLists.remove(item)
                if(differ.currentList.size == 0){
                    rootView.empty_planned_list_layout.visibility = View.VISIBLE
                    rootView.amount_of_planned_lists.visibility = View.GONE
                }


                mPlannedListViewModel.delete(item.id)

            mPlannedListViewModel.allPlannedLists.observe(fragment.viewLifecycleOwner){
                list -> list.let {
                    differ.submitList(null)
                    Constants.listOfPlannedLists = list as MutableList<PlannedListsModel>
                    differ.submitList(Constants.listOfPlannedLists)

                }
            }

//                dbHandler.deletePlannedListFromDB(item)


//            }

        }

        holder.plannedList.setOnTouchListener(
        object : View.OnTouchListener {
            var pressTime: Long = 0


            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
//                        if (context is MainActivity){
//                            context.amount_of_planned_lists.requestDisallowInterceptTouchEvent(true)
//                        }
                        xDown = event.x
                        yDown = event.x
                    pressTime = System.currentTimeMillis()}
                    MotionEvent.ACTION_MOVE -> {
                        holder.itemView.setVerticalScrollBarEnabled(false)
                        if(holder.plannedListView.x < holder.llLeft.x){
                            holder.plannedListView.x = holder.llLeft.x

                        }else if(holder.plannedListView.x > holder.LLRight.x - (holder.plannedListView.width - holder.LLRight.width)){
                            holder.plannedListView.x = holder.LLRight.x - (holder.plannedListView.width - holder.LLRight.width)
                        }else{


                            if(holder.plannedListView.x == holder.llLeft.x){
                                if(event.x > xDown){
                                    //How much the user
                                    movedX = event.x
                                    val distanceX: Float = movedX - xDown

                                    holder.plannedListView.x += distanceX
                                }
                            } else if(holder.plannedListView.x == holder.LLRight.x - (holder.plannedListView.width - holder.LLRight.width)){
                                if(event.x < xDown){
                                    //How much the user
                                    movedX = event.x
                                    val distanceX: Float = movedX - xDown

                                    holder.plannedListView.x += distanceX
                                }
                            } else {
                                if (event.x != xDown || event.y != yDown) {
                                    //How much the user
                                    movedX = event.x
                                    val distanceX: Float = movedX - xDown

                                    holder.plannedListView.x += distanceX
                                }

                            }

                        }

                    }
                    MotionEvent.ACTION_UP -> {
                        var releaseTime = System.currentTimeMillis() - pressTime
                        if(releaseTime <= 100){
                            //            notifyDataSetChanged()
                            //            if(Constants.listOfPlannedLists[item.getListId()].getItemsInList().isNotEmpty()){
                            //            }
                            selectPlannedList(fragment.requireActivity(), item)

                            holder.plannedList.performClick()

                        }else {
//                            if (xDown != event.x) {
                                if ((holder.plannedListView.x + (holder.plannedListView.width / 2)) <= (holder.plannedListItemParentLayout.width / 2).toFloat()) {
                                    if (holder.plannedListView.x != holder.llLeft.x) {
                                        holder.plannedListView.x = holder.llLeft.x
                                    }
                                } else {
                                    if (holder.plannedListView.x != holder.LLRight.x - (holder.plannedListView.width - holder.LLRight.width)) {
                                        holder.plannedListView.x =
                                            holder.LLRight.x - (holder.plannedListView.width - holder.LLRight.width)
                                    }
                                }
//                            }
                        }
                    }
//                    MotionEvent.ACTION_BUTTON_RELEASE -> {
////                        if(xDown != event.x){
//                            if((holder.plannedListView.x + (holder.plannedListView.width/2)) <= (holder.plannedListItemParentLayout.width/2).toFloat()){
//                                if(holder.plannedListView.x != holder.llLeft.x){
//                                    holder.plannedListView.x = holder.llLeft.x
//                                }
//                            } else {
//                                if(holder.plannedListView.x != holder.LLRight.x - (holder.plannedListView.width - holder.LLRight.width)){
//                                    holder.plannedListView.x = holder.LLRight.x - (holder.plannedListView.width - holder.LLRight.width)
//                                }
//                            }
////                        }
//
//                    }



                }

                return v?.onTouchEvent(event) ?: true
            }

        })







    }










    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}