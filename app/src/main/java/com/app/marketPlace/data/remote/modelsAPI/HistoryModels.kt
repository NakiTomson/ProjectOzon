package com.app.marketPlace.data.remote.modelsAPI

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class HistoryModels {


    @SerializedName("array")
    @Expose
    var arrayImages: List<String>? = null

}