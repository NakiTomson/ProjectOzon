package com.app.marketPlace.presentation.rowType

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.domain.models.Product
import com.app.marketPlace.presentation.adapters.ProductAdapter
import com.app.marketPlace.presentation.factory.ViewHolderFactory
import com.app.marketPlace.presentation.interfaces.ProductRowType
import com.app.marketPlace.presentation.interfaces.RowType


data class ProductRowType(
    val listProducts: List<Product>,
    val spain: Int,
    val adapter: ProductAdapter
) : ProductRowType {

    override var setOnProductClickListener: ProductRowType.ProductClickListener? = null

    override var setOnHeartProductClickListener: ProductRowType.ClickListener? = null

    override var setOnBasketProductClickListener: ProductRowType.ClickListener? = null


    override fun getItemViewType(): Int {
        return RowType.PRODUCTS_ROW_TYPE
    }


    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {

        val holder = viewHolder as ViewHolderFactory.ProductViewHolder
        holder.productsRecyclerView.adapter =adapter
        holder.productsRecyclerView.layoutManager = GridLayoutManager(holder.itemView.context, spain)
        holder.bind(listProducts, adapter)

        adapter.setClickListenerProduct = ProductRowType.ProductClickListener { product, imageView ->
            setOnProductClickListener?.clickProduct(product, imageView)
        }

        adapter.setClickHeartProduct = ProductRowType.ClickListener { productsItem ->
            setOnHeartProductClickListener?.onClick(productsItem)
        }

        adapter.setClickBasketProduct = ProductRowType.ClickListener { productsItem ->
            setOnBasketProductClickListener?.onClick(productsItem)
        }
    }
}