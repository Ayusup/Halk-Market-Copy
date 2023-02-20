package com.moonborn.walmart.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class Cart(
    @ColumnInfo val id: String,
    @ColumnInfo(name = "quantity") val quantity: Int = 0,
    @PrimaryKey(autoGenerate = true) val columnId: Int = 0
)

