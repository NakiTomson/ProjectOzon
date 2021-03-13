package com.app.marketPlace.presentation.rowType

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.domain.modelsUI.OnProductItem
import com.app.marketPlace.presentation.adapters.ProductItemAdapter
import com.app.marketPlace.presentation.factory.ViewHolderFactory
import com.app.marketPlace.presentation.interfaces.RowType


data class ProductsRowType(
    val listProducts: List<OnProductItem>,
    val spain: Int,
    val productItemAdapter: ProductItemAdapter
) :RowType {


    var setOnProductClickListener:ProductClickListener? = null

    var setOnHeartProductClickListener: ClickListener? = null

    var setOnBasketProductClickListener: ClickListener? = null

//    var wasSetup:Boolean = false

    fun interface ProductClickListener{
        fun clickProduct(product: OnProductItem, imageView: ImageView)
    }

    fun interface ClickListener{
        fun onClick(product: OnProductItem)
    }

    override fun getItemViewType(): Int {
        return RowType.PRODUCTS_ROW_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
//        if (wasSetup) return


        val productionViewHolder = viewHolder as ViewHolderFactory.ProductViewHolder
        productionViewHolder.bind(listProducts, spain, productItemAdapter)



        productionViewHolder.setClickListenerProduct = object :ProductClickListener{

            override fun clickProduct(product: OnProductItem, imageView: ImageView) {
                setOnProductClickListener?.clickProduct(product, imageView)
            }
        }
        productionViewHolder.setClickHeartProduct = object :ClickListener{
            override fun onClick(productsItem: OnProductItem) {
                setOnHeartProductClickListener?.onClick(productsItem)
            }
        }
        productionViewHolder.setClickBasketProduct = object :ClickListener{
            override fun onClick(productsItem: OnProductItem) {
                setOnBasketProductClickListener?.onClick(productsItem)
            }
        }
//        wasSetup = true
    }
}