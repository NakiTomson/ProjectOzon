package com.app.marketPlace.presentation.rowType

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.app.marketPlace.presentation.interfaces.RowType
import com.app.marketPlace.presentation.adapters.LiveStreamAdapter
import com.app.marketPlace.presentation.factory.ViewHolderFactory
import kotlin.math.abs

data class LiveRowType(val adapter: LiveStreamAdapter) :RowType {

    var setOnLiveStreamClickListener: LiveListener? = null

    fun interface LiveListener {
        fun onClick(liveUrl: String,view:ImageView)
    }

    override fun getItemViewType(): Int {
        return RowType.LIVE_ROW_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val holder = viewHolder as ViewHolderFactory.LiveViewHolder
        holder.bind(adapter)
        adapter.setOnLiveClickListener = LiveListener { liveUrl, view ->
            setOnLiveStreamClickListener?.onClick(liveUrl, view)
        }
        setTransformations(holder)
    }

    private fun setTransformations(holder: ViewHolderFactory.LiveViewHolder) {
        holder.liveStreamPager.clipToPadding = false
        holder.liveStreamPager.clipChildren = false
        holder.liveStreamPager.offscreenPageLimit = 3
        holder.liveStreamPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = (0.85f + r * 0.15f)
        }
        holder.liveStreamPager.setPageTransformer(compositePageTransformer)
    }
}