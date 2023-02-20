package com.moonborn.walmart.model.database.ProductDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.moonborn.walmart.model.entities.Wishlist
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDAO {

    @Insert
    suspend fun insertItemToWishlist(wishlist: Wishlist)

    @Query("SELECT * FROM WISHLIST ORDER BY ID")
    fun getAllItemsInWishlist(): Flow<List<Wishlist>>

    @Query("DELETE FROM WISHLIST WHERE id = :id")
    suspend fun deleteItemInWishlist(id: String)


    @Query("SELECT * FROM WISHLIST WHERE id = :id")
    fun exists(id: String): Flow<List<Wishlist>>

    @Query("DELETE FROM WISHLIST")
    suspend fun clearTable()


}
