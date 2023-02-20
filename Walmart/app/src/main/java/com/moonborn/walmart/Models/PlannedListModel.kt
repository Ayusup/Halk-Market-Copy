package com.moonborn.walmart.Models

import com.moonborn.walmart.model.entities.Product

class PlannedListModel(
    private var ListId: String,
    private var ListName: String,
    private var ListImportance: String,
    private var ListDate: String,
    private var ItemsInList: MutableList<Product>
) {


    fun getListId(): String{
        return ListId
    }



    fun getListName(): String {
        return ListName
    }

    fun setListName(name: String){
        this.ListName = name
    }

    fun getListImportance(): String{
        return ListImportance
    }

    fun setListImportance(importance: String){
        this.ListImportance = importance
    }

    fun getListDate(): String{
        return ListDate
    }

    fun setListDate(date: String){
        this.ListDate = date
    }

    fun getItemsInList(): MutableList<Product>{
        return this.ItemsInList
    }



    fun addItemToList(item: Product){
        var itemsInList = mutableListOf<Product>()
        for(i in this.ItemsInList){
            itemsInList.add(i)
        }
        itemsInList.add(item)

        this.ItemsInList = itemsInList
    }


    fun RemoveItemFromList(item: Product){
        if(item in this.ItemsInList){
            var itemsInList = this.ItemsInList
            itemsInList.remove(item)
            this.ItemsInList = itemsInList
        }
    }
}

