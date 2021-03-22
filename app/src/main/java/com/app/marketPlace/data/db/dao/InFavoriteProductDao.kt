package com.app.marketPlace.data.db.dao

import androidx.room.*

import com.app.marketPlace.data.db.models.FavoriteProductDb
import kotlinx.coroutines.flow.Flow


@Dao
interface InFavoriteProductDao {

    @Query("SELECT * FROM products")
    fun getAll(): List<FavoriteProductDb>?

    @Query("SELECT * FROM products")
    fun getAllFlow(): Flow<List<FavoriteProductDb>?>

    @Query("SELECT id FROM products")
    fun getAllIds(): Flow<List<Int>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: FavoriteProductDb?)

    @Update
    fun update(product: FavoriteProductDb?)

    @Delete
    fun delete(product: FavoriteProductDb?)


}