package com.moonborn.walmart.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.moonborn.walmart.Models.PlannedListModel
import com.moonborn.walmart.model.database.ProductDatabase.PlannedListRepository
import com.moonborn.walmart.model.database.ProductDatabase.WishlistRepository
import com.moonborn.walmart.model.entities.PlannedListsModel
import com.moonborn.walmart.model.entities.Product
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PlannedListViewModel(private val repository: PlannedListRepository): ViewModel() {

    val allPlannedLists: LiveData<List<PlannedListsModel>> = repository.allPlannedLists.asLiveData()

    fun insert(plannedListModel: PlannedListsModel) = viewModelScope.launch {
        repository.insertPlannedList(plannedListModel)
    }

    fun update(plannedListModel: PlannedListsModel) = viewModelScope.launch {
        repository.updatePlannedList(plannedListModel)
    }

    fun delete(id: String) = viewModelScope.launch {
        repository.deletePlannedList(id)
        Log.i("hjhj", "sdvsdsdcdssd")
    }

    fun clearTable() = viewModelScope.launch{
        repository.clearTable()
    }

}

class PlannedListViewModelFactory(private val repository: PlannedListRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PlannedListViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return PlannedListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}