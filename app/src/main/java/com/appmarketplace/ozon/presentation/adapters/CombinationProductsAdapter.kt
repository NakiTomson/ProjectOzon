package com.appmarketplace.ozon.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.pojo.OnBoardingItem


class CombinationProductsAdapter(
) : RecyclerView.Adapter<CombinationProductsAdapter.OnBoardingItemViewHolder>() {

    private val onboardingItems: MutableList<MutableList<OnBoardingItem>> = mutableListOf(mutableListOf())

    fun setData(items: MutableList<MutableList<OnBoardingItem>>) {
        onboardingItems.clear()
        onboardingItems.addAll(items)
        notifyDataSetChanged()
    }

    fun setItem(position: Int,items: MutableList<OnBoardingItem>) {
        onboardingItems[position] = items
        notifyItemChanged(position)
    }

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
            val categroyItemAdapter = CategoryAdapter()
            categroyItemAdapter.setData(onBoardingItem.toMutableList())
            onCategoryProductsRecyclerView.adapter = categroyItemAdapter
            onCategoryProductsRecyclerView.layoutManager = GridLayoutManager(itemView.context,5)
        }
    }
}













