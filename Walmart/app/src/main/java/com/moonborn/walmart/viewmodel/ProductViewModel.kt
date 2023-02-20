package com.moonborn.walmart.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.moonborn.walmart.Constants
import com.moonborn.walmart.model.database.ProductDatabase.ProductRepository
import com.moonborn.walmart.model.entities.Product
import com.moonborn.walmart.view.fragments.SeeAllFragment
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    fun insert(product: Product) = viewModelScope.launch {
        repository.insertProductData(product)
        Log.i("InsertedItem", "${product.name}")
    }

    val allProductsList: LiveData<List<Product>> = repository.allProductsList.asLiveData()



    fun update(product: Product) = viewModelScope.launch {
        repository.updateProductData(product)
    }

    fun delete(product: Product) = viewModelScope.launch {
        repository.deleteProductData(product)
    }

    fun getProductById(id: String): LiveData<Product> =

        repository.getProductByID(id).asLiveData()

    fun clearTable() = viewModelScope.launch {
        repository.clearTable()
    }








    fun getProductByDeal(dealName: String): LiveData<List<Product>> {
        var productsByDealList:LiveData<List<Product>>? = null
        when(dealName){
            Constants.NEWPRODUCTSDEAL -> productsByDealList = repository.newProducts.asLiveData()
            Constants.PROMOSALEDEAL -> productsByDealList = repository.promoProducts.asLiveData()
            Constants.POPULARDEAL -> productsByDealList = repository.popularProducts.asLiveData()
            Constants.SALESDEAL -> productsByDealList = repository.onSaleProducts.asLiveData()
        }
        return productsByDealList!!
    }

    fun searchProductInDeals(searchText: String, dealName: String): LiveData<List<Product>>?{
        return repository.searchItem("%$searchText%", dealName)?.asLiveData()
    }


    fun getFilteredList(value: String) : LiveData<List<Product>> =
        repository.filteredProductsList(value).asLiveData()



}

class ProductViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProductViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}