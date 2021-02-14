package com.appmarketplace.ozon.presentation.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class GeneralCategory(
    @SerializedName("from")
    @Expose
    internal var from: Int? = null,

    @SerializedName("to")
    @Expose
    internal var to: Int? = null,

    @SerializedName("currentPage")
    @Expose
    internal var currentPage: Int? = null,

    @SerializedName("total")
    @Expose
    internal var total: Int? = null,

    @SerializedName("totalPages")
    @Expose
    internal var totalPages: Int? = null,

    @SerializedName("queryTime")
    @Expose
    internal var queryTime: String? = null,

    @SerializedName("totalTime")
    @Expose
    internal var totalTime: String? = null,

    @SerializedName("partial")
    @Expose
    internal var partial: Boolean? = null,

    @SerializedName("canonicalUrl")
    @Expose
    internal var canonicalUrl: String? = null,

    @SerializedName("categories")
    @Expose internal var categories: MutableList<Categoryes> = mutableListOf()
) {

}

data class Categoryes(
    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("id")
    @Expose
    var id: String? = null,

    var image: String = "https://www.dropbox.com/s/3z9sqdui2zztnws/one.png?dl=0",
) {
}