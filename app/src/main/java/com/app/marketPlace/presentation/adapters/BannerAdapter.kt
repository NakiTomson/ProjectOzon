package com.app.marketPlace.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.R
import com.app.marketPlace.data.remote.models.Banner
import com.app.marketPlace.presentation.extensions.uploadPicture
import com.app.marketPlace.presentation.rowType.BannerRowType
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_boarding_container.view.*
import java.util.*




class BannerAdapter() : RecyclerView.Adapter<BannerAdapter.OnBoardingItemViewHolder>(){

    @Volatile private var countPosition = -1

    var setBannerClickListener: BannerRowType.BannerListener? = null
    var setCompleteListener: BannerRowType.CompleteListener? = null

    val onBoardingItems:MutableList<Banner> = LinkedList()

    fun setData(items: MutableList<Banner>) {
        onBoardingItems.clear()
        onBoardingItems.addAll(items)
        notifyDataSetChanged()
    }

    fun setItem(items: Banner) {
        ++countPosition
        onBoardingItems.add(items)
        notifyItemChanged(countPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        return  OnBoardingItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_boarding_container, parent, false))
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

        fun bind(onBoardingItem: Banner){

            imageOnBoarding.transitionName = onBoardingItem.transitionName


            if (onBoardingItem.onBoardingImageUrl.contains("drop".toUpperCase(Locale.ROOT))) {

                Picasso.with(itemView.context)
                    .load(onBoardingItem.onBoardingImageUrl)
                    .placeholder(R.drawable.example_ads_banner)
                    .noFade()
                    .into(imageOnBoarding)
            }else{
                Picasso.with(itemView.context).load(onBoardingItem.onBoardingImageUrl)
                    .centerInside()
                    .resize(500,500)
                    .into(imageOnBoarding,object :Callback{
                        override fun onSuccess() {
                            setCompleteListener?.onComplete()
                        }
                        override fun onError() {
                            setCompleteListener?.onComplete()
                        }
                    })
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
                setBannerClickListener?.onClickBanner(onBoardingItem.onBoardingImageUrl, imageOnBoarding)
            }
        }
    }
}














