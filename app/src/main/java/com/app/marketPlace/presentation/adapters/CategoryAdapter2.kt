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
import kotlinx.android.synthetic.main.item_simple.view.*


class CategoryAdapter2 : RecyclerView.Adapter<CategoryAdapter2.OnBoardingItemViewHolder>() {

    var setCompleteListener: BannerRowType.CompleteListener? = null

    var setOnCategoryClickListener: CategoryRowType.ClickCategoryListener2? = null

    private val onBoardingItems: MutableList<Categories> = mutableListOf()


    fun setData(items: List<Categories>) {
        onBoardingItems.clear()
        onBoardingItems.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        return OnBoardingItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_simple, parent, false)
        )
    }

    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        holder.bind(onBoardingItems[position])
    }

    override fun getItemCount(): Int {
        return onBoardingItems.size
    }


    inner class OnBoardingItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        private val imageOnBoarding = view.imageCategory
        private val textTitle = view.namePath
        private val titleCategory = view.titleCategory
        private val imageNext = view.imageNextCategories
        private val rootItem = view.rootSimpleItem
        val visible =View.VISIBLE
        val gone =View.GONE


        fun bind(category: Categories) {

            textTitle?.text = category.name
            imageOnBoarding.visibility = gone
            titleCategory.visibility = gone

            imageNext.visibility = if (category.subCategories.isNullOrEmpty()) gone else visible

            textTitle.transitionName = category.name

            category.back?.let {
                imageOnBoarding.visibility = visible
                imageOnBoarding.setImageResource(it)
                imageOnBoarding.layoutParams = ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                val outValue = TypedValue()
                itemView.context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
                imageOnBoarding.setBackgroundResource(outValue.resourceId);
            }

            category.backgroundColorSelected?.let { color->
                rootItem.setBackgroundColor(itemView.resources.getColor(color))
            }

            rootItem.setOnClickListener {
                category.name?.let {
                    setOnCategoryClickListener?.onClickItem(category, textTitle)
                }
            }

//            shimmer.stopShimmer()
//            shimmer.setShimmer(null)

//            Picasso.with(itemView.context)
//                .load(category.image)
//                .fit()
//                .placeholder(R.drawable.icon_market_place_app)
//                .into(imageOnBoarding,object : Callback {
//                    override fun onSuccess() {
//
//                        setCompleteListener?.onComplete()
//                    }
//                    override fun onError() {
//                        setCompleteListener?.onComplete()
//                    }
//                })
        }
    }
}