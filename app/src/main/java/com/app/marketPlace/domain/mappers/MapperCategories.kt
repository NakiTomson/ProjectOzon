package com.app.marketPlace.domain.mappers

import com.app.marketPlace.data.remote.models.*
import com.app.marketPlace.data.utils.Constants
import com.app.marketPlace.domain.exception.NotFoundMappedException
import com.app.marketPlace.domain.models.Params
import com.app.marketPlace.presentation.factory.Resource

class MapperCategories : Mapper {

    override fun <T> map(data: T?, params: Params): Resource<*> {

        if (data !is CategoriesProduct?){
            return Resource(status = Resource.Status.COMPLETED, data = null ,
                exception = NotFoundMappedException(data))
        }

        if (data == null || data.categories.isNullOrEmpty()){
            return Resource(status = Resource.Status.EMPTY, data = null ,
                exception = null)
        }


        val listCategories = if (data.categories.size <= 20) {
            for ((index, i) in (0 until data.categories!!.size).withIndex()) {
                data.categories[i].image = Constants.getImage(index)
            }

            data.categories
                .take(20)
                .map { Categories(name = it.name, image = it.image, id = it.id)}
        } else {
            data.categories
        }
        return Resource(status = Resource.Status.COMPLETED, data = listCategories , exception = null)
    }
}