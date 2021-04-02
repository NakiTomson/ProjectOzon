package com.app.marketPlace.data.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductOfServer(
    @SerializedName("sku")
    @Expose
    var sku: Int? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("startDate")
    @Expose
    var startDate: String? = null,

    @SerializedName("new")
    @Expose
    var new: Boolean? = null,

    @SerializedName("active")
    @Expose
    var active: Boolean? = null,

    @SerializedName("regularPrice")
    @Expose
    var regularPrice: Double? = null,

    @SerializedName("salePrice")
    @Expose
    var salePrice: Double? = null,

    @SerializedName("productTemplate")
    @Expose
    var productTemplate: String? = null,

    @SerializedName("categoryPath")
    @Expose
    var categoryPath: List<CategoriesPath>? = null,

    @SerializedName("shortDescription")
    @Expose
    var shortDescription: String? = null,

    @SerializedName("description")
    @Expose
    var description: Any? = null,

    @SerializedName("manufacturer")
    @Expose
    var manufacturer: String? = null,

    @SerializedName("modelNumber")
    @Expose
    var modelNumber: String? = null,

    @SerializedName("image")
    @Expose
    var image: String? = null,

    @SerializedName("largeFrontImage")
    @Expose
    var largeFrontImage: Any? = null,

    @SerializedName("alternateViewsImage")
    @Expose
    var alternateViewsImage: Any? = null,

    @SerializedName("angleImage")
    @Expose
    var angleImage: String? = null,

    @SerializedName("backViewImage")
    @Expose
    var backViewImage: Any? = null,

    @SerializedName("energyGuideImage")
    @Expose
    var energyGuideImage: Any? = null,

    @SerializedName("leftViewImage")
    @Expose
    var leftViewImage: Any? = null,

    @SerializedName("color")
    @Expose
    var color: String? = null,
    @SerializedName("longDescription")
    @Expose
    var longDescription: String? = null,

    )