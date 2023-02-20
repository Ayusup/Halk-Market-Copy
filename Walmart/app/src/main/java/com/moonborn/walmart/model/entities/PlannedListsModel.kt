package com.moonborn.walmart.model.entities

import android.os.Parcelable
import android.view.View
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize

@Parcelize
@TypeConverters
@Entity(tableName = "planned_lists")
data class PlannedListsModel (
    @ColumnInfo(name = "id") var id: String = "",
    @ColumnInfo(name = "list_name") var ListName: String = "",
    @ColumnInfo(name = "list_importance") var ListImportance: String = "",
    @ColumnInfo(name = "list_date") var ListDate: String = "",
    @TypeConverters(ItemsInListTypeConverter::class)
    @ColumnInfo(name = "items_in_list") var ItemsInList: MutableList<String> = mutableListOf(),
    @PrimaryKey(autoGenerate = true) var columnId: Int = 0
): Parcelable
//{
//
//
//    fun getListId(): String {
//        return id
//    }
//
//
//    fun getListName(): String {
//        return ListName
//    }
//
//    fun setListName(name: String) {
//        this.ListName = name
//    }
//
//    fun getListImportance(): String {
//        return ListImportance
//    }
//
//    fun setListImportance(importance: String) {
//        this.ListImportance = importance
//    }
//
//    fun getListDate(): String {
//        return ListDate
//    }
//
//    fun setListDate(date: String) {
//        this.ListDate = date
//    }
//
//    fun getItemsInList(): List<Product> {
//        return this.ItemsInList
//    }
//
//
//
//
////
////    fun addItemToList(item: Product){
////        var itemsInList = mutableListOf<Product>()
////        for(i in this.ItemsInList){
////            itemsInList.add(i)
////        }
////        itemsInList.add(item)
////
////        this.ItemsInList = itemsInList
////    }
//
//
////    fun RemoveItemFromList(item: Product){
////        if(item in this.ItemsInList){
////            var itemsInList = this.ItemsInList
////            itemsInList.remove(item)
////            this.ItemsInList = itemsInList
////        }
////    }
////}
//}

class ItemsInListTypeConverter{


    @TypeConverter
    fun listToJson(mutablelist: MutableList<String>?): String = Gson().toJson(mutablelist)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toMutableList()
}