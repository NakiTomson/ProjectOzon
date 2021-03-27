package com.app.marketPlace.domain.mappers

import com.app.marketPlace.domain.exception.NotMappedException
import com.app.marketPlace.domain.repositories.Params
import com.app.marketPlace.presentation.rowType.Resource

class MapperDef:IMapper {
    override fun <T> map(data: T?, params: Params): Resource<*> {
        return Resource(status = Resource.Status.COMPLETED, data = null ,
            exception = NotMappedException(data),
            type = params.resourceType)
    }
}