package com.moonborn.walmart.model.database.ProductDatabase

import androidx.annotation.WorkerThread
import com.moonborn.walmart.model.entities.PlannedListsModel
import kotlinx.coroutines.flow.Flow

class PlannedListRepository(private val plannedListDao: PlannedListDao) {

    @WorkerThread
    suspend fun insertPlannedList(plannedListModel: PlannedListsModel){
        plannedListDao.insertPlannedList(plannedListModel)
    }

    val allPlannedLists : Flow<List<PlannedListsModel>> = plannedListDao.getAllPlannedLists()

    @WorkerThread
    suspend fun deletePlannedList(id: String){
        plannedListDao.deletePlannedList(id)
    }

    @WorkerThread
    suspend fun updatePlannedList(plannedListModel: PlannedListsModel){
        plannedListDao.updatePlannedList(plannedListModel)
    }


    @WorkerThread
    suspend fun clearTable(){
        plannedListDao.clearTable()
    }

}