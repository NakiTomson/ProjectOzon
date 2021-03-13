package com.app.marketPlace.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class UserDb(
    val name:String,
    val phone:String,
    val mail:String,
    val password:String,
    val address:String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)