package com.appmarketplace.ozon.presentation.di

import com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.home_fragment.product_home_fragments.ListProductsHomeViewModel
import dagger.Component
import javax.inject.Singleton


@Component(modules = [AppModule::class, RepositoryModule::class, RestApiModule::class, ConverterModule::class])
@Singleton
interface AppComponent {


    fun inject(listProductsHome: ListProductsHomeViewModel)

}