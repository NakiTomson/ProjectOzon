package com.app.marketPlace.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.marketPlace.data.remote.modelsDB.BasketProductDb
import com.app.marketPlace.data.remote.modelsDB.HintProductDB
import com.app.marketPlace.data.remote.modelsDB.ProductDb
import com.app.marketPlace.data.remote.modelsDB.UserDB


@Database(entities = [HintProductDB::class,UserDB::class,ProductDb::class,BasketProductDb::class], version = 1)
abstract class MarketPlaceAppDataBase : RoomDatabase() {

    abstract fun hintProductsDao(): HintProductDao
    abstract fun userDao(): UserDao
    abstract fun productsDao(): FavoriteProductDao
    abstract fun productsBasketDao(): InBasketDao
}

