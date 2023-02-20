package com.moonborn.walmart.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.constraintlayout.motion.widget.MotionLayout
import com.moonborn.walmart.view.adapters.PlannedListAdapter
import com.moonborn.walmart.Constants
import com.moonborn.walmart.view.activities.CreatePlannedList
import com.moonborn.walmart.view.activities.MainActivity
import com.moonborn.walmart.R
import com.moonborn.walmart.application.WalmartApplication
import com.moonborn.walmart.viewmodel.PlannedListViewModel
import com.moonborn.walmart.viewmodel.PlannedListViewModelFactory
import kotlinx.android.synthetic.main.fragment_cart.view.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.fragment_planned_lists.view.*
import kotlinx.android.synthetic.main.planned_list_layout_content.*
import kotlinx.android.synthetic.main.planned_list_layout_content.view.*
import kotlinx.android.synthetic.main.planned_lists_layout.*

/**
 * A simple [Fragment] subclass.
 * Use the [PlannedListsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlannedListsFragment : Fragment() {

    var rootView: View? = null
    var plannedListAdapter: PlannedListAdapter? = null

    companion object{
        var updatePlannedList: Boolean = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    var mplannedListViewModel: PlannedListViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_planned_lists, container, false)
        MainActivity.currentFragment = 4
        plannedListAdapter = PlannedListAdapter(this, rootView!! as ViewGroup)

        val mPlannedListViewModel: PlannedListViewModel by requireActivity().viewModels {
            PlannedListViewModelFactory((requireActivity().application as WalmartApplication).plannedListsRepository)
        }

        mplannedListViewModel = mPlannedListViewModel

        val PlannedListContent = LayoutInflater.from(requireContext()).inflate(R.layout.planned_list_layout_content, null)
        rootView!!.planned_list_scrollview.addView(PlannedListContent)
        rootView!!.planned_list_recyclerview.adapter = plannedListAdapter



        rootView!!.profile_btn_planned_list.setOnClickListener {
            Log.i("MotionLayout", "${(requireActivity() as MainActivity).findViewById<MotionLayout>(R.id.activity_main_layout)}")
            val motionLayout: MotionLayout = (requireActivity() as MainActivity).findViewById(R.id.activity_main_layout)
            motionLayout.transitionToEnd()
        }

        rootView!!.add_list_btn_planned_list_layout.setOnClickListener {
            val intent = Intent(requireContext(), CreatePlannedList::class.java)
            intent.putExtra("from", "PlannedListFragment")
            requireContext().startActivity(intent)

        }

        rootView!!.add_btn_planned_lists_fragment.setOnClickListener {
            val intent = Intent(requireContext(), CreatePlannedList::class.java)
            intent.putExtra("from", "PlannedListFragment")
            requireContext().startActivity(intent)
        }

        updatePlannedLists(rootView!!, mPlannedListViewModel, plannedListAdapter!!)



        return rootView
    }

    fun updatePlannedLists(rootView: View, mPlannedListViewModel: PlannedListViewModel, plannedListAdapter: PlannedListAdapter){
//        if(updatePlannedList == true) {
            mPlannedListViewModel.allPlannedLists.observe(viewLifecycleOwner) { list ->
                list?.let {
                    Constants.listOfPlannedLists = list.toMutableList()
                    plannedListAdapter.differ.submitList(null)
                    plannedListAdapter.differ.submitList(Constants.listOfPlannedLists)
                    rootView.empty_planned_list_layout.visibility = View.GONE
                    rootView.amount_of_planned_lists.visibility = View.VISIBLE
                    if (list.size == 0) {
                        rootView.empty_planned_list_layout.visibility = View.VISIBLE
                        rootView.amount_of_planned_lists.visibility = View.GONE
                    }
                }

            }
//            updatePlannedList = false
//        }
    }

    override fun onResume() {
//        updatePlannedLists(rootView!!, mplannedListViewModel!!, plannedListAdapter!!)
        super.onResume()
    }

}