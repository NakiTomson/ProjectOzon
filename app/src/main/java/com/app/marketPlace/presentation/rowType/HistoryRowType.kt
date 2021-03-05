package com.app.marketPlace.presentation.rowType

import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.R
import com.app.marketPlace.presentation.interfaces.RowType
import com.app.marketPlace.presentation.factory.ViewHolderFactory
import com.app.marketPlace.domain.modelsUI.OnHistoryItem


data class HistoryRowType(val historyListItems: OnHistoryItem) :RowType {

    lateinit var setHistoryClickListener: HistoryListener

    interface HistoryListener {
        fun onClick(listOf: List<String>, position: Int, imageView: ImageView)
    }


    override fun getItemViewType(): Int {
        return RowType.HISTORY_ROW_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val historyViewHolder = viewHolder as ViewHolderFactory.HistoryViewHolder
        historyListItems.resultHistoryData?.let { historyViewHolder.bind(it) }
        historyViewHolder.historyButton.setOnClickListener {
            it.background =  ContextCompat.getDrawable(viewHolder.itemView.context,R.drawable.button_next)
            historyViewHolder.financeButton.background = ContextCompat.getDrawable(viewHolder.itemView.context,R.drawable.button_back)
            Toast.makeText(viewHolder.itemView.context,"Истории", Toast.LENGTH_SHORT).show()
        }

        historyViewHolder.financeButton.setOnClickListener {
            historyViewHolder.historyButton.background = ContextCompat.getDrawable(viewHolder.itemView.context,R.drawable.button_back)
            it.background = ContextCompat.getDrawable(viewHolder.itemView.context,R.drawable.button_next)
            Toast.makeText(viewHolder.itemView.context,"Финансы", Toast.LENGTH_SHORT).show()
        }

        historyViewHolder.setHistoryClickListener = object :HistoryListener{
            override fun onClick(listOf: List<String>, position: Int, imageView: ImageView) {
                setHistoryClickListener.onClick(listOf, position, imageView)
            }

        }
    }
}