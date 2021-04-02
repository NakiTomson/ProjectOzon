package com.app.marketPlace.presentation.adapters

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.R
import com.app.marketPlace.data.remote.models.Categories
import com.app.marketPlace.presentation.rowType.BannerRowType
import com.app.marketPlace.presentation.rowType.CategoryRowType
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_simple.view.*


class SimpleCategoriesAdapter : RecyclerView.Adapter<SimpleCategoriesAdapter.OnBoardingItemViewHolder>() {

    var setCompleteListener: BannerRowType.CompleteListener? = null

    var setOnCategoryClickListener: CategoryRowType.ClickCategoryListener2? = null

    private val categoriesList: MutableList<Categories> = mutableListOf()

    fun setData(items: List<Categories>) {
        categoriesList.clear()
        categoriesList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        return OnBoardingItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_simple, parent, false)
        )
    }

    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        holder.bind(categoriesList[position])
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }


    inner class OnBoardingItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imageCategory = view.imageCategory
        private val nameCategory = view.namePath
        private val titleCategory = view.titleCategory
        private val imageNext = view.imageNextCategories
        private val rootItem = view.rootSimpleItem

        val visible = View.VISIBLE
        val gone = View.GONE

        fun bind(category: Categories) {

            nameCategory?.text = category.name
            nameCategory.transitionName = category.name

            category.image?.let {
                setCategoriesWithImage(category)
            } ?: kotlin.run {
                setSimpleCategories(category)
            }

            rootItem.setOnClickListener {
                category.name?.let {
                    setOnCategoryClickListener?.onClickItem(category, nameCategory)
                }
            }
        }

        private fun setCategoriesWithImage(category: Categories) {

            Picasso.with(itemView.context)
                .load(category.image)
                .fit()
                .placeholder(R.drawable.icon_market_place_app)
                .into(imageCategory,object : Callback {
                    override fun onSuccess() {

                        setCompleteListener?.onComplete()
                    }
                    override fun onError() {
                        setCompleteListener?.onComplete()
                    }
                })
        }

        private fun setSimpleCategories(category: Categories) {
            imageCategory.visibility = gone
            titleCategory.visibility = gone
            imageNext.visibility = if (category.subCategories.isNullOrEmpty()) gone else visible

            category.back?.let {
                imageCategory.visibility = visible
                imageCategory.setImageResource(it)

                imageCategory.layoutParams = ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                val outValue = TypedValue()
                itemView.context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
                imageCategory.setBackgroundResource(outValue.resourceId)
            }

            category.backgroundColorSelected?.let { color->
                rootItem.setBackgroundColor(itemView.resources.getColor(color))
            }
        }
    }

}