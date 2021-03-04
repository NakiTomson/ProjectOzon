package com.appmarketplace.ozon.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.appmarketplace.ozon.data.remote.modelsDB.BasketProductDb
import com.appmarketplace.ozon.data.remote.modelsDB.HintProductDB
import com.appmarketplace.ozon.data.remote.modelsDB.ProductDb
import com.appmarketplace.ozon.data.remote.modelsDB.UserDB


@Database(entities = [HintProductDB::class,UserDB::class,ProductDb::class,BasketProductDb::class], version = 1)
abstract class OzonAppDataBase : RoomDatabase() {

    abstract fun hintProductsDao(): HintProductDao
    abstract fun userDao(): UserDao
    abstract fun productsDao(): FavoriteProductDao
    abstract fun productsBasketDao(): InBasketDao
}

