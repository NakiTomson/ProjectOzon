package com.app.marketPlace.data.db

import androidx.room.*
import com.app.marketPlace.data.remote.modelsDB.UserDB


@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): List<UserDB>?

    @Query("SELECT * FROM user WHERE mail == :login")
    fun getUser(login: String):UserDB


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserDB?)

    @Update
    fun update(user: UserDB?)

    @Delete
    fun delete(user: UserDB?)
}