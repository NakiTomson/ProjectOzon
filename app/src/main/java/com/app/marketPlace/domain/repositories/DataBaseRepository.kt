package com.app.marketPlace.domain.repositories

import androidx.annotation.WorkerThread
import com.app.marketPlace.data.db.InBasketDao
import com.app.marketPlace.data.db.FavoriteProductDao
import com.app.marketPlace.data.db.HintProductDao
import com.app.marketPlace.data.db.UserDao
import com.app.marketPlace.data.remote.modelsDB.BasketProductDb
import com.app.marketPlace.data.remote.modelsDB.HintProductDB
import com.app.marketPlace.data.remote.modelsDB.UserDB
import com.app.marketPlace.domain.mappers.Mapper
import com.app.marketPlace.domain.modelsUI.OnProductItem
import kotlinx.coroutines.flow.Flow

class DataBaseRepository(
    private val favoriteProductDao: FavoriteProductDao,
    private val basketDao: InBasketDao,
    private val userDao: UserDao,
    private val hintDao: HintProductDao,
    private val mapper: Mapper
) {

    val productsInFavoriteIds: Flow<List<Int>?> = favoriteProductDao.getAllIds()

    val productsInBasketIds: Flow<List<Int>?> = basketDao.getAllIds()

    val baskets: Flow<List<BasketProductDb>?> = basketDao.getAllFlow()

    val favorite: Flow<List<BasketProductDb>?> = favoriteProductDao.getAllFlow()


    val hintProducts: Flow<List<HintProductDB>>? = hintDao.getAllFlow()



    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insertHint(request: String){
        hintDao.insert(mapper.mapHintUiToDb(request))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun deleteHint(request: String){
        hintDao.delete(mapper.mapHintUiToDb(request))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insertProductInFavorite(product: OnProductItem) {
        favoriteProductDao.insert(mapper.mapProductUiToDb(product))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun deleteProductFromFavorite(product: OnProductItem) {
        favoriteProductDao.delete(mapper.mapProductUiToDb(product))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteBasket(basket: OnProductItem){
        basketDao.delete(mapper.mapBasketUiToDb(basket))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertBasket(basket: OnProductItem) {
        basketDao.insert(mapper.mapBasketUiToDb(basket))
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertUser(user: UserDB){
        userDao.insert(user)
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getUser(login: String): UserDB {
        return userDao.getUser(login)
    }


}