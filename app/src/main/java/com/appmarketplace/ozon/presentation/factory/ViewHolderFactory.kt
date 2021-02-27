package com.appmarketplace.ozon.presentation.factory

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.Interfaces.RowType
import com.appmarketplace.ozon.presentation.adapters.*
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem
import com.appmarketplace.ozon.domain.modelsUI.ResultHistoryData
import com.appmarketplace.ozon.presentation.rowType.*
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.items_history.view.*
import kotlinx.android.synthetic.main.row_type_banner.view.*
import kotlinx.android.synthetic.main.row_type_bottom_slogan.view.*
import kotlinx.android.synthetic.main.row_type_bottom_slogan.view.imageNextAll
import kotlinx.android.synthetic.main.row_type_complex_slogan.view.*
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

        var clickOnCategoryItem: CategoryRowType.ClickCategoryListener? = null

        val banneerViewPager = itemView.onAdsViewPager
        var bannerIndicatorsContainer:LinearLayout?= null


        fun bind(combinationProductsAdapter: CombinationProductsAdapter){
            bannerIndicatorsContainer = itemView.indicatorsContainerAds
            banneerViewPager.adapter  = combinationProductsAdapter

            combinationProductsAdapter.clickOnCategoryItem =object : CategoryRowType.ClickCategoryListener{
                override fun onClickItem(data: String) {
                    clickOnCategoryItem?.onClickItem(data)
                }
            }
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
                Picasso.with(itemView.context)
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
                if (it.isNotEmpty()) {
                    bottomStringOffer.visibility = View.VISIBLE
                    imageNextAll.visibility = View.VISIBLE
                    bottomStringOffer.text = slogan
                }
            }
        }
    }

    class ComplexSloganOfferProduct(itemView: View):RecyclerView.ViewHolder(itemView){

        private val topText = itemView.topTextMaybeBestzeller
        private val generalText = itemView.generalText
        private val childTextView = itemView.childGeneralTextView
        private val iconGeneral = itemView.iconGeneralText
        private val imageNext = itemView.imageNext

        fun bind(item: ComplexSloganRowType.Item){


            when(item){
                is ComplexSloganRowType.Item.setBestseller ->{
                    topText.visibility = View.VISIBLE
                    topText.text = "Бестселлер"
                    generalText.text = item.company
                    generalText.visibility = View.VISIBLE
                }

                is ComplexSloganRowType.Item.setPrice ->{
                    generalText.text = item.actualPrice
                    generalText.visibility = View.VISIBLE

                    childTextView.text = item.oldPrice
                    childTextView.visibility = View.VISIBLE

                    imageNext.visibility = View.VISIBLE
                    imageNext.setOnClickListener {
                        Toast.makeText(itemView.context,"Next",Toast.LENGTH_SHORT).show()
                    }
                }
                is ComplexSloganRowType.Item.setSimpleOffer->{
                    generalText.text = item.offer
                    generalText.visibility = View.VISIBLE
                    generalText.setPadding(0,0,0,20)
                    generalText.textSize = 16f
                    generalText.setTypeface(generalText.typeface, Typeface.NORMAL)
                    imageNext.visibility = View.VISIBLE
                    imageNext.setOnClickListener {
                        Toast.makeText(itemView.context,"Next",Toast.LENGTH_SHORT).show()
                    }
                }
            }




//            iconGeneral.setImageResource(R.drawable.icon_app_ozon)
//            iconGeneral.visibility = View.VISIBLE


        }
    }

    class ProductViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val productsRecyclerView = itemView.productsByOfferItemRecyclerView

        var setClickListenerProduct: ProductsRowType.OnClickProduct? = null

        var setClickHeartProduct: ProductsRowType.OnClickHeart? = null

        fun bind(listProducts: List<OnProductItem>,spain:Int){
            productsRecyclerView.layoutManager = GridLayoutManager(itemView.context, spain)
            val productAdapter = ProductItemAdapter()
            productAdapter.setData(listProducts)
            productsRecyclerView.adapter = productAdapter

            productAdapter.setClickListenerProduct = object : ProductsRowType.OnClickProduct{

                override fun clickProduct(product: OnProductItem, imageView: ImageView) {
                    setClickListenerProduct?.clickProduct(product,imageView)
                }
            }

            productAdapter.setClickHeartProduct = object:ProductsRowType.OnClickHeart{
                override fun onClickHeart(productsItem: OnProductItem) {
                    setClickHeartProduct?.onClickHeart(productsItem)
                }

            }
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
            RowType.PRODUCTS_SLOGAN_COMPLEX_TYPE->{
                val sloganViewType= LayoutInflater.from(parent.context).inflate(R.layout.row_type_complex_slogan,parent,false)
                ComplexSloganOfferProduct(sloganViewType)
            }
            else -> {
                throw UnknownFormatFlagsException("Not Faund Row Type")
            }

        }
    }

}