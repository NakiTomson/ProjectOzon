package com.app.marketPlace.data.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Categories(
    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("id")
    @Expose
    var id: String? = null,

    var image: String = "https://www.dropbox.com/s/3z9sqdui2zztnws/one.png?dl=0",
)