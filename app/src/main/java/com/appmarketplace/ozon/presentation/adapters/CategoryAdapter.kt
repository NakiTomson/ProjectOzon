package com.appmarketplace.ozon.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.rowType.CategoryRowType
import com.appmarketplace.ozon.domain.modelsUI.OnBoardingItem
import com.squareup.picasso.Picasso


class CategoryAdapter(): RecyclerView.Adapter<CategoryAdapter.OnBoardingItemViewHolder>(){
    var clickOnCategoryItem: CategoryRowType.ClickCategoryListener? = null

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
        private val categoryItem = view.findViewById<LinearLayout>(R.id.categoryItem)

        fun bind(onBoardingItem: OnBoardingItem){
            textTitle?.text = onBoardingItem.title
            textDescription?.text = onBoardingItem.description

            Picasso.with(itemView.context)
                .load(onBoardingItem.onBoardingImageUrl)
                .fit()
                .placeholder(R.drawable.icon_app_ozon)
                .into(imageOnboarding)

            categoryItem.setOnClickListener {
                onBoardingItem.category?.let { clickOnCategoryItem?.onClickItem(it)}
            }

        }

    }
}













