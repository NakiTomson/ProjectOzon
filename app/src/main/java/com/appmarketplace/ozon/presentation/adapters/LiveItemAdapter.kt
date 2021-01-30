package com.appmarketplace.ozon.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.pojo.OnLiveItem


class LiveItemAdapter(private val onboardingItems:List<OnLiveItem>): RecyclerView.Adapter<LiveItemAdapter.OnBoardingItemViewHolder>(){


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


        private val imageOnboarding = view.findViewById<ImageView>(R.id.imageOnboarding)
        private val iconOfCompany = view.findViewById<ImageView>(R.id.iconOfCompany)
        private val statusLive = view.findViewById<TextView>(R.id.statusLive)
        private val countUserLive = view.findViewById<TextView>(R.id.countUserLive)
        private val textDescription = view.findViewById<TextView>(R.id.textDescription)
        private val nameOfCompanyTitle = view.findViewById<TextView>(R.id.nameOfCompanyTitle)

        fun bind(onBoardingItem: OnLiveItem){
            imageOnboarding.setImageResource(onBoardingItem.onBoardingImage)
            iconOfCompany.setImageResource(onBoardingItem.onIconCompany)
            statusLive.text = onBoardingItem.statusLiveStream
            countUserLive?.text = onBoardingItem.countUser
            nameOfCompanyTitle?.text = onBoardingItem.nameOfCompany
            textDescription?.text = onBoardingItem.description
        }

    }
}













