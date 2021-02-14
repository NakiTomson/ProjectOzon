package com.appmarketplace.ozon.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.pojo.OnBoardingItem
import com.squareup.picasso.Picasso


class CategoryItemAdapter(): RecyclerView.Adapter<CategoryItemAdapter.OnBoardingItemViewHolder>(){


    private val onboardingItems: MutableList<OnBoardingItem> = mutableListOf()

    fun setData(items: MutableList<OnBoardingItem>) {
        onboardingItems.clear()
        onboardingItems.addAll(items)
        notifyDataSetChanged()
    }

    fun setItem(position: Int,items: OnBoardingItem) {
        onboardingItems[position] = items
        notifyItemChanged(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        return OnBoardingItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_category_product,parent,false))
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
//            imageOnboarding.setImageResource(onBoardingItem.onBoardingImage)
            textTitle?.text = onBoardingItem.title
            textDescription?.text = onBoardingItem.description

            Picasso.get()
                .load(onBoardingItem.onBoardingImageUrl)
                .fit()
                .noFade()
                .into(imageOnboarding)

        }

    }
}













