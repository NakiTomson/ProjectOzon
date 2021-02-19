package com.appmarketplace.ozon.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.pojo.OnBoardingItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_boarding_container.view.*
import java.util.*


class BannerAdapter(): RecyclerView.Adapter<BannerAdapter.OnBoardingItemViewHolder>(){



    val onboardingItems:MutableList<OnBoardingItem> = LinkedList()

    fun setData(items:MutableList<OnBoardingItem>) {
        onboardingItems.clear()
        onboardingItems.addAll(items)
        notifyDataSetChanged()
    }

    fun setItem(position: Int,items: OnBoardingItem) {
        onboardingItems[position] = items
        notifyItemChanged(position)
    }

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

        private val imageOnboarding = view.imageOnboarding
        private val textTitle = view.textTitle
        private val textDescription = view.textDescription

        fun bind(onBoardingItem: OnBoardingItem){

            Picasso.get()
                .load(onBoardingItem.onBoardingImageUrl)
                .placeholder(R.drawable.example_ads_banner)
                .noFade()
                .into(imageOnboarding)

//            imageOnboarding.setImageResource(onBoardingItem.onBoardingImage)

            onBoardingItem.title?.let{
                textTitle.visibility = View.VISIBLE
                textTitle?.text = it
            }

            onBoardingItem.description?.let {
                textDescription.visibility = View.VISIBLE
                textDescription?.text = it
            }

        }

    }
}













