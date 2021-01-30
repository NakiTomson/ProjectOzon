package com.appmarketplace.ozon.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.pojo.OnBoardingItem


class CombinationProductsAdapter(
    private val onboardingItems: List<List<OnBoardingItem>>
) : RecyclerView.Adapter<CombinationProductsAdapter.OnBoardingItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        return OnBoardingItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_combination_products_adapter, parent, false)
        )
    }


    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        holder.bind(onboardingItems[position])
    }

    override fun getItemCount(): Int {
        return onboardingItems.size
    }

    inner class OnBoardingItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        private val onCategoryProductsRecyclerView = view.findViewById<RecyclerView>(R.id.onCategoryProductsRecyclerView)

        fun bind(onBoardingItem: List<OnBoardingItem>) {
            onCategoryProductsRecyclerView.adapter =CategoryItemAdapter(onboardingItems = onBoardingItem)
            onCategoryProductsRecyclerView.layoutManager = GridLayoutManager(itemView.context,5)
        }
    }
}













