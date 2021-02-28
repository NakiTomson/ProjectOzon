package com.appmarketplace.ozon.data.db

import androidx.room.*
import com.appmarketplace.ozon.data.remote.modelsDB.BasketProductDb

import com.appmarketplace.ozon.data.remote.modelsDB.HintProductDB
import com.appmarketplace.ozon.data.remote.modelsDB.ProductDb
import com.appmarketplace.ozon.data.remote.modelsDB.UserDB


@Dao
interface BasketProductDao {

    @Query("SELECT * FROM basket")
    fun getAll(): List<BasketProductDb>?

//    @Query("SELECT * FROM user WHERE mail == :login")
//    fun getUser(login:String):UserDB


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: BasketProductDb?)

    @Update
    fun update(product: BasketProductDb?)

    @Delete
    fun delete(product: BasketProductDb?)

}