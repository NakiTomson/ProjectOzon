package com.app.marketPlace.presentation.rowType

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.R
import com.app.marketPlace.data.remote.modelsAPI.HistoryModels
import com.app.marketPlace.presentation.interfaces.RowType
import com.app.marketPlace.presentation.factory.ViewHolderFactory
import com.app.marketPlace.domain.modelsUI.OnHistoryItem
import com.squareup.picasso.Picasso


data class HistoryRowType(val historyModel: HistoryModels?) :RowType {

    lateinit var setHistoryClickListener: HistoryListener

    private var wasSetup:Boolean = false

    interface HistoryListener {
        fun onClick(listOf: List<String>, position: Int, imageView: ImageView)
    }


    override fun getItemViewType(): Int {
        return RowType.HISTORY_ROW_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        if (wasSetup) return
        val historyViewHolder = viewHolder as ViewHolderFactory.HistoryViewHolder
        val container = historyViewHolder.container
        historyModel?.let { historyViewHolder.bind(it) }

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


        setUpImages(historyModel?.arrayImages!!,container)
        wasSetup = true
    }


    private fun setUpImages(listImagesUrl: List<String>, container: LinearLayout?){


        val images = arrayOfNulls<ImageView>(listImagesUrl.size)

        val layoutParams: LinearLayout.LayoutParams =

            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                500
            )

        layoutParams.setMargins(8,8,8,8)

        for ( i in images.indices){
            images[i] = ImageView(container?.context)
            images[i]?.let {image:ImageView->
                Picasso.with(container?.context)
                    .load(listImagesUrl[i])
                    .placeholder(R.drawable.one_history)
                    .into(image)

                image.layoutParams = layoutParams
                image.adjustViewBounds = true
                container?.addView(image)

                image.setOnClickListener {
                    image.transitionName = listImagesUrl[i]
                    setHistoryClickListener.onClick(listImagesUrl,i,image)
                }
            }
        }
    }
}