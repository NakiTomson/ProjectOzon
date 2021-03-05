package com.app.marketPlace.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.R
import com.app.marketPlace.domain.modelsUI.OnBoardingItem
import com.app.marketPlace.presentation.rowType.BannerRowType
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_boarding_container.view.*
import java.util.*


class BannerAdapter : RecyclerView.Adapter<BannerAdapter.OnBoardingItemViewHolder>(){


    @Volatile private var countPosition = -1

    var bannerClickListener: BannerRowType.BannerListener? = null

    val onBoardingItems:MutableList<OnBoardingItem> = LinkedList()


    fun setData(items: MutableList<OnBoardingItem>) {
        onBoardingItems.clear()
        onBoardingItems.addAll(items)
        notifyDataSetChanged()
    }

    fun setItem(items: OnBoardingItem) {
        ++countPosition
        onBoardingItems.add(items)
        notifyItemChanged(countPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        return  OnBoardingItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_boarding_container,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        holder.bind(onBoardingItems[position])
    }

    override fun getItemCount(): Int {
        return onBoardingItems.size
    }




    inner class OnBoardingItemViewHolder(view: View):RecyclerView.ViewHolder(view){

        private val imageOnBoarding = view.imageOnBoarding
        private val textTitle = view.textTitle
        private val textDescription = view.textDescription

        fun bind(onBoardingItem: OnBoardingItem){


            if (onBoardingItem.onBoardingImageUrl.contains("dropBox".toUpperCase(Locale.ROOT))) {
                Picasso.with(itemView.context)
                    .load(onBoardingItem.onBoardingImageUrl)
                    .placeholder(R.drawable.example_ads_banner)
                    .noFade()
                    .into(imageOnBoarding)
            }else{
                imageOnBoarding.transitionName = onBoardingItem.onBoardingImageUrl
                Picasso.with(itemView.context).load(onBoardingItem.onBoardingImageUrl)
                    .resize(500,500)
                    .centerInside()
                    .into(imageOnBoarding)
            }

            onBoardingItem.title?.let{
                textTitle.visibility = View.VISIBLE
                textTitle?.text = it
            }

            onBoardingItem.description?.let {
                textDescription.visibility = View.VISIBLE
                textDescription?.text = it
            }

            imageOnBoarding.setOnClickListener {
                imageOnBoarding.transitionName = onBoardingItem.onBoardingImageUrl
                bannerClickListener?.onClickBanner(
                    onBoardingItem.onBoardingImageUrl,
                    imageOnBoarding
                )
            }
        }
    }
}













