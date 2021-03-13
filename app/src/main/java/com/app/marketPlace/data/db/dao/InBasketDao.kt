package com.app.marketPlace.data.db.dao

import androidx.room.*
import com.app.marketPlace.data.db.models.BasketProductDb

import kotlinx.coroutines.flow.Flow


@Dao
interface InBasketDao {


    @Query("SELECT * FROM basket")
    fun getAllFlow(): Flow<List<BasketProductDb>?>

    @Query("SELECT id FROM basket")
    fun getAllIds(): Flow<List<Int>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: BasketProductDb?)

    @Update
    fun update(product: BasketProductDb?)

    @Delete
    fun delete(product: BasketProductDb?)


}