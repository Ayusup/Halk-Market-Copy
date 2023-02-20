package com.moonborn.walmart.model.database.ProductDatabase

import androidx.room.*
import com.moonborn.walmart.model.entities.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDAO {

    @Insert
    suspend fun insertProductDetails(product: Product)

    @Query("SELECT * FROM PRODUCTS ORDER BY ID")
    fun getAllProductsList(): Flow<List<Product>>

    @Update
    suspend fun updateProductDetails(product: Product)

    @Delete
    suspend fun deleteProductDetails(product: Product)

    @Query("DELETE FROM PRODUCTS")
    suspend fun clearTable()

    @Query("SELECT * FROM PRODUCTS WHERE name LIKE :filterName")
    fun getFilteredProductsList(filterName: String): Flow<List<Product>>

    @Query("SELECT * FROM PRODUCTS WHERE id = :id")
    fun getProductById(id: String): Flow<Product>

    @Query("SELECT * FROM PRODUCTS WHERE new_sale = 1")
    fun getNewSaleProducts(): Flow<List<Product>>

    @Query("SELECT * FROM PRODUCTS WHERE promo_sale = 1")
    fun getPromoSaleProducts(): Flow<List<Product>>

    @Query("SELECT * FROM PRODUCTS WHERE popular = 1")
    fun getPopularSaleProducts(): Flow<List<Product>>

    @Query("SELECT * FROM PRODUCTS WHERE on_sale = 1")
    fun getOnSaleProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE name LIKE :searchText AND new_sale = 1")
    fun searchItemInNewSale(searchText: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE name LIKE :searchText AND promo_sale = 1")
    fun searchItemInPromoSale(searchText: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE name LIKE :searchText AND popular = 1")
    fun searchItemInPopular(searchText: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE name LIKE :searchText AND on_sale = 1")
    fun searchItemInOnSale(searchText: String): Flow<List<Product>>


}