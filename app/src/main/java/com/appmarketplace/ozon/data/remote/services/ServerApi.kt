package com.appmarketplace.ozon.data.remote.services

import com.appmarketplace.ozon.data.remote.models.ProductsModel
import com.appmarketplace.ozon.presentation.pojo.GeneralCategory
import com.appmarketplace.ozon.presentation.pojo.OnHistoryItem
import com.appmarketplace.ozon.presentation.pojo.OnLiveItem
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


const val APIKEY1 ="d4iN6AkD53RDasGanCbqDO0U"

const val APIKEY2 ="wBuDrNSy1QpcZQGh1Hp7BOfp"
const val APIKEY3 ="TcFNyzH0fkpgppZVvZEA9MY6"
const val APIKEY4 ="ipabDM127uDkkdQ2U4EEJ2Bg"

const val APIKEY5 ="LRa7FGrgdpYyviTjICLhVBdC"

interface ServerApi{


    interface MarketPlaceService  {

        @GET("/v1/categories?format=json&show=name,id&apiKey=$APIKEY1")
        fun getCategoryProducts(
            @Query("pageSize") pageSize: String,
            @Query("page") page: String,
        ): Deferred<GeneralCategory?>


        @GET("/v1/products((categoryPath.id={PathId}))?format=json")
        fun getProductsByCategory(
                @Path("PathId") pathId:String,
                @Query("pageSize") pageSize: String,
                @Query("page") page: String,
                @Query("apiKey") apykey: String
        ): Deferred<ProductsModel>


        @GET("/v1/products((search={keyword}))?format=json&apiKey=$APIKEY1")


        fun getProductsBySearch(
            @Path("keyword") keyword:String,
            @Query("pageSize") pageSize: String,
            @Query("page") page: String
        ): Deferred<ProductsModel>



        @GET("/s/0w71n1rc6v85s0r/historyData.json?dl=1")
        fun getHistorys(): Deferred<OnHistoryItem>


        @GET("/s/xgal8vjau426dka/liveData.json?dl=1")
        fun getLives(): Deferred<OnLiveItem>
    }
}
