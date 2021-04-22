package com.app.marketPlace.presentation.factory


import com.app.marketPlace.data.remote.models.Banner
import com.app.marketPlace.data.remote.models.Categories
import com.app.marketPlace.data.remote.models.Stories
import com.app.marketPlace.domain.models.CombineProducts
import com.app.marketPlace.domain.models.LiveStreamItem

sealed class TypeResource(open val result: Resource<*>) {
    class Category(override val result: Resource<List<Categories>>): TypeResource(result)
    class Banners(override val result : Resource<MutableList<Banner>>): TypeResource(result)
    class Story(override val result : Resource<Stories>): TypeResource(result)
    class LiveStreams(override val result : Resource<LiveStreamItem>): TypeResource(result)
    class Product(override val result : Resource<CombineProducts>): TypeResource(result)
    class HorizontalProduct(override val result : Resource<CombineProducts>): TypeResource(result)
    class Registration : TypeResource(Resource(status = Resource.Status.COMPLETED,"reg"))
    class Undefined : TypeResource(Resource(status = Resource.Status.COMPLETED,null))
}
