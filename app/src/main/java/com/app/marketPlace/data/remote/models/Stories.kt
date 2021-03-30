package com.app.marketPlace.data.remote.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Stories {

    @SerializedName("array")
    @Expose
    var arrayImages: List<String>? = null
}