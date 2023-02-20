package com.moonborn.walmart.application

import android.app.Application
import com.moonborn.walmart.model.database.ProductDatabase.*
import com.moonborn.walmart.model.entities.Cart

class WalmartApplication: Application() {

    private val productsDatabase by lazy { ProductsRoomDatabase.getDatabse((this@WalmartApplication)) }

    val productsRepository by lazy { ProductRepository(productsDatabase.productDao()) }

    val wishlistRepository by lazy { WishlistRepository(productsDatabase.wishlistDao()) }

    val plannedListsRepository by lazy { PlannedListRepository(productsDatabase.plannedLisDao()) }

    val cartRepository by lazy { CartRepository(productsDatabase.cartDao()) }
}