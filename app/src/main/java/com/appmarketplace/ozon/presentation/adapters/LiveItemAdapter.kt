package com.appmarketplace.ozon.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.pojo.ListResultLiveItems
import com.appmarketplace.ozon.presentation.pojo.OnLiveItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_live.view.*
import java.util.*
import kotlin.collections.ArrayList


class LiveItemAdapter(): RecyclerView.Adapter<LiveItemAdapter.OnBoardingItemViewHolder>(){


    val onboardingItems:MutableList<ListResultLiveItems> = ArrayList()

    fun setData(items:OnLiveItem) {
        val newList = items.resultLiveData
        newList?.let {
            onboardingItems.clear()
            onboardingItems.addAll(newList)
            notifyDataSetChanged()
        }

    }

    fun setItem(position: Int,items:ListResultLiveItems) {
        onboardingItems[position] = items
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        return OnBoardingItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_live,parent,false))
    }


    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        holder.bind(onboardingItems[position])
    }

    override fun getItemCount(): Int {
        return onboardingItems.size
    }

    inner class OnBoardingItemViewHolder(view: View):RecyclerView.ViewHolder(view){


        private val imageOnboarding = view.imageOnboarding
        private val iconOfCompany = view.iconOfCompany
        private val statusLive = view.statusLive
        private val countUserLive = view.countUserLive
        private val textDescription = view.textDescription
        private val nameOfCompanyTitle = view.nameOfCompanyTitle

        fun bind(liveItem: ListResultLiveItems){

            Picasso.get()
                    .load(R.drawable.ic_live_example)
                    .noFade()
                    .placeholder(R.drawable.ic_live_example)
                    .into(imageOnboarding)


            Picasso.get()
                    .load(liveItem.onIconCompanyUrl)
                    .noFade()
                    .into(iconOfCompany)

            statusLive.text = liveItem.statusLiveStream
            countUserLive?.text = liveItem.countUser

            nameOfCompanyTitle?.text = liveItem.nameOfCompany
            textDescription?.text = liveItem.description
        }

    }
}













