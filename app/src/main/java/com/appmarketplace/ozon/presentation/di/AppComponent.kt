package com.appmarketplace.ozon.presentation.di

import com.appmarketplace.ozon.presentation.activityes.MainActivity
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.authorization.SignUpFragment
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.basket.BasketFragment
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.basket.BasketViewModel
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.catalog.CatalogViewModel
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.detail.DetailsProductFragment
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.detail.DetailsProductViewModel
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.favorite.FavoriteFragment
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.home.HomeFragment

import com.appmarketplace.ozon.presentation.activityes.ui.fragments.home.HomeViewModel
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.makingOrder.MakingOrderFragment
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.personalAccount.PersonalAccount
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.productsList.ProductsListFragment
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.productsList.ProductsListViewModel
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.searchHintHistory.SearchHintHistoryProductFragment
import dagger.Component
import javax.inject.Singleton


@Component(modules = [AppModule::class, RepositoryModule::class, RestApiModule::class,DataBaseModule::class])
@Singleton
interface AppComponent {


    fun inject(listProductsHome: HomeViewModel)
    fun inject(catalogViewModel: CatalogViewModel)
    fun inject(searchHintProductHomeViewModel: ProductsListViewModel)
    fun inject(detailsProductViewModel: DetailsProductViewModel)
    fun inject(basketViewModel: BasketViewModel)
    fun inject(mainActivity: MainActivity)
    fun inject(productsListFragment: ProductsListFragment)
    fun inject(personalAccount: PersonalAccount)
    fun inject(homeFragment: HomeFragment)
    fun inject(basketFragment: BasketFragment)
    fun inject(searchHintHistoryProductFragment: SearchHintHistoryProductFragment)
    fun inject(makingOrderFragment: MakingOrderFragment)
    fun inject(favoriteFragment: FavoriteFragment)
    fun inject(signUpFragment: SignUpFragment)
    fun inject(detailsProductFragment: DetailsProductFragment)


}