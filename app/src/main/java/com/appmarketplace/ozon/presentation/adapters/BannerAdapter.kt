package com.appmarketplace.ozon.presentation.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.domain.modelsUI.OnBoardingItem
import com.appmarketplace.ozon.presentation.rowType.BannerRowType
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_boarding_container.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.URL
import java.net.URLConnection
import java.util.*


class BannerAdapter(): RecyclerView.Adapter<BannerAdapter.OnBoardingItemViewHolder>(){


    @Volatile private var countPosition = -1

    var bannerClickListener: BannerRowType.BannerListener? = null

    val onboardingItems:MutableList<OnBoardingItem> = LinkedList()

    fun setData(items: MutableList<OnBoardingItem>) {
        onboardingItems.clear()
        onboardingItems.addAll(items)
        notifyDataSetChanged()
    }

    fun setItem(items: OnBoardingItem) {
        ++countPosition
        onboardingItems.add(items)
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


            if (onBoardingItem?.onBoardingImageUrl.contains("dropbox")){
                Picasso.get()
                    .load(onBoardingItem.onBoardingImageUrl)
                    .placeholder(R.drawable.example_ads_banner)
                    .noFade()
                    .into(imageOnboarding)
            }else{
                Log.v("TAGMAPPEER", "re url  ${onBoardingItem.onBoardingImageUrl}")
                val newUrl = URL(onBoardingItem.onBoardingImageUrl)
                GlobalScope.launch(Dispatchers.IO) {
                    val connection: URLConnection = newUrl.openConnection()
                    val inputStream: InputStream = connection.getInputStream()
                    val productIcon = BitmapFactory.decodeStream(inputStream)

                    val pairSize = mapperSize(productIcon.height,productIcon.width)
                    val resizedBitmap =
                        Bitmap.createScaledBitmap(productIcon, pairSize.second, pairSize.first, false)

                    inputStream.close()

                    withContext(Dispatchers.Main) {
                        imageOnboarding.setImageBitmap(resizedBitmap)
                    }
                }
            }

            onBoardingItem.title?.let{
                textTitle.visibility = View.VISIBLE
                textTitle?.text = it
            }

            onBoardingItem.description?.let {
                textDescription.visibility = View.VISIBLE
                textDescription?.text = it
            }

            imageOnboarding.setOnClickListener {
                imageOnboarding.transitionName = onBoardingItem.onBoardingImageUrl
                bannerClickListener?.onClickBanner(
                    onBoardingItem.onBoardingImageUrl,
                    imageOnboarding
                )
            }
        }


        fun mapperSize(height: Int,width: Int):Pair<Int,Int>{
            Log.v("TAGMAPPEER","re he $height  re wi $width")

            if (width<=500 || height<=500){
                return Pair(first = width,height)
            }

            return if (height>width){
                val scale = height.toString().take(2).toLong() / 10
                val newHeight = height / scale
                val newWidth = width / scale
                Log.v("TAGMAPPEER", "re1 newhe ${newHeight.toInt()}  re2 newwi ${newWidth.toInt()}")
                Pair(first = newHeight.toInt(),newWidth.toInt())
            }else {
                val scale = width.toString().take(2).toLong() / 10
                val newHeight = height / scale
                val newWidth = width / scale

                Log.v("TAGMAPPEER", "re2 newhe ${newHeight.toInt()}  re2 newwi ${newWidth.toInt()}")
                Pair(first = newHeight.toInt(),newWidth.toInt())
            }
        }
    }
}













