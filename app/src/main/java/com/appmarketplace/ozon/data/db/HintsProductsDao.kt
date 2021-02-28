package com.appmarketplace.ozon.data.db

import androidx.room.*

import com.appmarketplace.ozon.data.remote.modelsDB.HintProductDB


@Dao
interface HintsProductsDao {

    @Query("SELECT * FROM hintproductdb")
    fun getAll(): List<HintProductDB>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(employee: HintProductDB?)

    @Update
    fun update(employee: HintProductDB?)

    @Delete
    fun delete(employee: HintProductDB?)
}