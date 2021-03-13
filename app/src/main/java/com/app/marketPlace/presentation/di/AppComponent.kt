package com.app.marketPlace.presentation.di

import com.app.marketPlace.presentation.activities.MainActivity
import com.app.marketPlace.presentation.activities.ui.fragments.authorization.SignUpFragment
import com.app.marketPlace.presentation.activities.ui.fragments.basket.BasketFragment
import com.app.marketPlace.presentation.activities.ui.fragments.basket.BasketViewModel
import com.app.marketPlace.presentation.activities.ui.fragments.catalog.CatalogViewModel
import com.app.marketPlace.presentation.activities.ui.fragments.detail.DetailsProductFragment
import com.app.marketPlace.presentation.activities.ui.fragments.detail.DetailsProductViewModel
import com.app.marketPlace.presentation.activities.ui.fragments.favorite.FavoriteFragment
import com.app.marketPlace.presentation.activities.ui.fragments.home.HomeFragment

import com.app.marketPlace.presentation.activities.ui.fragments.home.HomeViewModel
import com.app.marketPlace.presentation.activities.ui.fragments.makingOrder.MakingOrderFragment
import com.app.marketPlace.presentation.activities.ui.fragments.personalAccount.PersonalAccount
import com.app.marketPlace.presentation.activities.ui.fragments.productsList.ProductsListFragment
import com.app.marketPlace.presentation.activities.ui.fragments.productsList.ProductsListViewModel
import com.app.marketPlace.presentation.activities.ui.fragments.searchHintHistory.SearchHintHistoryProductFragment
import dagger.Component
import javax.inject.Singleton


@Component(modules = [AppModule::class, RepositoryModule::class, RestApiModule::class,DataBaseModule::class])
@Singleton
interface AppComponent {

    fun inject(listProductsHome: HomeViewModel)
    fun inject(catalogViewModel: CatalogViewModel)
    fun inject(detailsProductViewModel: DetailsProductViewModel)
    fun inject(basketViewModel: BasketViewModel)
    fun inject(mainActivity: MainActivity)
    fun inject(productsListFragment: ProductsListFragment)
    fun inject(personalAccount: PersonalAccount)
    fun inject(homeFragment: HomeFragment)
    fun inject(basketFragment: BasketFragment)
    fun inject(searchHintHistoryProductFragment: SearchHintHistoryProductFragment)
    fun inject(makingOrderFragment: MakingOrderFragment)
    fun inject(productsListViewModel: ProductsListViewModel)
    fun inject(favoriteFragment: FavoriteFragment)
    fun inject(signUpFragment: SignUpFragment)
    fun inject(detailsProductFragment: DetailsProductFragment)

}