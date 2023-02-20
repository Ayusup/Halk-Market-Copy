package com.moonborn.walmart.model.database.ProductDatabase

import androidx.annotation.WorkerThread
import com.moonborn.walmart.model.entities.Cart
import com.moonborn.walmart.model.entities.Wishlist
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDAO: CartDAO) {

    @WorkerThread
    suspend fun insertItemToCartData(cart: Cart){
        cartDAO.insertItemToCart(cart)
    }

    val allItemsInWishlist: Flow<List<Cart>> = cartDAO.getAllItemsInCart()

    @WorkerThread
    suspend fun deleteItemInCart(id: String){
        cartDAO.deleteItemInCart(id)
    }

    @WorkerThread
    suspend fun clearTable(){
        cartDAO.clearTable()
    }

    @WorkerThread
    suspend fun updateQuantity(cart: Cart){
        cartDAO.updateQuantity(cart)
    }

}