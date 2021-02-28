package com.appmarketplace.ozon.domain.mappers

import com.appmarketplace.ozon.presentation.rowType.Resource


interface MapperUI<T, M> {
    fun map(list: M): Resource<T>
}


