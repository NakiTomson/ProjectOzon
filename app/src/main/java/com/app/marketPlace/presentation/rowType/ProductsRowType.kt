package com.app.marketPlace.presentation.rowType

import androidx.recyclerview.widget.RecyclerView
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
        val productionViewHolder = viewHolder as ViewHolderFactory.ProductViewHolder

        productionViewHolder.bind(listProducts, spain, productItemAdapter)

        productionViewHolder.setClickListenerProduct = ProductRowType.ProductClickListener { product, imageView ->
            setOnProductClickListener?.clickProduct(product, imageView)
        }

        productionViewHolder.setClickHeartProduct = ProductRowType.ClickListener { productsItem ->
            setOnHeartProductClickListener?.onClick(productsItem)
        }

        productionViewHolder.setClickBasketProduct = ProductRowType.ClickListener { productsItem ->
            setOnBasketProductClickListener?.onClick(productsItem)
        }
    }
}