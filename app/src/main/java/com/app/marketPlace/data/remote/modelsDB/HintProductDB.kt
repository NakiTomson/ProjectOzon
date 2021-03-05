package com.app.marketPlace.data.remote.modelsDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hintProductDb")
data class HintProductDB(

    val nameRequest: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    )