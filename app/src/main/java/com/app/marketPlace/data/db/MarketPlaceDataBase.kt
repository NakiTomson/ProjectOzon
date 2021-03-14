package com.app.marketPlace.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.marketPlace.data.db.dao.HintProductDao
import com.app.marketPlace.data.db.dao.InBasketDao
import com.app.marketPlace.data.db.dao.InFavoriteProductDao
import com.app.marketPlace.data.db.dao.UserDao
import com.app.marketPlace.data.db.models.BasketProductDb
import com.app.marketPlace.data.db.models.HintProductDb
import com.app.marketPlace.data.db.models.FavoriteProductDb
import com.app.marketPlace.data.db.models.UserDb


@Database(entities = [HintProductDb::class,UserDb::class,FavoriteProductDb::class,BasketProductDb::class], version = 1)
abstract class MarketPlaceDataBase : RoomDatabase() {

    abstract fun hintProductsDao(): HintProductDao
    abstract fun userDao(): UserDao
    abstract fun productsDao(): InFavoriteProductDao
    abstract fun productsBasketDao(): InBasketDao
}

