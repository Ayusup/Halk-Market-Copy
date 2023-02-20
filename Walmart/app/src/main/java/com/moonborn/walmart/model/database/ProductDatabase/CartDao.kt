package com.moonborn.walmart.model.database.ProductDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.moonborn.walmart.model.entities.Cart
import com.moonborn.walmart.model.entities.Wishlist
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDAO {

    @Insert
    suspend fun insertItemToCart(cart: Cart)

    @Query("SELECT * FROM CART ORDER BY ID")
    fun getAllItemsInCart(): Flow<List<Cart>>

    @Query("DELETE FROM CART WHERE id = :id")
    suspend fun deleteItemInCart(id: String)

    @Update
    suspend fun updateQuantity(cart: Cart)

    @Query("DELETE FROM CART")
    suspend fun clearTable()

}