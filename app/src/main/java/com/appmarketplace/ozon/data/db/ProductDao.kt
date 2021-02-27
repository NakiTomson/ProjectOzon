package com.appmarketplace.ozon.data.db

import androidx.room.*

import com.appmarketplace.ozon.data.remote.modelsDB.HintProductDB
import com.appmarketplace.ozon.data.remote.modelsDB.ProductDb
import com.appmarketplace.ozon.data.remote.modelsDB.UserDB


@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getAll(): List<ProductDb>?

//    @Query("SELECT * FROM user WHERE mail == :login")
//    fun getUser(login:String):UserDB


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: ProductDb?)

    @Update
    fun update(product: ProductDb?)

    @Delete
    fun delete(product: ProductDb?)

}