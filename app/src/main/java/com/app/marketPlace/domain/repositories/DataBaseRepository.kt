package com.app.marketPlace.domain.repositories

import androidx.annotation.WorkerThread
import com.app.marketPlace.data.db.dao.HintProductDao
import com.app.marketPlace.data.db.dao.InBasketDao
import com.app.marketPlace.data.db.dao.InFavoriteProductDao
import com.app.marketPlace.data.db.dao.UserDao
import com.app.marketPlace.data.db.models.BasketProductDb
import com.app.marketPlace.data.db.models.HintProductDb
import com.app.marketPlace.data.db.models.FavoriteProductDb
import com.app.marketPlace.data.db.models.UserDb
import com.app.marketPlace.domain.mappers.Mapper
import com.app.marketPlace.domain.models.ProductItem
import kotlinx.coroutines.flow.Flow

public class DataBaseRepository(
    private val favoriteProductDao: InFavoriteProductDao,
    private val basketDao: InBasketDao,
    private val userDao: UserDao,
    private val hintDao: HintProductDao
) {

    val productsInFavoriteIds: Flow<List<Int>?> = favoriteProductDao.getAllIds()
    val productsInBasketIds: Flow<List<Int>?> = basketDao.getAllIds()

    val baskets: Flow<List<BasketProductDb>?> = basketDao.getAllFlow()

    val favorite: Flow<List<FavoriteProductDb>?> = favoriteProductDao.getAllFlow()

    val hintProducts: Flow<List<HintProductDb>>? = hintDao.getAllFlow()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insertHint(request: String){
        hintDao.insert(Mapper.MapperToDb.mapHintUi(request))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun deleteHint(request: String){
        hintDao.delete(Mapper.MapperToDb.mapHintUi(request))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insertProductInFavorite(product: ProductItem) {
        favoriteProductDao.insert(Mapper.MapperToDb.mapFavoriteUi(product))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun deleteProductFromFavorite(product: ProductItem) {
        favoriteProductDao.delete(Mapper.MapperToDb.mapFavoriteUi(product))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteBasket(basket: ProductItem){
        basketDao.delete(Mapper.MapperToDb.mapBasketUi(basket))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertBasket(basket: ProductItem) {
        basketDao.insert(Mapper.MapperToDb.mapBasketUi(basket))
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertUser(user: UserDb){
        userDao.insert(user)
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getUser(login: String): UserDb {
        return userDao.getUser(login)
    }


}