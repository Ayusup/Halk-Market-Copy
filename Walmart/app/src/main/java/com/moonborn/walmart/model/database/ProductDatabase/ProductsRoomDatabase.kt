package com.moonborn.walmart.model.database.ProductDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moonborn.walmart.model.entities.*

@Database(entities = [Product::class, Cart::class, Wishlist::class, PlannedListsModel::class], version = 3)
@TypeConverters(AttachedViewsTypeConverter::class, ItemsInListTypeConverter::class)
abstract class ProductsRoomDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDAO
    abstract fun wishlistDao(): WishlistDAO
    abstract fun plannedLisDao(): PlannedListDao
    abstract fun cartDao(): CartDAO

    companion object{

        @Volatile
        private var INSTANCE: ProductsRoomDatabase? = null

        fun getDatabse(context: Context): ProductsRoomDatabase {
            //if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductsRoomDatabase::class.java,
                    "product_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                //return instance
                instance
            }


        }


    }
}