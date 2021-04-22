package com.app.marketPlace.domain.models

import com.app.marketPlace.data.utils.Constants
import com.app.marketPlace.domain.mappers.Mapper
import com.app.marketPlace.domain.mappers.MapperCategories
import com.app.marketPlace.domain.mappers.MapperDefault
import com.app.marketPlace.domain.mappers.MapperProducts
import com.app.marketPlace.presentation.factory.Resource

sealed class Params(
    open var attributes: String = Constants.attrSearch,
    open var pathId: String = Constants.CoffeeMaker,
    open var pageSize: String = "3",
    open var apiKey: String = Constants.ApiToken,
    open var page: String = "1",
    open var topOffer: String = "",
    open var bottomOffer: String = "",
    open var requestName: String = "",
    open var typeProduct: Product.Type = Product.Type.OnlyImage,
    open var spain: Int = 3,
    open var mapper: Mapper = MapperDefault()
){

    class CategoriesProductParams(
        override var pageSize: String = "3",
        override var apiKey: String = Constants.ApiToken,
        override var page: String = "1",
        override var mapper: Mapper = MapperCategories(),
        override var pathId: String = ""
    ) : Params()


    class ProductsParams(
        override var attributes: String = Constants.attrCategoryPathId,
        override var pathId: String = Constants.CellPhone,
        override var pageSize: String = "3",
        override var apiKey: String = Constants.ApiToken,
        override var page: String = "1",
        override var topOffer: String = "",
        override var bottomOffer: String = "",
        override var requestName: String = "",
        override var typeProduct: Product.Type,
        override var mapper: Mapper = MapperProducts(),
        override var spain: Int = 3
    ) : Params()

    class BannerParams(
    ) : Params()
    class StoriesParams(
    ) : Params()
    class LiveParams(
    ) : Params()
}
