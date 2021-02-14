package com.appmarketplace.ozon.presentation.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class OnLiveItem {
    @SerializedName("resultLiveData")
    @Expose
    var resultLiveData: MutableList<ListResultLiveItems>? = null
}

data class ListResultLiveItems(
        @SerializedName("onBoardingImageUrl")
        @Expose
        val onBoardingImageUrl: String? = null,

        @SerializedName("onIconCompanyUrl")
        @Expose
        val onIconCompanyUrl: String? = null,

        @SerializedName("countUser")
        @Expose
        val countUser: String? = null,

        @SerializedName("description")
        @Expose
        val description: String? = null,

        @SerializedName("nameOfCompany")
        @Expose
        val nameOfCompany: String? = null,

        @SerializedName("statusLiveStream")
        @Expose
        val statusLiveStream: String? = null,

        @SerializedName("onContentUrl")
        @Expose
        val onContentUrl: String? = null,
) {

}