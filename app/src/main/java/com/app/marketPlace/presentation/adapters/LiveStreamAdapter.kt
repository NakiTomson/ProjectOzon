package com.app.marketPlace.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.R
import com.app.marketPlace.presentation.rowType.LiveRowType
import com.app.marketPlace.domain.models.LiveItems
import com.app.marketPlace.domain.models.LiveStreamItem
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_live.view.*
import kotlin.collections.ArrayList


class LiveStreamAdapter : RecyclerView.Adapter<LiveStreamAdapter.OnBoardingItemViewHolder>(){

    private val liveList:MutableList<LiveItems> = ArrayList()

    var setOnLiveClickListener: LiveRowType.LiveListener? = null

    fun setData(items:LiveStreamItem) {
        items.liveItemsList?.let {
            liveList.addAll(it)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        return OnBoardingItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_live,parent,false))
    }

    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        holder.bind(liveList[position])
    }

    override fun getItemCount(): Int {
        return liveList.size
    }

    inner class OnBoardingItemViewHolder(view: View):RecyclerView.ViewHolder(view){

        private val imageLive = view.imageLiveStreams
        private val iconCompany = view.iconOfCompany
        private val statusLive = view.statusLive
        private val countUserLive = view.countUserLive
        private val textDescription = view.textDescription
        private val nameOfCompanyTitle = view.nameOfCompanyTitle
        private val shimmer = itemView.shimmerLayout

        fun bind(liveItem: LiveItems){

            imageLive.transitionName = liveItem.onIconCompanyUrl

            Picasso.with(itemView.context)
                .load(liveItem.onIconCompanyUrl)
                .into(iconCompany)

            Picasso.with(itemView.context)
                .load(liveItem.onBoardingImageUrl)
                .into(imageLive,object:Callback{
                    override fun onSuccess() {
                        shimmer.stopShimmer()
                        shimmer.setShimmer(null)
                    }
                    override fun onError() {
                        shimmer.stopShimmer()
                        shimmer.setShimmer(null)
                    }
                })

            statusLive.text = liveItem.statusLiveStream
            countUserLive?.text = liveItem.countUser

            nameOfCompanyTitle.text = liveItem.nameOfCompany
            textDescription.text = liveItem.description

            imageLive.setOnClickListener {
                liveItem.onIconCompanyUrl?.let { urlImage ->
                    setOnLiveClickListener?.onClick(urlImage,imageLive)
                }
            }
        }
    }
}













