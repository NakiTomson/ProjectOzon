package com.appmarketplace.ozon.presentation.factory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.Interfaces.RowType
import com.appmarketplace.ozon.presentation.adapters.*
import com.appmarketplace.ozon.presentation.data.BannerRowType
import com.appmarketplace.ozon.presentation.data.HistoryRowType
import com.appmarketplace.ozon.presentation.data.LiveRowType
import com.appmarketplace.ozon.presentation.pojo.OnProductItem
import com.appmarketplace.ozon.presentation.pojo.ResultHistoryData
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.items_history.view.*
import kotlinx.android.synthetic.main.row_type_banner.view.*
import kotlinx.android.synthetic.main.row_type_bottom_slogan.view.*
import kotlinx.android.synthetic.main.row_type_history.view.*
import kotlinx.android.synthetic.main.row_type_live.view.*
import kotlinx.android.synthetic.main.row_type_products.view.*
import kotlinx.android.synthetic.main.row_type_registration.view.*
import kotlinx.android.synthetic.main.row_type_top_slogan.view.*
import java.util.*


object ViewHolderFactory {


    class BannerViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        var bannerClickListener: BannerRowType.BannerListener? = null

        val banneerViewPager = itemView.onAdsViewPager
        var bannerIndicatorsContainer:LinearLayout?  = null


        fun bind(onBoardingAdapter: BannerAdapter){

            onBoardingAdapter.bannerClickListener = object : BannerRowType.BannerListener{
                override fun onClickBanner(imageUrl: String, imageOnboarding: RoundedImageView) {
                    bannerClickListener?.onClickBanner(imageUrl, imageOnboarding)
                }
            }
            bannerIndicatorsContainer = itemView.indicatorsContainerAds
            banneerViewPager.adapter  = onBoardingAdapter
        }
    }

    class CategoryViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val banneerViewPager = itemView.onAdsViewPager
        var bannerIndicatorsContainer:LinearLayout?= null


        fun bind(combinationProductsAdapter: CombinationProductsAdapter){
            bannerIndicatorsContainer = itemView.indicatorsContainerAds
            banneerViewPager.adapter  = combinationProductsAdapter
        }
    }

    class HistoryViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        lateinit var historClickListener: HistoryRowType.HistoryListener
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

                val itesm = listOf.map { it.historyUrl.toString() }

                listofImage[index].setOnClickListener {
                    listofImage[index].transitionName = itesm[index]
                    historClickListener.onClickHistory(itesm,index,listofImage[index])
                }
            }
        }
    }

    class LiveViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        var liveClickListener: LiveRowType.LiveListener? = null

        val liveStreamPager = itemView.onLiveStreemViewPager

        fun bind(liveItemAdapter: LiveItemAdapter){
            liveStreamPager.adapter = liveItemAdapter

            liveItemAdapter.liveClickListener = object : LiveRowType.LiveListener{
                override fun onClickLive(liveUrl: String) {
                    liveClickListener?.onClickLive(liveUrl)
                }

            }
        }
    }

    class RegistrationViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val financeButton = itemView.buttonGoRegistration

        fun bind(){

        }
    }


    class TopSloganOfferProduct(itemView: View):RecyclerView.ViewHolder(itemView){

        private val topStringOffer = itemView.productsTopOffer

        fun bind(slogan:String?){

            slogan?.let {
                topStringOffer.visibility = View.VISIBLE
                topStringOffer.text = slogan
            }

        }
    }

    class BottomSloganOfferProduct(itemView: View):RecyclerView.ViewHolder(itemView){
        private val bottomStringOffer  = itemView.textViewSloganOffer
        private val imageNextAll  = itemView.imageNextAll
        fun bind(slogan:String?){
            slogan?.let {
                bottomStringOffer.visibility = View.VISIBLE
                imageNextAll.visibility = View.VISIBLE
                bottomStringOffer.text = slogan
            }
        }
    }


    class ProductViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val productsRecyclerView = itemView.productsByOfferItemRecyclerView

        fun bind(listProducts: List<OnProductItem>,spain:Int){
            productsRecyclerView.layoutManager = GridLayoutManager(itemView.context, spain)
            val productAdapter =ProductItemAdapter()
            productAdapter.setData(listProducts)
            productsRecyclerView.adapter = productAdapter
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
                LiveViewHolder(liveTypeView,)
            }
            RowType.REGISTRATION_ROW_TYPE->{
                val registrationTypeView= LayoutInflater.from(parent.context).inflate(R.layout.row_type_registration,parent,false)
                RegistrationViewHolder(registrationTypeView)
            }

            RowType.PRODUCTS_ROW_TYPE->{
                val productTypeView= LayoutInflater.from(parent.context).inflate(R.layout.row_type_products,parent,false)
                ProductViewHolder(productTypeView)
            }

            RowType.PRODUCTS_SLOGAN_TOP_TYPE->{
                val sloganViewType= LayoutInflater.from(parent.context).inflate(R.layout.row_type_top_slogan,parent,false)
                TopSloganOfferProduct(sloganViewType)
            }
            RowType.PRODUCTS_SLOGAN_BOTTOM_TYPE->{
                val sloganViewType= LayoutInflater.from(parent.context).inflate(R.layout.row_type_bottom_slogan,parent,false)
                BottomSloganOfferProduct(sloganViewType)
            }
            else -> {
                throw UnknownFormatFlagsException("Not Faund Row Type")
            }

        }
    }

}