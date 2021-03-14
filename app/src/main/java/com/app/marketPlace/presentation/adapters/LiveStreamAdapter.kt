package com.app.marketPlace.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.R
import com.app.marketPlace.presentation.rowType.LiveRowType
import com.app.marketPlace.domain.models.ListResultLiveItems
import com.app.marketPlace.domain.models.LiveStreamItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_live.view.*
import kotlin.collections.ArrayList


class LiveStreamAdapter : RecyclerView.Adapter<LiveStreamAdapter.OnBoardingItemViewHolder>(){

    private val onBoardingItems:MutableList<ListResultLiveItems> = ArrayList()

    var setOnLiveClickListener: LiveRowType.LiveListener? = null

    fun setData(items:LiveStreamItem) {
        val newList = items.resultLiveData
        newList?.let {
            onBoardingItems.addAll(newList)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        return OnBoardingItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_live,parent,false))
    }

    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        holder.bind(onBoardingItems[position])
    }

    override fun getItemCount(): Int {
        return onBoardingItems.size
    }

    inner class OnBoardingItemViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val imageOnBoarding = view.imageOnBoarding
        private val iconOfCompany = view.iconOfCompany
        private val statusLive = view.statusLive
        private val countUserLive = view.countUserLive
        private val textDescription = view.textDescription
        private val nameOfCompanyTitle = view.nameOfCompanyTitle

        fun bind(liveItem: ListResultLiveItems){

            Picasso.with(itemView.context)
                    .load(liveItem.onIconCompanyUrl)
                    .into(iconOfCompany)

            Picasso.with(itemView.context)
                .load(liveItem.onIconCompanyUrl)
                .into(iconOfCompany)

            statusLive.text = liveItem.statusLiveStream
            countUserLive?.text = liveItem.countUser

            nameOfCompanyTitle?.text = liveItem.nameOfCompany
            textDescription?.text = liveItem.description

            imageOnBoarding.setOnClickListener {
                imageOnBoarding.transitionName = liveItem.onIconCompanyUrl
                liveItem.onIconCompanyUrl?.let { urlImage -> setOnLiveClickListener?.onClick(urlImage,imageOnBoarding) }
            }
        }

    }
}













