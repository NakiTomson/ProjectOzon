package com.appmarketplace.ozon.presentation.data

import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.presentation.Interfaces.RowType
import com.appmarketplace.ozon.presentation.adapters.LiveItemAdapter
import com.appmarketplace.ozon.presentation.factory.ViewHolderFactory

class LiveRowType(val liveItemAdapter: LiveItemAdapter) :RowType {


    override fun getItemViewType(): Int {
        return RowType.LIVE_ROW_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val liveViewHolder = viewHolder as ViewHolderFactory.LiveViewHolder
        liveViewHolder.bind(liveItemAdapter)
    }
}