package com.appmarketplace.ozon.presentation.di

import com.appmarketplace.ozon.presentation.activityes.ui.fragments.search_hint_history.SearchHintHistoryProductViewModel
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.home.HomeViewModel
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.products_list.ProductsListViewModel
import dagger.Component
import javax.inject.Singleton


@Component(modules = [AppModule::class, RepositoryModule::class, RestApiModule::class, ConverterModule::class,DataBaseModule::class])
@Singleton
interface AppComponent {


    fun inject(listProductsHome: HomeViewModel)
    fun inject(searchHintProductHomeViewModel: SearchHintHistoryProductViewModel)
    fun inject(searchHintProductHomeViewModel: ProductsListViewModel)

}