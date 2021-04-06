package com.app.marketPlace.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.R
import com.app.marketPlace.data.remote.models.Banner
import com.app.marketPlace.presentation.rowType.BannerRowType
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_banner_container.view.*
import java.util.*


class BannerAdapter : RecyclerView.Adapter<BannerAdapter.OnBoardingItemViewHolder>(){

    @Volatile private var countPosition = -1

    var setBannerClickListener: BannerRowType.BannerListener? = null
    var setCompleteListener: BannerRowType.CompleteListener? = null

    val bannerList:MutableList<Banner> = LinkedList()

    fun setItems(items: MutableList<Banner>) {
        bannerList.clear()
        bannerList.addAll(items)
        notifyDataSetChanged()
    }

    fun setItem(items: Banner) {
        ++countPosition
        bannerList.add(items)
        notifyItemChanged(countPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        return OnBoardingItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_banner_container, parent, false))
    }

    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        holder.bind(bannerList[position])
    }

    override fun getItemCount(): Int {
        return bannerList.size
    }

    inner class OnBoardingItemViewHolder(view: View):RecyclerView.ViewHolder(view){

        private val imageView = view.imageLiveStreams
        private val textTitle = view.textTitle
        private val textDescription = view.textDescription

        fun bind(banner: Banner){
            imageView.transitionName = banner.imageUrl
            Picasso.with(itemView.context)
                .load(banner.imageUrl)
                .noFade()
                .into(imageView,object :Callback{
                    override fun onSuccess() {
                        setCompleteListener?.onComplete()
                    }
                    override fun onError() {
                        setCompleteListener?.onComplete()
                    }
                })

            banner.title?.let{
                textTitle.visibility = View.VISIBLE
                textTitle?.text = it
            }

            banner.description?.let {
                textDescription.visibility = View.VISIBLE
                textDescription?.text = it
            }

            imageView.setOnClickListener {
                setBannerClickListener?.onClickBanner(banner.imageUrl!!, imageView)
            }
            if (banner.startOnBoard){
                imageView.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    700
                )
            }
        }

    }
}














