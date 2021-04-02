package com.app.marketPlace.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.R
import com.app.marketPlace.presentation.rowType.CategoryRowType
import com.app.marketPlace.data.remote.models.Categories
import com.app.marketPlace.presentation.rowType.BannerRowType


class CombinationAdapter : RecyclerView.Adapter<CombinationAdapter.OnBoardingItemViewHolder>() {

    var setCompleteListener: BannerRowType.CompleteListener? = null

    var setOnCategoryClickListener: CategoryRowType.ClickCategoryListener? = null

    private val onBoardingItems: MutableList<List<Categories>> = mutableListOf(mutableListOf())

    fun setData(vararg items: List<Categories>) {
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

    inner class OnBoardingItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val recyclerView = view.findViewById<RecyclerView>(R.id.onCategoryProductsRecyclerView)

        fun bind(onBoardingItem: List<Categories>) {
            val adapter = CategoryAdapter()
            adapter.setData(onBoardingItem)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(itemView.context,5)

            adapter.setOnCategoryClickListener = CategoryRowType.ClickCategoryListener { data ->
                setOnCategoryClickListener?.onClickItem(data)
            }

            adapter.setCompleteListener = BannerRowType.CompleteListener {
                setCompleteListener?.onComplete()
            }
        }
    }
}













