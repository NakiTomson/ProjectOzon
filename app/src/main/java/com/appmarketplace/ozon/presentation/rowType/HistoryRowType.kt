package com.appmarketplace.ozon.presentation.rowType

import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.Interfaces.RowType
import com.appmarketplace.ozon.presentation.factory.ViewHolderFactory
import com.appmarketplace.ozon.domain.modelsUI.OnHistoryItem


data class HistoryRowType(val historyListItems: OnHistoryItem) :RowType {

    lateinit var historClickListener: HistoryListener

    interface HistoryListener {
        fun onClickHistory(listOf: List<String>, position: Int, imageView: ImageView)
    }


    override fun getItemViewType(): Int {
        return RowType.HISTORY_ROW_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val historyViewHolder = viewHolder as ViewHolderFactory.HistoryViewHolder
        historyListItems.resultHistoryData?.let { historyViewHolder.bind(it) }
        historyViewHolder.historyButton.setOnClickListener {
            it.background = viewHolder.itemView.context.getDrawable(R.drawable.button_next)
            historyViewHolder.financeButton.background =  viewHolder.itemView.context.getDrawable(R.drawable.button_back)
            Toast.makeText(viewHolder.itemView.context,"Истории", Toast.LENGTH_SHORT).show()
        }

        historyViewHolder.financeButton.setOnClickListener {
            historyViewHolder.historyButton.background =  viewHolder.itemView.context.getDrawable(R.drawable.button_back)
            it.background = viewHolder.itemView.context.getDrawable(R.drawable.button_next)
            Toast.makeText(viewHolder.itemView.context,"Финансы", Toast.LENGTH_SHORT).show()
        }

        historyViewHolder.historClickListener = object :HistoryListener{
            override fun onClickHistory(listOf: List<String>, position: Int, imageView: ImageView) {
                historClickListener.onClickHistory(listOf, position, imageView)
            }

        }
    }
}