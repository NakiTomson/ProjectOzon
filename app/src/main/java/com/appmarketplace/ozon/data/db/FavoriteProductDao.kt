package com.appmarketplace.ozon.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.appmarketplace.ozon.data.remote.modelsDB.BasketProductDb

import com.appmarketplace.ozon.data.remote.modelsDB.HintProductDB
import com.appmarketplace.ozon.data.remote.modelsDB.ProductDb
import com.appmarketplace.ozon.data.remote.modelsDB.UserDB
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteProductDao {

    @Query("SELECT * FROM products")
    fun getAll(): List<ProductDb>?

    @Query("SELECT * FROM products")
    fun getAllFlow(): Flow<List<BasketProductDb>?>

    @Query("SELECT id FROM products")
    fun getAllIds(): Flow<List<Int>?>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: ProductDb?)

    @Update
    fun update(product: ProductDb?)

    @Delete
    fun delete(product: ProductDb?)


}