package com.app.marketPlace.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hintProductDb")
data class HintProductDb(

    val nameRequest: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    )