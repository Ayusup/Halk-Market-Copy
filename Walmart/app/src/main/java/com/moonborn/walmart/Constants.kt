package com.moonborn.walmart

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moonborn.walmart.Models.BrandModel
import com.moonborn.walmart.Models.CategoryModel
import com.moonborn.walmart.Models.PlannedListModel
import com.moonborn.walmart.model.entities.PlannedListsModel
//import com.moonborn.walmart.Models.Product
import com.moonborn.walmart.model.entities.Product
import java.util.*

object Constants {

    const val ENGLISH: String = "en"
    const val RUSSIAN: String = "ru"
    const val TURKMEN: String = "tk"

    const val USERS: String = "users"
    const val PRODUCTS: String = "products"
    const val CATEGORIES: String = "categories"
    const val BRANDS: String = "brands"

    const val NEWPRODUCTSDEAL = "new_products"
    const val PROMOSALEDEAL = "promoSale"
    const val POPULARDEAL = "popular"
    const val SALESDEAL = "onSale"

    var brandsList: MutableList<BrandModel> = mutableListOf()
    var pList: MutableList<Product> = mutableListOf()
//    var categoriesNames: MutableMap<String, MutableList<String>> = mutableMapOf()
    var categories: MutableList<CategoryModel> = mutableListOf()

    const val API_ENDPOINT: String = "recipes/random"
    const val API_KEY: String = "apiKey"
    const val LIMIT_LICENSE: String = "limitLicense"
    const val TAGS: String = "tags"
    const val NUMBER: String = "number"

    const val BASE_URL: String = "https://api.spoonacular.com/"

    const val API_KEY_VALUE: String = "a86d3fe2f0914c9fbde2505cfb107457"

    const val LIMIT_LICENSE_VALUE: Boolean = true
    const val TAGS_VALUE: String = "vegeterian, dessert"
    const val NUMBER_VALUE: Int = 1


    const val DISH_IMAGE_SOURCE_ONLINE: String = "Online"


    val ads: MutableList<Int> = mutableListOf(R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5)


    fun isNetworkAvalible(context: Context): Boolean{
        val connectivitymanager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as
                 ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val network = connectivitymanager.activeNetwork ?: return false
            val activeNetwork = connectivitymanager.getNetworkCapabilities(network) ?: return false

            return when{
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }else {
            val networkInfo = connectivitymanager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnectedOrConnecting
        }
    }



    var newProducts = mutableListOf<Product>()
    var promoProducts = mutableListOf<Product>()
    var popularProducts = mutableListOf<Product>()
    var saleProducts = mutableListOf<Product>()

    var itemsInWishlist: MutableList<Product> = mutableListOf()
    var itemsInCart: MutableList<Product> = mutableListOf()
    var listOfPlannedLists: MutableList<PlannedListsModel> = mutableListOf()



//    fun getCategories(){
//        for(i in pList){
//
//            var categoriesOfProduct = i.getCategory().split(":")
//
//            categoriesNames.put(categoriesOfProduct[0], mutableListOf())
//
//
//
//
//        }
//    }

//    fun setCategories(){
//
//        for(i in categoriesNames){
//            categories.add(CategoryModel(UUID.randomUUID().toString(),  "urlNeeded", i.key, i.value))
//        }
//    }

    fun sort_by_deals(): MutableMap<String, ArrayList<Product>> {
        var products_by_deals = mutableMapOf<String, ArrayList<Product>>()
        products_by_deals.put("new_products", ArrayList())
        products_by_deals.put("promoSale", ArrayList())
        products_by_deals.put("popular", ArrayList())
        products_by_deals.put("onSale", ArrayList())



        for(i in Constants.pList){
            if(i.newSale == 1){
                products_by_deals["new_products"]?.add(i)
            }
            if(i.promoSale == 1){
                products_by_deals["promoSale"]?.add(i)

            }
            if(i.popular == 1){
                products_by_deals["popular"]?.add(i)
            }
            if(i.popular == 1){
                products_by_deals["onSale"]?.add(i)
            }
            Log.i("promoSale", "${i.promoSale}")
            Log.i("new", "${i.newSale}")
            Log.i("onSale", "${i.onSale}")
            Log.i("popular", "${i.onSale}")
        }
        return products_by_deals
    }




    fun sort_by_category(): MutableMap<String, MutableList<Product>> {
        val sorted_by_type: MutableMap<String, MutableList<Product>> = mutableMapOf()
        for(i in Constants.pList){
            if(i.category !in sorted_by_type){
                sorted_by_type.put(i.category, mutableListOf(i))
            } else if(i.category in sorted_by_type){
                sorted_by_type[i.category]!!.add(i)
            }
        }
        return sorted_by_type
    }

}

