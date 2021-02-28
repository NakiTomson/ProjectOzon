package com.appmarketplace.ozon.presentation.di

import com.appmarketplace.ozon.presentation.activityes.MainViewModel
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.authorization.AuthorizationViewModel
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.basket.BasketViewModel
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.catalog.CatalogViewModel
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.detail.DetailsProductViewModel
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.favorite.FavoriteViewModel
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.searchHintHistory.SearchHintHistoryProductViewModel
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.home.HomeViewModel
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.makingOrder.MakingOrderViewModel
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.personalAccount.PersonalAccountViewModel
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.productsList.ProductsListViewModel
import dagger.Component
import javax.inject.Singleton


@Component(modules = [AppModule::class, RepositoryModule::class, RestApiModule::class,DataBaseModule::class])
@Singleton
interface AppComponent {


    fun inject(listProductsHome: HomeViewModel)
    fun inject(catalogViewModel: CatalogViewModel)
    fun inject(searchHintProductHomeViewModel: SearchHintHistoryProductViewModel)
    fun inject(searchHintProductHomeViewModel: ProductsListViewModel)
    fun inject(detailsProductViewModel: DetailsProductViewModel)
    fun inject(authorizationViewModel: AuthorizationViewModel)
    fun inject(personalAccountViewModel: PersonalAccountViewModel)
    fun inject(makingOrderViewModel: MakingOrderViewModel)
    fun inject(basketViewModel: BasketViewModel)
    fun inject(favoriteViewModel: FavoriteViewModel)
    fun inject(mainViewModel: MainViewModel)


}