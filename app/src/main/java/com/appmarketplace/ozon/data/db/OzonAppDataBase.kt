package com.appmarketplace.ozon.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.appmarketplace.ozon.data.remote.modelsDB.HintProductDB
import com.appmarketplace.ozon.data.remote.modelsDB.ProductDb
import com.appmarketplace.ozon.data.remote.modelsDB.UserDB


@Database(entities = [HintProductDB::class,UserDB::class,ProductDb::class], version = 1)
abstract class OzonAppDataBase : RoomDatabase() {

    abstract fun hintProductsDao(): HintsProductsDao
    abstract fun userDao(): UserDao
    abstract fun productsDao(): ProductDao
}

