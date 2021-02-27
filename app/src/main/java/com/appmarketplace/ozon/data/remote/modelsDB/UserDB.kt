package com.appmarketplace.ozon.data.remote.modelsDB

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class UserDB(
    val name:String,
    val phone:String,
    val mail:String,
    val password:String,
    val address:String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) {
}