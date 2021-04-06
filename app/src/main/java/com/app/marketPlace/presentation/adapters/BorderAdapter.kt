package com.app.marketPlace.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.R
import com.app.marketPlace.data.remote.models.Banner
import com.app.marketPlace.presentation.rowType.BannerRowType
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_border_container.view.*
import java.util.*


class BorderAdapter : RecyclerView.Adapter<BorderAdapter.OnBoardingItemViewHolder>() {

    @Volatile
    private var countPosition = -1

    var setCompleteListener: BannerRowType.CompleteListener? = null

    private val onBoardingItems: MutableList<Banner> = LinkedList()


    fun setItems(items: List<Banner>) {
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
        return OnBoardingItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_border_container, parent, false))
    }

    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        holder.bind(onBoardingItems[position])
    }

    override fun getItemCount(): Int {
        return onBoardingItems.size
    }

    inner class OnBoardingItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val onBoarding = view.imageLiveStreams

        fun bind(onBoardingItem: Banner) {

            onBoarding.transitionName = onBoardingItem.imageUrl

            Picasso.with(itemView.context).load(onBoardingItem.imageUrl)
                .centerInside()
                .resize(500, 500)
                .into(onBoarding, object : Callback {
                    override fun onSuccess() {
                        setCompleteListener?.onComplete()
                    }

                    override fun onError() {
                        setCompleteListener?.onComplete()
                    }
                })
        }
    }
}