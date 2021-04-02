package com.app.marketPlace.presentation.rowType

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.R
import com.app.marketPlace.data.remote.models.Stories
import com.app.marketPlace.presentation.interfaces.RowType
import com.app.marketPlace.presentation.factory.ViewHolderFactory
import com.facebook.shimmer.ShimmerFrameLayout
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


data class StoryRowType(val stories: Stories?) :RowType {

    var setOnStoriesClickListener: HistoryListener? = null


    fun interface HistoryListener {
        fun onClick(listOf: List<String>, position: Int, imageView: ImageView)
    }

    override fun getItemViewType(): Int {
        return RowType.HISTORY_ROW_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val holder = viewHolder as ViewHolderFactory.HistoryViewHolder

        stories?.let { holder.bind(it) }

        holder.historyButton.setOnClickListener {
            it.background =  ContextCompat.getDrawable(viewHolder.itemView.context,R.drawable.button_next)
            holder.financeButton.background = ContextCompat.getDrawable(viewHolder.itemView.context,R.drawable.button_back)
            Toast.makeText(viewHolder.itemView.context,"Истории", Toast.LENGTH_SHORT).show()
        }

        holder.financeButton.setOnClickListener {
            holder.historyButton.background = ContextCompat.getDrawable(viewHolder.itemView.context,R.drawable.button_back)
            it.background = ContextCompat.getDrawable(viewHolder.itemView.context,R.drawable.button_next)
            Toast.makeText(viewHolder.itemView.context,"Финансы", Toast.LENGTH_SHORT).show()

        }

        setUpImages(stories?.arrayImages!!,holder.container,holder.shimmer)
    }


    private fun setUpImages(listImagesUrl: List<String>, container: LinearLayout, shimmer: ShimmerFrameLayout){
        if (container.children.count() > 0) return
        val images = arrayOfNulls<RoundedImageView>(listImagesUrl.size)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                250,
                375
            )
        layoutParams.setMargins(8, 8, 16, 8)
        for (i in images.indices) {
            images[i] = RoundedImageView(container.context)
            images[i]?.transitionName = listImagesUrl[i] + i
            images[i]?.let { image: RoundedImageView ->
                Picasso.with(container.context)
                    .load(listImagesUrl[i])
                    .placeholder(R.drawable.one_history)
                    .into(image, object : Callback {
                        override fun onSuccess() {
                            shimmer.stopShimmer()
                            shimmer.setShimmer(null)
                        }

                        override fun onError() {
                            shimmer.stopShimmer()
                            shimmer.setShimmer(null)
                        }
                    })

                image.layoutParams = layoutParams
                image.adjustViewBounds = true
                image.scaleType = ImageView.ScaleType.CENTER_CROP
                image.cornerRadius = 30f
                image.borderColor = container.context.resources.getColor(R.color.custom_color_primary_light)
                image.borderWidth = 7f
                container.addView(image)
                image.setOnClickListener {
                    setOnStoriesClickListener?.onClick(listImagesUrl, i, image)
                }
            }
        }
    }
    
}