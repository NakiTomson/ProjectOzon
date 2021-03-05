package com.app.marketPlace.data.db

import androidx.room.*

import com.app.marketPlace.data.remote.modelsDB.HintProductDB
import kotlinx.coroutines.flow.Flow


@Dao
interface HintProductDao {

    @Query("SELECT * FROM hintProductDb")
    fun getAll(): List<HintProductDB>?

    @Query("SELECT * FROM hintProductDb")
    fun getAllFlow(): Flow<List<HintProductDB>>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(employee: HintProductDB?)

    @Update
    fun update(employee: HintProductDB?)

    @Delete
    fun delete(employee: HintProductDB?)
}