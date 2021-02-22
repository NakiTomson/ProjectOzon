package com.appmarketplace.ozon.domain.mappers

import com.appmarketplace.ozon.presentation.data.Resource
import com.appmarketplace.ozon.presentation.pojo.GeneralCategory
import com.appmarketplace.ozon.presentation.pojo.OnBoardingItem


 interface MapperUI <T,M> {
     fun map(list: M): Resource<T>
 }


