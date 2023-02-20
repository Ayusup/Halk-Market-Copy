package com.moonborn.walmart.Models

class CategoryModel(
    private var id: String,
    private var name: String,
    private var categoriesInside: MutableList<String>
) {

    fun getId(): String {
        return id
    }

    fun getName(): String {
        return name
    }

    fun getCategoriesInside(): MutableList<String> {
        return categoriesInside
    }

    fun setId(id: String){
        this.id = id
    }

    fun setName(name: String){
        this.name = name
    }

    fun setCategoriesInside(categoriesInside: MutableList<String>){
        this.categoriesInside = categoriesInside
    }



}