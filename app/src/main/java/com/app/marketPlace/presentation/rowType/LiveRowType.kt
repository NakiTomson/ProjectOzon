package com.app.marketPlace.presentation.rowType

import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.presentation.interfaces.RowType
import com.app.marketPlace.presentation.adapters.LiveItemAdapter
import com.app.marketPlace.presentation.factory.ViewHolderFactory

data class LiveRowType(val liveItemAdapter: LiveItemAdapter) :RowType {

    lateinit var setLiveClickListener: LiveListener

    interface LiveListener {
        fun onClick(liveUrl: String)
    }

    override fun getItemViewType(): Int {
        return RowType.LIVE_ROW_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val liveViewHolder = viewHolder as ViewHolderFactory.LiveViewHolder
        liveViewHolder.bind(liveItemAdapter)

        liveViewHolder.serLiveClickListener = object :LiveListener{
            override fun onClick(liveUrl: String) {
                setLiveClickListener.onClick(liveUrl)
            }
        }
    }
}