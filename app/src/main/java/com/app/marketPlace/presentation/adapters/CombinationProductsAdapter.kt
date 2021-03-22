package com.app.marketPlace.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.R
import com.app.marketPlace.presentation.rowType.CategoryRowType
import com.app.marketPlace.data.remote.models.Banner


class CombinationProductsAdapter : RecyclerView.Adapter<CombinationProductsAdapter.OnBoardingItemViewHolder>() {

    var setOnCategoryClickListener: CategoryRowType.ClickCategoryListener? = null

    private val onBoardingItems: MutableList<MutableList<Banner>> = mutableListOf(mutableListOf())

    fun setData(items: MutableList<MutableList<Banner>>) {
        onBoardingItems.clear()
        onBoardingItems.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        return OnBoardingItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_combination_products_adapter, parent, false))
    }

    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        holder.bind(onBoardingItems[position])
    }

    override fun getItemCount(): Int {
        return onBoardingItems.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    inner class OnBoardingItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val onCategoryProductsRecyclerView = view.findViewById<RecyclerView>(R.id.onCategoryProductsRecyclerView)

        fun bind(onBoardingItem: List<Banner>) {
            val categoryItemAdapter = CategoryAdapter()
            categoryItemAdapter.setData(onBoardingItem.toMutableList())
            onCategoryProductsRecyclerView.adapter = categoryItemAdapter
            onCategoryProductsRecyclerView.layoutManager = GridLayoutManager(itemView.context,5)

            categoryItemAdapter.setOnCategoryClickListener = CategoryRowType.ClickCategoryListener { data ->
                setOnCategoryClickListener?.onClickItem(data)
            }
        }
    }
}













