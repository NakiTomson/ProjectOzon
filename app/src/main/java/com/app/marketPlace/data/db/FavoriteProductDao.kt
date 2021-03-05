package com.app.marketPlace.data.db

import androidx.room.*
import com.app.marketPlace.data.remote.modelsDB.BasketProductDb

import com.app.marketPlace.data.remote.modelsDB.ProductDb
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