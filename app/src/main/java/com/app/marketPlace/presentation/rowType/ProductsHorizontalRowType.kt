package com.app.marketPlace.presentation.rowType

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.domain.models.ProductItem
import com.app.marketPlace.presentation.adapters.ProductHorizontalAdapter
import com.app.marketPlace.presentation.factory.ViewHolderFactory
import com.app.marketPlace.presentation.interfaces.ProductRowType
import com.app.marketPlace.presentation.interfaces.RowType


data class ProductsHorizontalRowType(
    val listProducts: List<ProductItem>,
    val spain: Int,
    val productItemAdapter: ProductHorizontalAdapter
) : ProductRowType {

    override var setOnProductClickListener: ProductRowType.ProductClickListener? = null

    override var setOnHeartProductClickListener: ProductRowType.ClickListener? = null

    override var setOnBasketProductClickListener: ProductRowType.ClickListener? = null


    override fun getItemViewType(): Int {
        return RowType.PRODUCTS_HORIZONTAL_ROW_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val holder = viewHolder as ViewHolderFactory.ProductHorizontalViewHolder
        holder.productsRecyclerView.adapter = productItemAdapter
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