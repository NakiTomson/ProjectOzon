package com.appmarketplace.ozon.presentation.di

import com.appmarketplace.ozon.presentation.activityes.ui.fragments.detail.DetailsProductViewModel
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.searchHintHistory.SearchHintHistoryProductViewModel
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.home.HomeViewModel
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.productsList.ProductsListViewModel
import dagger.Component
import javax.inject.Singleton


@Component(modules = [AppModule::class, RepositoryModule::class, RestApiModule::class,DataBaseModule::class])
@Singleton
interface AppComponent {


    fun inject(listProductsHome: HomeViewModel)
    fun inject(searchHintProductHomeViewModel: SearchHintHistoryProductViewModel)
    fun inject(searchHintProductHomeViewModel: ProductsListViewModel)
    fun inject(detailsProductViewModel: DetailsProductViewModel)

}