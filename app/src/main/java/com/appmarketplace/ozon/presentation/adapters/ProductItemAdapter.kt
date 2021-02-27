package com.appmarketplace.ozon.presentation.adapters

import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem
import com.appmarketplace.ozon.presentation.rowType.ProductsRowType
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.URL
import java.net.URLConnection
import kotlin.concurrent.thread


class ProductItemAdapter() : RecyclerView.Adapter<ProductItemAdapter.CategoryOfferItemProductViewHolder>() {

    var listOnProductsByOfferItems: MutableList<OnProductItem>? = arrayListOf()

    var setClickListenerProduct: ProductsRowType.OnClickProduct? = null

    var setClickHeartProduct: ProductsRowType.OnClickHeart? = null


    fun setData(list: List<OnProductItem>) {
        listOnProductsByOfferItems?.clear()
        listOnProductsByOfferItems?.addAll(list)
        notifyDataSetChanged()
    }

    fun deleteProduct(productsItem: OnProductItem) {
        listOnProductsByOfferItems?.remove(productsItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryOfferItemProductViewHolder {
        return CategoryOfferItemProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryOfferItemProductViewHolder, position: Int) {
        listOnProductsByOfferItems?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount() = listOnProductsByOfferItems?.size ?: 0

    inner class CategoryOfferItemProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val generalIconProductImageView = itemView.generalIconProductImageView

        val favoritelIconProductImageView = itemView.favoritelIconProductImageView
        val productDiscountTextViewProzent = itemView.productDiscountTextView
        val isBestsellerTextView = itemView.isBestsellerTextView
        val priceWithDiscountTextView = itemView.priceWithDiscountTextView
        val priceOlDTextView = itemView.priceOlDTextView
        val buttonAddToBasket = itemView.buttonAddToBasket
        val productItemTextView = itemView.nameOfProduct
        val product = itemView.product

        val visible = View.VISIBLE

        fun bind(productsItem: OnProductItem) {


            when(productsItem.type.type){

                OnProductItem.Type.OnlyImage().type ->{
                    setOnlyImage(productsItem)
                }
                OnProductItem.Type.ProductNonName().type ->{
                    setOnlyImage(productsItem)
                    setNonName(productsItem)
                }
                OnProductItem.Type.ProductWithName().type->{
                    setOnlyImage(productsItem)
                    setNonName(productsItem)
                    setWithName(productsItem)
                }
            }

            product.setOnClickListener {
                generalIconProductImageView.transitionName = productsItem.images?.get(0)
                setClickListenerProduct?.clickProduct(productsItem,generalIconProductImageView)
            }
            favoritelIconProductImageView.setOnClickListener {

                if(!productsItem.favoritelIconProduct){
                    productsItem.favoritelIconProduct = true
                    favoritelIconProductImageView.setImageResource(R.drawable.like_favorite_products_icon_heart)
                    setClickHeartProduct?.onClickHeart(productsItem)
                    Toast.makeText(itemView.context,"Добавлено в избранное",Toast.LENGTH_SHORT).show()
                }else{
                    productsItem.favoritelIconProduct = false
                    favoritelIconProductImageView.setImageResource(R.drawable.unlike_favorite_products_icon_heart)
                    setClickHeartProduct?.onClickHeart(productsItem)
                    Toast.makeText(itemView.context,"Удалено из избранного",Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun setOnlyImage(productsItem: OnProductItem) {
            productsItem.generalIconProductSting?.let {
                Picasso.with(itemView.context)
                    .load(productsItem.generalIconProductSting)
                    .into(generalIconProductImageView)
            } ?: run {
                productsItem.generalIconProduct?.let { generalIconProductImageView.setImageResource(it) } ?:
                kotlin.run{generalIconProductImageView.setImageResource(R.drawable.product_by_offer_exmplae)}
            }
        }

        private fun setNonName(productsItem: OnProductItem) {

            favoritelIconProductImageView.visibility = visible

            if (productsItem.favoritelIconProduct) {
                favoritelIconProductImageView.setImageResource(R.drawable.like_favorite_products_icon_heart)
            }

            productsItem.productDiscount?.let {
                productDiscountTextViewProzent.visibility = visible
                priceWithDiscountTextView.visibility = visible
                priceOlDTextView.visibility = visible

                productDiscountTextViewProzent.text = productsItem.productDiscount
                priceWithDiscountTextView.text = productsItem.priceWithDiscount
                priceOlDTextView.text = productsItem.priceOlD
            } ?: run {
                productsItem.priceWithDiscount?.let {
                    priceWithDiscountTextView.visibility = visible
                    priceWithDiscountTextView.text = productsItem.priceWithDiscount
                    priceWithDiscountTextView.setTextColor(Color.GRAY)
                }
            }

            if (productsItem.isBestseller) {
                isBestsellerTextView.visibility = View.VISIBLE
            }


            if (productsItem.goToBasket) {
                buttonAddToBasket.visibility = View.VISIBLE
            }

        }

        private fun setWithName(productsItem: OnProductItem) {
            productsItem.nameOfProduct?.let {
                productItemTextView.visibility = visible
                productItemTextView.text = it
            }
        }

    }



    override fun getItemId(position: Int): Long {
        return listOnProductsByOfferItems?.get(position)?.hashCode()?.toLong() ?: 0
    }



}