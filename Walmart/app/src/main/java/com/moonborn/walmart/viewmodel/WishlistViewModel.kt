package com.moonborn.walmart.viewmodel

import androidx.lifecycle.*
import com.moonborn.walmart.model.database.ProductDatabase.WishlistRepository
import com.moonborn.walmart.model.entities.Wishlist
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class WishlistViewModel(private val repository: WishlistRepository): ViewModel() {

    fun insert(wishlist: Wishlist) = viewModelScope.launch {

        repository.insertWishlistData(wishlist)

//        val myFlow = MutableStateFlow(exists(wishlist.id))
//        val myLiveData = myFlow.asLiveData(viewModelScope.coroutineContext)
//
//
//        myLiveData.asFlow().collect(){
//            count ->
//            if(count == null)
//
//
//        }

    }

    val allItemsList: LiveData<List<Wishlist>> = repository.allItemsInWishlist.asLiveData()

    fun delete(id: String) = viewModelScope.launch {
        repository.deleteItemInWishlist(id)
    }

    fun exists(id: String) = viewModelScope.launch {
        repository.exists(id)
    }

    fun clearTable() = viewModelScope.launch {
        repository.clearTable()

    }

}

class WishlistViewModelFactory(private val repository: WishlistRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WishlistViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return WishlistViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}