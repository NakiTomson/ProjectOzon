package com.appmarketplace.ozon.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.appmarketplace.ozon.data.remote.modelsDB.HintProductDB


@Database(entities = [HintProductDB::class], version = 1)
abstract class OzonAppDataBase : RoomDatabase() {

    abstract fun productsDao(): HintsProductsDao?
}

