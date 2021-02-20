package com.appmarketplace.ozon.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HintProductDB(

    val nameRequset: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

)