package com.app.marketPlace.presentation.rowType

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.presentation.interfaces.RowType
import com.app.marketPlace.presentation.factory.ViewHolderFactory
import com.app.marketPlace.domain.modelsUI.OnProductItem

data class ProductsRowType(val listProducts: List<OnProductItem>, val spain:Int) :RowType {


    var setClickListenerProduct:OnProductClickListener? = null

    var setClickHeartProduct: OnClickListener? = null

    var setClickBasketProduct: OnClickListener? = null

    interface OnProductClickListener{
        fun clickProduct(product: OnProductItem,imageView: ImageView)
    }

    interface OnClickListener{
        fun onClick(productsItem: OnProductItem)
    }

    override fun getItemViewType(): Int {
        return RowType.PRODUCTS_ROW_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val productionViewHolder = viewHolder as ViewHolderFactory.ProductViewHolder
        productionViewHolder.bind(listProducts,spain)

        productionViewHolder.setClickListenerProduct = object :OnProductClickListener{

            override fun clickProduct(product: OnProductItem, imageView: ImageView) {
                setClickListenerProduct?.clickProduct(product,imageView)
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
    }
}