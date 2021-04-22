package com.app.marketPlace.domain.mappers

import com.app.marketPlace.domain.models.Params
import com.app.marketPlace.presentation.factory.Resource

interface Mapper {

    fun<T> map(data: T?, params: Params): Resource<*>

//    fun<Type,TypeToMap> map2(data: Type?, params: Params): Resource<TypeToMap>

}