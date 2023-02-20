package com.moonborn.walmart.model.database.ProductDatabase

import androidx.room.*
import com.moonborn.walmart.model.entities.PlannedListsModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PlannedListDao {

    @Insert
    suspend fun insertPlannedList(plannedListModel: PlannedListsModel)

    @Query("SELECT * FROM PLANNED_LISTS ORDER BY ID")
    fun getAllPlannedLists(): Flow<List<PlannedListsModel>>

    @Query("DELETE FROM PLANNED_LISTS WHERE id = :id")
    suspend fun deletePlannedList(id: String)

    @Update
    suspend fun updatePlannedList(plannedListModel: PlannedListsModel)

    @Query("DELETE FROM PLANNED_LISTS")
    suspend fun clearTable()

}