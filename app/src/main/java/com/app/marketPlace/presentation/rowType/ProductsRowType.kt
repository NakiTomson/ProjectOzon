package com.app.marketPlace.presentation.rowType

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.domain.models.ProductItem
import com.app.marketPlace.presentation.adapters.ProductItemAdapter
import com.app.marketPlace.presentation.factory.ViewHolderFactory
import com.app.marketPlace.presentation.interfaces.RowType


data class ProductsRowType(
    val listProducts: List<ProductItem>,
    val spain: Int,
    val productItemAdapter: ProductItemAdapter
) :RowType {

    var setOnProductClickListener:ProductClickListener? = null

    var setOnHeartProductClickListener: ClickListener? = null

    var setOnBasketProductClickListener: ClickListener? = null


    fun interface ProductClickListener{
        fun clickProduct(product: ProductItem, view: ImageView)
    }

    fun interface ClickListener{
        fun onClick(product: ProductItem)
    }

    override fun getItemViewType(): Int {
        return RowType.PRODUCTS_ROW_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val productionViewHolder = viewHolder as ViewHolderFactory.ProductViewHolder

        productionViewHolder.bind(listProducts, spain, productItemAdapter)

        productionViewHolder.setClickListenerProduct = ProductClickListener { product, imageView ->
            setOnProductClickListener?.clickProduct(product, imageView)
        }

        productionViewHolder.setClickHeartProduct = ClickListener { productsItem ->
            setOnHeartProductClickListener?.onClick(productsItem)
        }

        productionViewHolder.setClickBasketProduct = ClickListener { productsItem ->
            setOnBasketProductClickListener?.onClick(productsItem)
        }
    }
}