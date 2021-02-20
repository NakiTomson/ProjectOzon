package com.appmarketplace.ozon.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.appmarketplace.ozon.data.models.HintProductDB


@Database(entities = [HintProductDB::class], version = 1)
abstract class OzonAppDataBase : RoomDatabase() {

    abstract fun productsDao(): HintsProductsDao?
}

