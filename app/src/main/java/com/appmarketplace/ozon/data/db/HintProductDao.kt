package com.appmarketplace.ozon.data.db

import androidx.room.*

import com.appmarketplace.ozon.data.remote.modelsDB.HintProductDB
import kotlinx.coroutines.flow.Flow


@Dao
interface HintProductDao {

    @Query("SELECT * FROM hintproductdb")
    fun getAll(): List<HintProductDB>?

    @Query("SELECT * FROM hintproductdb")
    fun getAllFlow(): Flow<List<HintProductDB>>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(employee: HintProductDB?)

    @Update
    fun update(employee: HintProductDB?)

    @Delete
    fun delete(employee: HintProductDB?)
}