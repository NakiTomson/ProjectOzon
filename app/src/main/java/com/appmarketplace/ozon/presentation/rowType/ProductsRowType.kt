package com.appmarketplace.ozon.presentation.rowType

import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.presentation.Interfaces.RowType
import com.appmarketplace.ozon.presentation.factory.ViewHolderFactory
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem

data class ProductsRowType(val listProducts: List<OnProductItem>, val spain:Int) :RowType {


    var setClickListenerProduct:OnClickProduct? = null

    interface OnClickProduct{
        fun clickProduct(product: OnProductItem)
    }


    override fun getItemViewType(): Int {
        return RowType.PRODUCTS_ROW_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val productionViewHolder = viewHolder as ViewHolderFactory.ProductViewHolder
        productionViewHolder.bind(listProducts,spain)

        productionViewHolder.setClickListenerProduct = object :OnClickProduct{
            override fun clickProduct(product: OnProductItem) {
                setClickListenerProduct?.clickProduct(product)
            }

        }
    }
}