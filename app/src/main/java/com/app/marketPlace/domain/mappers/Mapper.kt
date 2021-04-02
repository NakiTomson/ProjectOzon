package com.app.marketPlace.domain.mappers

import com.app.marketPlace.domain.repositories.Params
import com.app.marketPlace.presentation.factory.Resource

interface Mapper {

    fun<T> map(data: T?, params: Params): Resource<*>

}