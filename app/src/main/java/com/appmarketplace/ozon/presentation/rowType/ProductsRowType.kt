package com.appmarketplace.ozon.presentation.rowType

import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.presentation.Interfaces.RowType
import com.appmarketplace.ozon.presentation.factory.ViewHolderFactory
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem

data class ProductsRowType(val listProducts: List<OnProductItem>, val spain:Int) :RowType {


    var setClickListenerProduct:OnClickProduct? = null

    var setClickHeartProduct: OnClickHeart? = null

    var setClickBasketProduct: OnClickHeart? = null

    interface OnClickProduct{
        fun clickProduct(product: OnProductItem,imageView: ImageView)
    }

    interface OnClickHeart{
        fun onClickHeart(productsItem: OnProductItem)
    }

    override fun getItemViewType(): Int {
        return RowType.PRODUCTS_ROW_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val productionViewHolder = viewHolder as ViewHolderFactory.ProductViewHolder
        productionViewHolder.bind(listProducts,spain)

        productionViewHolder.setClickListenerProduct = object :OnClickProduct{

            override fun clickProduct(product: OnProductItem, imageView: ImageView) {
                setClickListenerProduct?.clickProduct(product,imageView)
            }
        }
        productionViewHolder.setClickHeartProduct = object :OnClickHeart{
            override fun onClickHeart(productsItem: OnProductItem) {
                setClickHeartProduct?.onClickHeart(productsItem)
            }
        }
        productionViewHolder.setClickBasketProduct = object :OnClickHeart{
            override fun onClickHeart(productsItem: OnProductItem) {
                Log.v("TAGLCICK","CKCUICL2")
                setClickBasketProduct?.onClickHeart(productsItem)
            }
        }
    }
}