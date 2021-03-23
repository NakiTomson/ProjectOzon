package com.app.marketPlace.presentation.rowType

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.app.marketPlace.presentation.interfaces.RowType
import com.app.marketPlace.presentation.adapters.LiveStreamAdapter
import com.app.marketPlace.presentation.factory.ViewHolderFactory
import kotlin.math.abs

data class LiveRowType(val liveItemAdapter: LiveStreamAdapter) :RowType {

    lateinit var setOnLiveStreamClickListener: LiveListener
    var wasSetup:Boolean = false

    fun interface LiveListener {
        fun onClick(liveUrl: String,view:ImageView)
    }

    override fun getItemViewType(): Int {
        return RowType.LIVE_ROW_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        if (wasSetup) return
        val liveViewHolder = viewHolder as ViewHolderFactory.LiveViewHolder
        liveViewHolder.bind(liveItemAdapter)

        liveViewHolder.serLiveClickListener = LiveListener { liveUrl, view ->
            setOnLiveStreamClickListener.onClick(liveUrl,view)
        }

        liveViewHolder.liveStreamPager.clipToPadding = false
        liveViewHolder.liveStreamPager.clipChildren = false
        liveViewHolder.liveStreamPager.offscreenPageLimit = 3
        liveViewHolder.liveStreamPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = (0.85f + r * 0.15f)
        }
        liveViewHolder.liveStreamPager.setPageTransformer(compositePageTransformer)
        wasSetup = true
    }
}