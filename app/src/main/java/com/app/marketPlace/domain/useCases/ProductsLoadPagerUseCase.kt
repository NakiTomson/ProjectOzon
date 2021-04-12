package com.app.marketPlace.domain.useCases

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.app.marketPlace.data.remote.services.BestBuyService
import com.app.marketPlace.domain.models.Params
import com.app.marketPlace.domain.models.Product
import com.app.marketPlace.presentation.paging.ProductPagingSource
import javax.inject.Inject

class ProductsLoadPagerUseCase @Inject constructor(private val marketPlaceApi: BestBuyService) {

    fun loadProductsPager(params: Params, pagingConfig: PagingConfig = getDefaultPageConfig()): LiveData<PagingData<Product>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { ProductPagingSource(marketPlaceApi,params) }
        ).liveData
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = ProductPagingSource.DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }
}