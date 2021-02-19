package com.appmarketplace.ozon.presentation.factory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.Interfaces.RowType
import com.appmarketplace.ozon.presentation.adapters.*
import com.appmarketplace.ozon.presentation.pojo.ResultHistoryData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_boarding_container.view.*
import kotlinx.android.synthetic.main.items_history.view.*
import kotlinx.android.synthetic.main.row_type_banner.view.*
import kotlinx.android.synthetic.main.row_type_history.view.*
import kotlinx.android.synthetic.main.row_type_live.view.*
import kotlinx.android.synthetic.main.row_type_products.view.*
import kotlinx.android.synthetic.main.row_type_registration.view.*
import java.util.*


object ViewHolderFactory {


    class BannerViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val banneerViewPager = itemView.onAdsViewPager
        var bannerIndicatorsContainer:LinearLayout?  = null

        var childCount:Int?=null


        fun bind(onBoardingAdapter: BannerAdapter){

            bannerIndicatorsContainer = itemView.indicatorsContainerAds
            childCount = bannerIndicatorsContainer?.childCount!!
            banneerViewPager.adapter  = onBoardingAdapter
        }
    }

    class CategoryViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val banneerViewPager = itemView.onAdsViewPager
        val bannerIndicatorsContainer = itemView.indicatorsContainerAds


        fun bind(combinationProductsAdapter: CombinationProductsAdapter){
            banneerViewPager.adapter  = combinationProductsAdapter
        }
    }

    class HistoryViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val historyButton = itemView.hostoryItemButton
        val financeButton = itemView.hostoryItemFinancle


        val listofImage = listOf<ImageView>(
                itemView.oneHistoryItem,
                itemView.twoHistoryItem,
                itemView.threeHistoryItem,
                itemView.foureHistoryItem,
                itemView.fiveHistoryItem,
                itemView.sixHistoryItem
        )

        fun bind(listOf: List<ResultHistoryData>) {

            for ((index,item) in (listOf).withIndex()){
                Picasso.get()
                    .load(item.historyUrl)
                    .noFade()
                    .placeholder(R.drawable.one_history)
                    .into(listofImage[index])
            }

        }
    }

    class LiveViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val textLive = itemView.textOzonLive
        val liveStreamPager = itemView.onLiveStreemViewPager


        fun bind(liveItemAdapter: LiveItemAdapter){
            liveStreamPager.adapter = liveItemAdapter
        }
    }

    class RegistrationViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val financeButton = itemView.buttonGoRegistration

        fun bind(){

        }
    }

    class ProductViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val productsByOffer = itemView.productsByOfferItemRecyclerView

        fun bind(adapterProducts: ContainerProductsAdapter){
            productsByOffer.layoutManager = LinearLayoutManager(itemView.context)
            productsByOffer.adapter = adapterProducts
        }
    }

    @JvmStatic
    fun create(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            RowType.BANNER_ROW_TYPE->{
                val bannerTypeView= LayoutInflater.from(parent.context).inflate(R.layout.row_type_banner,parent,false)
                BannerViewHolder(bannerTypeView)
            }
            RowType.CATEGORY_ROW_TYPE->{
                val categoryTypeView= LayoutInflater.from(parent.context).inflate(R.layout.row_type_banner,parent,false)
                CategoryViewHolder(categoryTypeView)
            }
            RowType.HISTORY_ROW_TYPE->{
                val historyTypeView= LayoutInflater.from(parent.context).inflate(R.layout.row_type_history,parent,false)
                HistoryViewHolder(historyTypeView)
            }
            RowType.LIVE_ROW_TYPE->{
                val liveTypeView= LayoutInflater.from(parent.context).inflate(R.layout.row_type_live,parent,false)
                LiveViewHolder(liveTypeView)
            }
            RowType.REGISTRATION_ROW_TYPE->{
                val registrationTypeView= LayoutInflater.from(parent.context).inflate(R.layout.row_type_registration,parent,false)
                RegistrationViewHolder(registrationTypeView)
            }
            RowType.PRODUCTS_ROW_TYPE->{
                val productTypeView= LayoutInflater.from(parent.context).inflate(R.layout.row_type_products,parent,false)
                ProductViewHolder(productTypeView)
            }

            else -> {
                throw UnknownFormatFlagsException("Not Faund Row Type")
            }

        }
    }

}