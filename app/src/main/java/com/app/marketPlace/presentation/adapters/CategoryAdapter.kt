package com.app.marketPlace.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.R
import com.app.marketPlace.presentation.rowType.CategoryRowType
import com.app.marketPlace.data.remote.models.Categories
import com.app.marketPlace.presentation.rowType.BannerRowType
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_category_product.view.*

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.OnBoardingItemViewHolder>() {

    var setCompleteListener: BannerRowType.CompleteListener? = null
    var setOnCategoryClickListener: CategoryRowType.ClickCategoryListener? = null

    private val categoriesList: MutableList<Categories> = mutableListOf()

    fun setData(items: List<Categories>) {
        categoriesList.clear()
        categoriesList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        return OnBoardingItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_category_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        holder.bind(categoriesList[position])
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    inner class OnBoardingItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val iconCategory = view.imageLiveStreams
        private val titleCategory = view.textTitle

        private val rootLayout = view.categoryItem

        fun bind(category: Categories) {
            titleCategory.text = category.name

            Picasso.with(itemView.context)
                .load(category.image)
                .fit()
                .placeholder(R.drawable.icon_market_place_app)
                .into(iconCategory,object :Callback{
                    override fun onSuccess() {
                        setCompleteListener?.onComplete()
                    }
                    override fun onError() {
                        setCompleteListener?.onComplete()
                    }
                })
            rootLayout.setOnClickListener {
                category.id?.let {
                    setOnCategoryClickListener?.onClickItem(it)
                }
            }
        }
    }
}