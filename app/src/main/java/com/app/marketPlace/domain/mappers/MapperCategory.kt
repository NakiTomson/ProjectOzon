package com.app.marketPlace.domain.mappers

import com.app.marketPlace.data.remote.models.*
import com.app.marketPlace.data.utils.ConstantsApp
import com.app.marketPlace.domain.exception.NotMappedException
import com.app.marketPlace.domain.repositories.Params
import com.app.marketPlace.presentation.rowType.Resource

class MapperCategory : IMapper {

    override fun <T> map(data: T?, params: Params): Resource<*> {

        if (data !is CategoriesProduct?){
            return Resource(status = Resource.Status.COMPLETED, data = null ,
                exception = NotMappedException(data),
                type = params.resourceType)
        }

        if (data == null || data.categories.isNullOrEmpty()){
            return Resource(status = Resource.Status.EMPTY, data = null ,
                exception = null,
                type = params.resourceType)
        }


        val listCategories = if (data.categories.size <= 20) {
            for ((index, i) in (0 until data.categories!!.size).withIndex()) {
                data.categories[i].image = ConstantsApp.getImage(index)
            }

            data.categories
                .take(20)
                .map { Categories(name = it.name, image = it.image, id = it.id)}
        } else {
            data.categories
        }
        return Resource(status = Resource.Status.COMPLETED, data = listCategories , exception = null,type = params.resourceType)
    }
}