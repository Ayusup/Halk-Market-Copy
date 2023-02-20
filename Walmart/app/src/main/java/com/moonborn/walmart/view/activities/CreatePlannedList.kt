package com.moonborn.walmart.view.activities

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.moonborn.walmart.AddToPlannedDialog
import com.moonborn.walmart.Constants
import com.moonborn.walmart.R
import com.moonborn.walmart.application.WalmartApplication
import com.moonborn.walmart.model.entities.PlannedListsModel
import com.moonborn.walmart.model.entities.Product
import com.moonborn.walmart.view.adapters.PlannedListAdapter
import com.moonborn.walmart.viewmodel.PlannedListViewModel
import com.moonborn.walmart.viewmodel.PlannedListViewModelFactory
import kotlinx.android.synthetic.main.create_planned_list_layout.*
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*


class CreatePlannedList : AppCompatActivity(), View.OnClickListener {
    var planned_list_importance_lvl: String? = null
    var planned_list_importance_lvl_btn: View? = null
    var planned_list_importance_lvl_btn_txt: TextView? = null
    var PlannedList: PlannedListsModel? = null
    var editList: Boolean = false
    var PlannedListToEdit: PlannedListsModel? = null

    private var cal = Calendar.getInstance()
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    companion object{
        var plannedList = mutableMapOf<String, Any>()
        @SuppressLint("StaticFieldLeak")
        var plannedListAdapter: PlannedListAdapter? = null




    }


    private val mPlannedListViewModel : PlannedListViewModel by viewModels {
        PlannedListViewModelFactory((this.application as WalmartApplication).plannedListsRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_planned_list_layout)



        if(intent.getStringExtra("id") != null){
            for(i in Constants.listOfPlannedLists){
                if(i.id == intent.getStringExtra("id")){
                    PlannedListToEdit = i
                }
            }
            if(intent.getStringExtra("name") != null){
                planned_list_name.setText(intent.getStringExtra("name"))
            }
            if(intent.getStringExtra("importance") != null){
                val importance = intent.getStringExtra("importance")
                when(importance){
                    "high" -> selecteImportance("high", high_importance_btn, high_importance_btn_txt)
                    "normal" -> selecteImportance("normal", normal_importance_btn, normal_importance_btn_txt)
                    "low" -> selecteImportance("low", low_importance_btn, low_importance_btn_txt)
                }
            }
            if(intent.getStringExtra("date") != null){
                et_date.setText(intent.getStringExtra("date"))
            }
            if(intent.getParcelableExtra<PlannedListsModel>("item") != null){
                PlannedListToEdit = intent.getParcelableExtra<PlannedListsModel>("item")
            }
            create_planned_list_btn.text = "Save"
        }
        Log.i("Dateee", "${intent.getStringExtra("date")}")

        high_importance_btn.setOnClickListener {
            selecteImportance("high", high_importance_btn, high_importance_btn_txt)

//            if (planned_list_importance_lvl != "high") {
//                ExpandOrCollapse.expand(
//                    high_importance_btn,
//                    150,
//                    resources.getDimension(R.dimen.importance_level_selected_size).toInt()
//                )
//                if (planned_list_importance_lvl_btn != null && planned_list_importance_lvl_btn != high_importance_btn) {
//                    ExpandOrCollapse.collapse(
//                        planned_list_importance_lvl_btn!!,
//                        150,
//                        resources.getDimension(R.dimen.importance_level_unselected_size).toInt()
//                    )
//                    planned_list_importance_lvl_btn_txt?.setTextColor(resources.getColor(R.color.dark_gray))
//                }
//                planned_list_importance_lvl_btn = high_importance_btn
//                planned_list_importance_lvl = "high"
//                planned_list_importance_lvl_btn_txt = high_importance_btn_txt
//                planned_list_importance_lvl_btn_txt?.setTextColor(resources.getColor(R.color.black))
//            }
        }
        normal_importance_btn.setOnClickListener {
            selecteImportance("normal", normal_importance_btn, normal_importance_btn_txt)

//            if (planned_list_importance_lvl != "normal") {
//                ExpandOrCollapse.expand(
//                    normal_importance_btn,
//                    150,
//                    resources.getDimension(R.dimen.importance_level_selected_size).toInt()
//                )
//                if (planned_list_importance_lvl_btn != null) {
//                    ExpandOrCollapse.collapse(
//                        planned_list_importance_lvl_btn!!,
//                        150,
//                        resources.getDimension(R.dimen.importance_level_unselected_size).toInt()
//                    )
//                    planned_list_importance_lvl_btn_txt?.setTextColor(resources.getColor(R.color.dark_gray))
//                }
//                planned_list_importance_lvl_btn = normal_importance_btn
//                planned_list_importance_lvl = "normal"
//                planned_list_importance_lvl_btn_txt = normal_importance_btn_txt
//                planned_list_importance_lvl_btn_txt?.setTextColor(resources.getColor(R.color.black))
//            }


        }
        low_importance_btn.setOnClickListener {
            selecteImportance("low", low_importance_btn, low_importance_btn_txt)

//            if (planned_list_importance_lvl != "low") {
//                ExpandOrCollapse.expand(
//                    low_importance_btn,
//                    150,
//                    resources.getDimension(R.dimen.importance_level_selected_size).toInt()
//                )
//                if (planned_list_importance_lvl_btn != null) {
//                    ExpandOrCollapse.collapse(
//                        planned_list_importance_lvl_btn!!,
//                        150,
//                        resources.getDimension(R.dimen.importance_level_unselected_size).toInt()
//                    )
//                    planned_list_importance_lvl_btn_txt?.setTextColor(resources.getColor(R.color.dark_gray))
//                }
//                planned_list_importance_lvl_btn = low_importance_btn
//                planned_list_importance_lvl = "low"
//                planned_list_importance_lvl_btn_txt = low_importance_btn_txt
//                planned_list_importance_lvl_btn_txt?.setTextColor(resources.getColor(R.color.black))
//            }


        }

        cancel_create_planned_list_layout.setOnClickListener {
            this.finish()
        }

        create_planned_list_back_btn.setOnClickListener{
            this.finish()
        }


        dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            updateDateInView()
        }

        planned_list_name.setOnClickListener(this)
        et_date.setOnClickListener(this)

        create_planned_list_btn.setOnClickListener(this)


        planned_list_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(planned_list_name.text.length < 4){
                    val colorStateList: ColorStateList =
                        ColorStateList.valueOf(resources.getColor(R.color.red))
                    planned_list_name.backgroundTintList = colorStateList
                } else{
                    val colorStateList: ColorStateList =
                        ColorStateList.valueOf(resources.getColor(R.color.green_cart_color))
                    planned_list_name.backgroundTintList = colorStateList
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

    }

    fun selecteImportance(selected_importance: String, selected_importnce_btn: View, selected_importance_btn_txt: TextView){

        if (planned_list_importance_lvl != selected_importance) {
            ExpandOrCollapse.expand(
                selected_importnce_btn,
                150,
                resources.getDimension(R.dimen.importance_level_selected_size).toInt()
            )
            if (planned_list_importance_lvl_btn != null && planned_list_importance_lvl_btn != selected_importnce_btn) {
                ExpandOrCollapse.collapse(
                    planned_list_importance_lvl_btn!!,
                    150,
                    resources.getDimension(R.dimen.importance_level_unselected_size).toInt()
                )
                planned_list_importance_lvl_btn_txt?.setTextColor(resources.getColor(R.color.dark_gray))
            }
            planned_list_importance_lvl_btn = selected_importnce_btn
            planned_list_importance_lvl = selected_importance
            planned_list_importance_lvl_btn_txt = selected_importance_btn_txt
            planned_list_importance_lvl_btn_txt?.setTextColor(resources.getColor(R.color.black))
        }

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.et_date -> {

                val locale = resources.configuration.locale
                Locale.setDefault(locale)

                val dialog = DatePickerDialog(
                    this@CreatePlannedList, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                )
                dialog.datePicker.minDate = cal.getTimeInMillis()
                dialog.show()
            }
            R.id.planned_list_name -> {
                if(planned_list_name.text.length < 4){
                    val colorStateList: ColorStateList =
                        ColorStateList.valueOf(resources.getColor(R.color.red))
                    planned_list_name.backgroundTintList = colorStateList
                } else{
                    val colorStateList: ColorStateList =
                        ColorStateList.valueOf(resources.getColor(R.color.green_cart_color))
                    planned_list_name.backgroundTintList = colorStateList
                }
            }
            R.id.create_planned_list_btn -> {
                if(planned_list_name.text.length >= 4 && et_date.text.isNotEmpty() && planned_list_importance_lvl != null){
                    var id = ""
                    var PlannedListToEditId: Int? = null
                    if(PlannedListToEdit != null){
                        for(i in Constants.listOfPlannedLists){
                            if(i.id == PlannedListToEdit!!.id){
                                PlannedListToEdit = i
                                PlannedListToEditId = Constants.listOfPlannedLists.indexOf(i)
                                i.ListName = planned_list_name.text.toString()
                                i.ListImportance = planned_list_importance_lvl!!
                                i.ListDate = et_date.text.toString()
                                id = i.id
                            }
                        }
                        Log.i("PlannedListId", "${PlannedListToEdit!!.id}")
//                        dbHandler.updatePlannedListDb(id, PlannedListToEdit!!)
                        mPlannedListViewModel.update(PlannedListToEdit!!)
                        plannedListAdapter!!.differ.submitList(null)
                        plannedListAdapter!!.differ.submitList(Constants.listOfPlannedLists)
                        Constants.listOfPlannedLists[PlannedListToEditId!!] = PlannedListToEdit!!
                    } else {
                        var random = abs((0..999999999999).random())
                        var PlannedList = PlannedListsModel(
                            random.toString(),
                            planned_list_name.text.toString(),
                            planned_list_importance_lvl!!,
                            et_date.text.toString(),
                            mutableListOf<String>()
                        )

                        Constants.listOfPlannedLists.add(
                            PlannedList
                        )
                        var plannedListAttrs = mutableListOf<Any>()
                        plannedListAttrs.add(0, planned_list_name.text.toString())
                        plannedListAttrs.add(1, planned_list_importance_lvl!!)
                        plannedListAttrs.add(2, et_date.text.toString())
                        plannedListAttrs.add(3, mutableListOf<Product>())
                        plannedList.put(planned_list_name.text.toString(), plannedListAttrs)

                        mPlannedListViewModel.insert(PlannedList)

//                        dbHandler.addPlannedListToDB(PlannedList)

                        if(intent.getStringExtra("from") != "PlannedListFragment") {
                            val dialog = AddToPlannedDialog(this)
                            dialog.show()
                        }
                    }
                    Thread.sleep(30)
                    this.finish()
                }

                Log.i("Planned_List", "$plannedList")
                Log.i("Planned_new_list", "${Constants.listOfPlannedLists}")
            }
        }
    }

    private fun updateDateInView() {
        val myFormat = "dd MMM yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        et_date.setText(sdf.format(cal.time).toString().lowercase())
        et_date.setTextColor(resources.getColor(R.color.dark_gray))
        val colorStateList: ColorStateList =
            ColorStateList.valueOf(resources.getColor(R.color.green_cart_color))
        et_date.backgroundTintList = colorStateList
        et_date.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.planned_list_edittext_ic_calendar_selected,
            0
        )
    }


}

object ExpandOrCollapse {
    fun expand(v: View, duration: Int, targetHeight: Int) {
        val prevHeight = v.height
        v.visibility = View.VISIBLE
        val valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight)
        valueAnimator.addUpdateListener { animation ->
            v.layoutParams.height = animation.animatedValue as Int
            v.layoutParams.width = animation.animatedValue as Int
            v.requestLayout()
        }
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.duration = duration.toLong()
        valueAnimator.start()
    }

    fun collapse(v: View, duration: Int, targetHeight: Int) {
        val prevHeight = v.height
        val valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight)
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.addUpdateListener { animation ->
            v.layoutParams.height = animation.animatedValue as Int
            v.requestLayout()
        }
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.duration = duration.toLong()
        valueAnimator.start()
    }
}
