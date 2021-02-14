package com.appmarketplace.ozon.presentation.di

import com.appmarketplace.ozon.domain.converters.GeneralCategoryConverter
import dagger.Module
import dagger.Provides


@Module
class ConverterModule  {


    @Provides
    fun provideConverterCategoryProducts(): GeneralCategoryConverter {
        return GeneralCategoryConverter()
    }
}