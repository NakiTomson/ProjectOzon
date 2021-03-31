package com.app.marketPlace.presentation.factory

import android.app.Activity
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat.postponeEnterTransition
import androidx.core.app.ActivityCompat.startPostponedEnterTransition
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.app.marketPlace.R
import com.app.marketPlace.data.remote.models.Stories
import com.app.marketPlace.domain.exception.NotFoundRealizationException
import com.app.marketPlace.presentation.interfaces.RowType
import com.app.marketPlace.presentation.adapters.*
import com.app.marketPlace.domain.models.ProductItem
import com.app.marketPlace.presentation.interfaces.ProductRowType
import com.app.marketPlace.presentation.rowType.*
import com.facebook.shimmer.ShimmerFrameLayout
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

    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val shimmer: ShimmerFrameLayout = itemView.shimmerLayout
        val viewPager: ViewPager2 = itemView.onAdsViewPager
        var bannerIndicatorsContainer: LinearLayout = itemView.indicatorsContainerAds

        fun bind(bannerAdapter: BannerAdapter) {

        }
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var clickOnCategoryItem: CategoryRowType.ClickCategoryListener? = null
        val bannekerViewPager: ViewPager2 = itemView.onAdsViewPager
        var bannerIndicatorsContainer: LinearLayout? = null
        val shimmer: ShimmerFrameLayout = itemView.shimmerLayout

        fun bind(combinationProductsAdapter: CombinationAdapter) {
            bannerIndicatorsContainer = itemView.indicatorsContainerAds
            bannekerViewPager.adapter = combinationProductsAdapter

            combinationProductsAdapter.setOnCategoryClickListener = CategoryRowType.ClickCategoryListener { data ->
                clickOnCategoryItem?.onClickItem(data)
            }
            combinationProductsAdapter.setCompleteListener = BannerRowType.CompleteListener {
                shimmer.stopShimmer()
                shimmer.setShimmer(null)
            }
        }
    }

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var setHistoryClickListener: HistoryRowType.HistoryListener
        val historyButton: Button = itemView.historyItemButton
        val financeButton: Button = itemView.financeItemButton
        val container: LinearLayout = itemView.containerImages

        val shimmer: ShimmerFrameLayout = itemView.shimmerLayoutStories

        fun bind(stories: Stories) {

        }

    }

    class LiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var serLiveClickListener: LiveRowType.LiveListener? = null
        val liveStreamPager: ViewPager2 = itemView.onLiveStreamViewPager


        fun bind(liveItemAdapter: LiveStreamAdapter) {
            liveStreamPager.adapter = liveItemAdapter
            liveItemAdapter.setOnLiveClickListener = LiveRowType.LiveListener { liveUrl, view ->
                serLiveClickListener?.onClick(liveUrl, view)
            }
        }
    }

    class RegistrationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val financeButton: Button = itemView.buttonGoRegistration

        fun bind() {}
    }


    class TopSloganOfferProduct(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val topStringOffer = itemView.productsTopOffer

        fun bind(slogan: String?) {
            slogan?.let {
                topStringOffer.visibility = View.VISIBLE
                topStringOffer.text = slogan
            }
        }
    }

    class BottomSloganOfferProduct(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bottomStringOffer = itemView.textViewSloganOffer
        private val imageNextAll = itemView.imageNextAll
        fun bind(slogan: String?) {
            slogan?.let {
                if (it.isNotEmpty()) {
                    bottomStringOffer.visibility = View.VISIBLE
                    imageNextAll.visibility = View.VISIBLE
                    bottomStringOffer.text = slogan
                }
            }
        }
    }

    class ComplexSloganOfferProduct(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val topText = itemView.topTextMaybeBestseller
        val generalText = itemView.generalText
        val childTextView = itemView.childGeneralTextView
        val iconGeneral = itemView.iconGeneralText
        val imageNext = itemView.imageNextMoxyData

        fun bind(item: ComplexSloganRowType.Item) {

        }
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal val productsRecyclerView = itemView.productsByOfferItemRecyclerView

        private val animation: Animation =
            AnimationUtils.loadAnimation(itemView.context, R.anim.appearances_out)

        private val controller = LayoutAnimationController(animation)

        fun bind(listProducts: List<ProductItem>, productItemAdapter: ProductAdapter) {
            productItemAdapter.setData(listProducts)
            productsRecyclerView.layoutAnimation = controller
        }
    }


    class ProductHorizontalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal val productsRecyclerView: RecyclerView = itemView.productsByOfferItemRecyclerView

        private val animation: Animation =
            AnimationUtils.loadAnimation(itemView.context, R.anim.appearances_out)

        private val controller = LayoutAnimationController(animation)

        fun bind(listProducts: List<ProductItem>,productItemAdapter: ProductHorizontalAdapter) {
            productItemAdapter.setData(listProducts)
            productsRecyclerView.layoutAnimation = controller
        }
    }

    @JvmStatic
    fun create(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            RowType.BANNER_ROW_TYPE -> {
                val bannerTypeView = LayoutInflater.from(parent.context).inflate(R.layout.row_type_banner, parent, false)
                BannerViewHolder(bannerTypeView)
            }
            RowType.CATEGORY_ROW_TYPE -> {
                val categoryTypeView = LayoutInflater.from(parent.context).inflate(R.layout.row_type_banner, parent, false)
                CategoryViewHolder(categoryTypeView)
            }
            RowType.HISTORY_ROW_TYPE -> {
                val historyTypeView = LayoutInflater.from(parent.context).inflate(R.layout.row_type_history, parent, false)
                HistoryViewHolder(historyTypeView)
            }
            RowType.LIVE_ROW_TYPE -> {
                val liveTypeView = LayoutInflater.from(parent.context).inflate(R.layout.row_type_live, parent, false)
                LiveViewHolder(liveTypeView)
            }
            RowType.REGISTRATION_ROW_TYPE -> {
                val registrationTypeView = LayoutInflater.from(parent.context).inflate(R.layout.row_type_registration, parent, false)
                RegistrationViewHolder(registrationTypeView)
            }

            RowType.PRODUCTS_ROW_TYPE -> {
                val productTypeView = LayoutInflater.from(parent.context).inflate(R.layout.row_type_products, parent, false)
                ProductViewHolder(productTypeView)
            }
            RowType.PRODUCTS_HORIZONTAL_ROW_TYPE -> {
                val productTypeView = LayoutInflater.from(parent.context).inflate(R.layout.row_type_products, parent, false)
                ProductHorizontalViewHolder(productTypeView)
            }

            RowType.PRODUCTS_SLOGAN_TOP_TYPE -> {
                val sloganViewType = LayoutInflater.from(parent.context).inflate(R.layout.row_type_top_slogan, parent, false)
                TopSloganOfferProduct(sloganViewType)
            }
            RowType.PRODUCTS_SLOGAN_BOTTOM_TYPE -> {
                val sloganViewType = LayoutInflater.from(parent.context).inflate(R.layout.row_type_bottom_slogan, parent, false)
                BottomSloganOfferProduct(sloganViewType)
            }
            RowType.PRODUCTS_SLOGAN_COMPLEX_TYPE -> {
                val sloganViewType = LayoutInflater.from(parent.context).inflate(R.layout.row_type_complex_slogan, parent, false)
                ComplexSloganOfferProduct(sloganViewType)
            }
            else -> {
                throw NotFoundRealizationException("Not Found Row Type")
            }
        }
    }
}