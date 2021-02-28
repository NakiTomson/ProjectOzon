package com.appmarketplace.ozon.data.remote.modelsDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HintProductDB(

    val nameRequset: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

)