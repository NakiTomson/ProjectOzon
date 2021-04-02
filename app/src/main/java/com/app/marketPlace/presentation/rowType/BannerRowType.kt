package com.app.marketPlace.presentation.rowType

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.app.marketPlace.R
import com.app.marketPlace.presentation.interfaces.RowType
import com.app.marketPlace.presentation.adapters.BannerAdapter
import com.app.marketPlace.presentation.factory.ViewHolderFactory

data class BannerRowType(val adapter: BannerAdapter) : RowType{

    var setOnBannerClickListener: BannerListener? = null

    fun interface BannerListener {
        fun onClickBanner(imageUrl: String, imageView: View)
    }

    fun interface CompleteListener {
        fun onComplete()
    }

    override fun getItemViewType(): Int {
        return RowType.BANNER_ROW_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val holder: ViewHolderFactory.BannerViewHolder = viewHolder as  ViewHolderFactory.BannerViewHolder
        holder.bind(adapter)

        setupIndicator(holder.bannerIndicatorsContainer,adapter.itemCount,viewHolder.itemView.context)
        setIndicatorsContainer(0, holder.bannerIndicatorsContainer,viewHolder.itemView.context)

        holder.viewPager.adapter = adapter

        adapter.setBannerClickListener = BannerListener { imageUrl, view ->
            setOnBannerClickListener?.onClickBanner(imageUrl, view)
        }

        adapter.setCompleteListener = CompleteListener {
            holder.shimmer.stopShimmer()
            holder.shimmer.setShimmer(null)
        }

        holder.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setIndicatorsContainer(position, holder.bannerIndicatorsContainer, viewHolder.itemView.context)
                adapter.setBannerClickListener = BannerListener { _, imageOnBoarding ->
                    setOnBannerClickListener?.onClickBanner(
                        adapter.bannerList[position].imageUrl!!, imageOnBoarding)
                }
            }
        })
    }

    private fun setupIndicator(indicatorsContainer: LinearLayout, itemCount: Int, context: Context) {
        if (indicatorsContainer.children.count() > 0) return
        val indicators = arrayOfNulls<ImageView>(itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
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
        val childCount = indicatorsContainers.childCount

        for (i in 0 until childCount) {
            val imageView = indicatorsContainers.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        context.applicationContext,
                        R.drawable.indicator_active_background
                    )
                )
            } else {
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