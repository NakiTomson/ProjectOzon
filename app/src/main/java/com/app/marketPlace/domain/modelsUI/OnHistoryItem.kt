package com.app.marketPlace.domain.modelsUI

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class OnHistoryItem(

        @SerializedName("resultHistoryData")
        @Expose
        var resultHistoryData: List<ResultHistoryData>? = null,

        )

data class ResultHistoryData(

        @SerializedName("historyUrl")
        @Expose
        var historyUrl: String? = null,

        @SerializedName("HistoryUrlContent")
        @Expose
        var historyUrlContent: String? = null, )