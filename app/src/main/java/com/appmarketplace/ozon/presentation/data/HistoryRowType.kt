package com.appmarketplace.ozon.presentation.data

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.presentation.Interfaces.RowType
import com.appmarketplace.ozon.presentation.factory.ViewHolderFactory

class HistoryRowType() :RowType {

    override fun getItemViewType(): Int {
        return RowType.HISTORY_ROW_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val historyViewHolder = viewHolder as ViewHolderFactory.HistoryViewHolder
        historyViewHolder.bind()
        historyViewHolder.historyButton.setOnClickListener {
            Toast.makeText(viewHolder.itemView.context,"Истории", Toast.LENGTH_SHORT).show()
        }
    }
}