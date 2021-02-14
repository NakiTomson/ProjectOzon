package com.appmarketplace.ozon.domain.repositories

abstract class BaseRepository<Params,Result> {

    abstract suspend fun loadDataCategoryProduct(params: Params): HomeRepositoryImpl.Results

}