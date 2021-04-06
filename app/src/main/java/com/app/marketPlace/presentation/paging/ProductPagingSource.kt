package com.app.marketPlace.presentation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.marketPlace.data.remote.services.BestBuyService
import com.app.marketPlace.domain.mappers.MapperProducts
import com.app.marketPlace.domain.models.Product
import com.app.marketPlace.domain.repositories.Params
import com.app.marketPlace.presentation.activities.checkingForErrors
import com.app.marketPlace.presentation.factory.Resource
import retrofit2.HttpException
import java.io.IOException

class ProductPagingSource(private val bestBuyApiService: BestBuyService, private val param: Params) :
    PagingSource<Int, Product> () {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {

        return try {

            val page = params.key ?: param.page.toInt()

            val response =   bestBuyApiService
                .getProductsAsync(
                    param.attributes,
                    param.pathId,
                    param.pageSize,
                    page.toString(),
                    param.apiKey
                )


            val resource = checkingForErrors(MapperProducts().mapProduct(response.await().products, param))

            LoadResult.Page(
                data =  resource.data?.list ?: listOf(),
                prevKey = if (page == param.page.toInt()) null else page - 1,
                nextKey = if (resource.data?.list?.isNullOrEmpty() == null) null else page + 1
            )

        } catch (exception: IOException) {
            checkingForErrors(Resource(Resource.Status.ERROR,null,exception))
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            checkingForErrors(Resource(Resource.Status.ERROR,null,exception))
            return LoadResult.Error(exception)
        }
    }


    companion object{
        const val DEFAULT_PAGE_SIZE = 40
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
//        return state.anchorPosition
    }
}