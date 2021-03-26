package com.app.marketPlace.domain.mappers

import com.app.marketPlace.data.remote.models.Banner
import com.app.marketPlace.data.remote.models.Categories
import com.app.marketPlace.data.remote.models.CategoriesProduct
import com.app.marketPlace.domain.repositories.Params
import com.app.marketPlace.presentation.rowType.Resource

class MapperCategory :MapperToUi{

    override fun map(category: CategoriesProduct?, params: Params): Resource<List<Categories>> {

//
//        for ((index, i) in (0 until category?.categories!!.size).withIndex()) {
//            category.categories[i].image = ConstantsApp.listImagesByCategory[index]
//        }
//
//        val data = if (category.categories.size <= 21) {
//            mutableListOf(
//                category.categories
//                    .take(10)
//                    .map {
//                        Banner(
//                            title = it.name,
//                            onBoardingImageUrl = it.image,
//                            category = it.id
//                        )
//                    }.toMutableList(),
//
//                category.categories
//                    .takeLast(10)
//                    .map {
//                        Banner(
//                            title = it.name,
//                            onBoardingImageUrl = it.image,
//                            category = it.id
//                        )
//                    }.toMutableList()
//            )
//        } else {
//            mutableListOf(
//                category.categories.map {
//                    Banner(title = it.name, onBoardingImageUrl = it.image, category = it.id)
//                }.toMutableList()
//            )
//        }

        return Resource(status = Resource.Status.COMPLETED, data = category?.categories , exception = null,type = params.resourceType)
    }

}