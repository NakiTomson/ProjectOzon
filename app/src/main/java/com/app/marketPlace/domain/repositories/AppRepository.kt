package com.app.marketPlace.domain.repositories

import com.app.marketPlace.data.remote.services.APIKEY1
import com.app.marketPlace.data.remote.services.APIKEY2
import com.app.marketPlace.data.utils.Configs
import com.app.marketPlace.domain.modelsUI.*
import com.app.marketPlace.domain.modelsUI.OnProductItem.Type
import com.app.marketPlace.presentation.rowType.Resource


sealed class Params(
    open var pathId: String = Configs.HOME_AUDIO,
    open var pageSize: String = "3",
    open var apiKey: String = APIKEY2,
    open var page: String = "1",
    open var typeProduct: Type = Type.OnlyImage
){

    class CategoriesProductParams(
        override var pageSize: String = "3",
        override var apiKey: String = APIKEY1,
        override var page: String = "1"
    ) : Params()


    class ProductsParams(
        override var pathId: String = Configs.HOME_AUDIO,
        override var pageSize: String = "3",
        override var apiKey: String = APIKEY2,
        override var page: String = "1",
        override var typeProduct:Type,
    ) : Params()

}


sealed class Results {

    data class ResultCategoryProduct(val result: Resource<MutableList<MutableList<OnBoardingItem>>>):Results()
    data class ResultBanner(val result : Resource<MutableList<OnBoardingItem>>):Results()
    data class ResultHistory(val result : Resource<OnHistoryItem>):Results()
    data class ResultLive(val result : Resource<OnLiveItem>):Results()
    data class ResultProduct(val result : Resource<OnOfferProductsItem>):Results()

}