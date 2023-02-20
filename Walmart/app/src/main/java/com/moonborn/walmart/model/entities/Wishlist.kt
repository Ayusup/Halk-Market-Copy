package com.moonborn.walmart.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "wishlist")
data class Wishlist(
    @ColumnInfo val id: String,
    @PrimaryKey(autoGenerate = true) val columnId: Int = 0
)