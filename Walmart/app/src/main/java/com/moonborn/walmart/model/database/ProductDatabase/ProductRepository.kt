package com.moonborn.walmart.model.database.ProductDatabase

import androidx.annotation.WorkerThread
import com.moonborn.walmart.Constants
import com.moonborn.walmart.model.entities.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class ProductRepository(private val productDao: ProductDAO) {

    @WorkerThread
    suspend fun insertProductData(product: Product){
        productDao.insertProductDetails(product)
    }

    val allProductsList : Flow<List<Product>> = productDao.getAllProductsList()

    @WorkerThread
    suspend fun updateProductData(product: Product){
        productDao.updateProductDetails(product)
    }

    @WorkerThread
    suspend fun deleteProductData(product: Product){
        productDao.deleteProductDetails(product)
    }

    @WorkerThread
    suspend fun clearTable(){
        productDao.clearTable()
    }

    @WorkerThread
    fun searchItem(searchText: String, dealName:String): Flow<List<Product>>? {
        var list: Flow<List<Product>>? = null
        when(dealName){
            Constants.NEWPRODUCTSDEAL -> list = productDao.searchItemInNewSale(searchText)
            Constants.PROMOSALEDEAL -> list = productDao.searchItemInPromoSale(searchText)
            Constants.POPULARDEAL -> list = productDao.searchItemInPopular(searchText)
            Constants.SALESDEAL -> list = productDao.searchItemInOnSale(searchText)
        }
        return list
    }

    val newProducts = productDao.getNewSaleProducts()

    val promoProducts = productDao.getPromoSaleProducts()

    val popularProducts = productDao.getPopularSaleProducts()

    val onSaleProducts = productDao.getOnSaleProducts()

    fun getProductByID(id: String): Flow<Product> = productDao.getProductById(id)




    fun filteredProductsList(value: String): Flow<List<Product>> = productDao.getFilteredProductsList(value)
}