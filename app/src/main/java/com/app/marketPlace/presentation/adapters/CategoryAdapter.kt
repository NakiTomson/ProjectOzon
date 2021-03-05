package com.app.marketPlace.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.R
import com.app.marketPlace.presentation.rowType.CategoryRowType
import com.app.marketPlace.domain.modelsUI.OnBoardingItem
import com.squareup.picasso.Picasso


class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.OnBoardingItemViewHolder>(){
    var clickOnCategoryItem: CategoryRowType.ClickCategoryListener? = null

    private val onBoardingItems: MutableList<OnBoardingItem> = mutableListOf()

    fun setData(items: MutableList<OnBoardingItem>) {
        onBoardingItems.clear()
        onBoardingItems.addAll(items)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        return OnBoardingItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_category_product,parent,false))
    }

    

    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        holder.bind(onBoardingItems[position])
    }

    override fun getItemCount(): Int {
        return onBoardingItems.size
    }

    inner class OnBoardingItemViewHolder(view: View):RecyclerView.ViewHolder(view){

        private val imageOnBoarding = view.findViewById<ImageView>(R.id.imageOnBoarding)
        private val textTitle = view.findViewById<TextView>(R.id.textTitle)
        private val textDescription = view.findViewById<TextView>(R.id.textDescription)
        private val categoryItem = view.findViewById<LinearLayout>(R.id.categoryItem)

        fun bind(onBoardingItem: OnBoardingItem){
            textTitle?.text = onBoardingItem.title
            textDescription?.text = onBoardingItem.description

            Picasso.with(itemView.context)
                .load(onBoardingItem.onBoardingImageUrl)
                .fit()
                .placeholder(R.drawable.icon_market_place_app)
                .into(imageOnBoarding)

            categoryItem.setOnClickListener {
                onBoardingItem.category?.let { clickOnCategoryItem?.onClickItem(it)}
            }

        }

    }
}













