package com.moonborn.walmart.model.database.ProductDatabase

import androidx.annotation.WorkerThread
import com.moonborn.walmart.model.entities.Wishlist
import kotlinx.coroutines.flow.Flow

class WishlistRepository(private val wishlistDAO: WishlistDAO) {

    @WorkerThread
    suspend fun insertWishlistData(wishlist: Wishlist){
        wishlistDAO.insertItemToWishlist(wishlist)
    }

    fun exists(id: String): Flow<List<Wishlist>> = wishlistDAO.exists(id)

    val allItemsInWishlist : Flow<List<Wishlist>> = wishlistDAO.getAllItemsInWishlist()

    @WorkerThread
    suspend fun deleteItemInWishlist(id: String){
        wishlistDAO.deleteItemInWishlist(id)
    }



    @WorkerThread
    suspend fun clearTable(){
        wishlistDAO.clearTable()
    }
}