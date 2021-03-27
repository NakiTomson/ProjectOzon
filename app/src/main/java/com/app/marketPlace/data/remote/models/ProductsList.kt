package com.app.marketPlace.data.remote.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class ProductsList(

        @SerializedName("totalPages")
        @Expose
        val totalPages: Int? = null,

        @SerializedName("products")
        @Expose val products: List<Product>? = null
)