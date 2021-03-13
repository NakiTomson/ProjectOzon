package com.app.marketPlace.data.db.dao

import androidx.room.*
import com.app.marketPlace.data.db.models.UserDb


@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): List<UserDb>?

    @Query("SELECT * FROM user WHERE mail == :login")
    fun getUser(login: String):UserDb


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserDb?)

    @Update
    fun update(user: UserDb?)

    @Delete
    fun delete(user: UserDb?)
}