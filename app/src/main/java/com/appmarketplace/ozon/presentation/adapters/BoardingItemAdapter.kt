package com.appmarketplace.ozon.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.pojo.OnBoardingItem


class BoardingItemAdapter(private val onboardingItems:List<OnBoardingItem>): RecyclerView.Adapter<BoardingItemAdapter.OnBoardingItemViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        return  OnBoardingItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_boarding_container,parent,false))
    }

    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        holder.bind(onboardingItems[position])
    }

    override fun getItemCount(): Int {
        return onboardingItems.size
    }

    inner class OnBoardingItemViewHolder(view: View):RecyclerView.ViewHolder(view){

        private val imageOnboarding = view.findViewById<ImageView>(R.id.imageOnboarding)
        private val textTitle = view.findViewById<TextView>(R.id.textTitle)
        private val textDescription = view.findViewById<TextView>(R.id.textDescription)

        fun bind(onBoardingItem: OnBoardingItem){
            imageOnboarding.setImageResource(onBoardingItem.onBoardingImage)
            textTitle?.text = onBoardingItem.title
            textDescription?.text = onBoardingItem.description
        }

    }
}













