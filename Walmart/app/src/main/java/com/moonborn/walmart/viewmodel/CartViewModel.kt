package com.moonborn.walmart.viewmodel

import androidx.lifecycle.*
import com.moonborn.walmart.model.database.ProductDatabase.CartRepository
import com.moonborn.walmart.model.database.ProductDatabase.WishlistRepository
import com.moonborn.walmart.model.entities.Cart
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class CartViewModel(private val repository: CartRepository): ViewModel() {

    fun insert(cart: Cart) = viewModelScope.launch {
        repository.insertItemToCartData(cart)
    }

    val allItemsList:LiveData<List<Cart>> = repository.allItemsInWishlist.asLiveData()

    fun delete(id: String) = viewModelScope.launch {
        repository.deleteItemInCart(id)
    }

    suspend fun updateQuantity(cart: Cart){
        repository.updateQuantity(cart)
    }

    fun clearTable() = viewModelScope.launch {
        repository.clearTable()
    }
}

class CartViewModelFactory(private val repository: CartRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CartViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}