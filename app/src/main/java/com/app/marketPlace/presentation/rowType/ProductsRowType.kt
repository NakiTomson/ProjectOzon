package com.app.marketPlace.presentation.rowType

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.R
import com.app.marketPlace.domain.modelsUI.OnProductItem
import com.app.marketPlace.presentation.adapters.ProductItemAdapter
import com.app.marketPlace.presentation.factory.ViewHolderFactory
import com.app.marketPlace.presentation.interfaces.RowType


data class ProductsRowType(
    val listProducts: List<OnProductItem>,
    val spain: Int,
    val productItemAdapter: ProductItemAdapter
) :RowType {


    var setClickListenerProduct:OnProductClickListener? = null

    var setClickHeartProduct: OnClickListener? = null

    var setClickBasketProduct: OnClickListener? = null

    var wasSetup:Boolean = false

    interface OnProductClickListener{
        fun clickProduct(product: OnProductItem, imageView: ImageView)
    }

    interface OnClickListener{
        fun onClick(productsItem: OnProductItem)
    }

    override fun getItemViewType(): Int {
        return RowType.PRODUCTS_ROW_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        if (wasSetup) return

        val animation: Animation = AnimationUtils.loadAnimation(
            viewHolder?.itemView?.context,
            R.anim.appearances_out
        )
        val controller = LayoutAnimationController(animation)

        val productionViewHolder = viewHolder as ViewHolderFactory.ProductViewHolder
        productionViewHolder.bind(listProducts, spain, productItemAdapter)

        productionViewHolder.productsRecyclerView.layoutAnimation = controller


        productionViewHolder.setClickListenerProduct = object :OnProductClickListener{

            override fun clickProduct(product: OnProductItem, imageView: ImageView) {
                setClickListenerProduct?.clickProduct(product, imageView)
            }
        }
        productionViewHolder.setClickHeartProduct = object :OnClickListener{
            override fun onClick(productsItem: OnProductItem) {
                setClickHeartProduct?.onClick(productsItem)
            }
        }
        productionViewHolder.setClickBasketProduct = object :OnClickListener{
            override fun onClick(productsItem: OnProductItem) {
                setClickBasketProduct?.onClick(productsItem)
            }
        }
        wasSetup = true
    }
}