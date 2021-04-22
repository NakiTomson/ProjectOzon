package com.app.marketPlace.domain.mappers

import com.app.marketPlace.domain.exception.NotFoundMappedException
import com.app.marketPlace.domain.models.Params
import com.app.marketPlace.presentation.factory.Resource

class MapperDefault:Mapper {

    //It will throws NotFoundMappedException
    override fun <T> map(data: T?, params: Params): Resource<*> {
        return Resource(status = Resource.Status.COMPLETED, data = null ,
            exception = NotFoundMappedException(data))
    }
}