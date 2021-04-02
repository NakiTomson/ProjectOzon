package com.app.marketPlace.data.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CategoriesPath(
    @SerializedName("id")
    @Expose
    var id: String? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,
)