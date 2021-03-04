package com.appmarketplace.ozon.presentation.rowType

import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.presentation.Interfaces.RowType
import com.appmarketplace.ozon.presentation.factory.ViewHolderFactory
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem

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
                Log.v("TAGLCICK","CKCUICL2")
                setClickBasketProduct?.onClick(productsItem)
            }
        }
    }
}