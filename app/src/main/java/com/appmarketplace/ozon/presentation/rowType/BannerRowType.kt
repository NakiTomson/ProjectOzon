package com.appmarketplace.ozon.presentation.rowType

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.Interfaces.RowType
import com.appmarketplace.ozon.presentation.adapters.BannerAdapter
import com.appmarketplace.ozon.presentation.factory.ViewHolderFactory
import com.makeramen.roundedimageview.RoundedImageView

data class BannerRowType(val onBoardingAdapter: BannerAdapter) :RowType{



    var bannerClickListener: BannerListener? = null

    interface BannerListener {
        fun onClickBanner(imageUrl: String, imageOnboarding: RoundedImageView)
    }

    override fun getItemViewType(): Int {
        return RowType.BANNER_ROW_TYPE;
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val bunnerViewHolder: ViewHolderFactory.BannerViewHolder = viewHolder as  ViewHolderFactory.BannerViewHolder
        bunnerViewHolder.bind(onBoardingAdapter)

        setupIndicator(bunnerViewHolder.bannerIndicatorsContainer!!,onBoardingAdapter.itemCount,viewHolder.itemView.context)
        setIndicatorsContainer(0, bunnerViewHolder.bannerIndicatorsContainer!!,viewHolder.itemView.context)

        bunnerViewHolder.banneerViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setIndicatorsContainer(position, bunnerViewHolder.bannerIndicatorsContainer!!, viewHolder.itemView.context)

                viewHolder.bannerClickListener = object :BannerListener{
                    override fun onClickBanner(imageUrl: String, imageOnboarding: RoundedImageView) {
                        bannerClickListener?.onClickBanner(
                            onBoardingAdapter.onboardingItems[position].onBoardingImageUrl,
                            imageOnboarding
                        )
                    }
                }
            }
        })
    }


    fun setupIndicator(indicatorsContainer: LinearLayout, itemCount: Int, context: Context) {

        val indicators = arrayOfNulls<ImageView>(itemCount)
        val layoutParams: LinearLayout.LayoutParams =

            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

        layoutParams.setMargins(8,0,8,0)
        for ( i in indicators.indices){
            indicators[i] = ImageView(context)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        context.applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
                it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }
    }
    fun setIndicatorsContainer(position: Int, indicatorsContainers: LinearLayout, context: Context){

        val childCount  = indicatorsContainers.childCount

        for (i in 0 until  childCount){
            val imageView = indicatorsContainers.getChildAt(i) as ImageView
            if (i == position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                    context.applicationContext,
                    R.drawable.indicator_active_background
                )
                )
            }else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        context.applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }
    }
}