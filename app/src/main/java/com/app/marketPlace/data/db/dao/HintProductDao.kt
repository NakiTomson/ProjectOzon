package com.app.marketPlace.data.db.dao

import androidx.room.*

import com.app.marketPlace.data.db.models.HintProductDb
import kotlinx.coroutines.flow.Flow


@Dao
interface HintProductDao {

    @Query("SELECT * FROM hintProduct")
    fun getAll(): List<HintProductDb>?

    @Query("SELECT * FROM hintProduct")
    fun getAllFlow(): Flow<List<HintProductDb>>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(employee: HintProductDb?)

    @Update
    fun update(employee: HintProductDb?)

    @Delete
    fun delete(employee: HintProductDb?)
}