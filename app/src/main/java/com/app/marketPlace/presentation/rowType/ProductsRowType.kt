package com.app.marketPlace.presentation.rowType

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.R
import com.app.marketPlace.domain.models.ProductItem
import com.app.marketPlace.presentation.adapters.ProductAdapter
import com.app.marketPlace.presentation.factory.ViewHolderFactory
import com.app.marketPlace.presentation.interfaces.ProductRowType
import com.app.marketPlace.presentation.interfaces.RowType


data class ProductsRowType(
    val listProducts: List<ProductItem>,
    val spain: Int,
    val productItemAdapter: ProductAdapter
) : ProductRowType {

    override var setOnProductClickListener: ProductRowType.ProductClickListener? = null

    override var setOnHeartProductClickListener: ProductRowType.ClickListener? = null

    override var setOnBasketProductClickListener: ProductRowType.ClickListener? = null


    override fun getItemViewType(): Int {
        return RowType.PRODUCTS_ROW_TYPE
    }


    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {

        val holder = viewHolder as ViewHolderFactory.ProductViewHolder
        holder.productsRecyclerView.adapter =productItemAdapter
        holder.productsRecyclerView.layoutManager = GridLayoutManager(holder.itemView.context, spain)
        holder.bind(listProducts, productItemAdapter)

        productItemAdapter.setClickListenerProduct = ProductRowType.ProductClickListener { product, imageView ->
            setOnProductClickListener?.clickProduct(product, imageView)
        }

        productItemAdapter.setClickHeartProduct = ProductRowType.ClickListener { productsItem ->
            setOnHeartProductClickListener?.onClick(productsItem)
        }

        productItemAdapter.setClickBasketProduct = ProductRowType.ClickListener { productsItem ->
            setOnBasketProductClickListener?.onClick(productsItem)
        }
    }
}