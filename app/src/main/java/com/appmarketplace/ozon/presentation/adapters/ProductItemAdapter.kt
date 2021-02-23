package com.appmarketplace.ozon.presentation.adapters

import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem
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

    fun setData(list: List<OnProductItem>) {
        listOnProductsByOfferItems?.clear()
        listOnProductsByOfferItems?.addAll(list)
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

        fun bind(productsItem: OnProductItem) {
            val visible = View.VISIBLE


            productsItem.generalIconProductSting?.let {

                val newurl = URL(productsItem.generalIconProductSting)

                thread {
                    val connection: URLConnection = newurl.openConnection()
                    val inputStream: InputStream = connection.getInputStream()
                    val productIcon = BitmapFactory.decodeStream(inputStream)
                    inputStream.close()
                    GlobalScope.launch(Dispatchers.Main) {
                        generalIconProductImageView.setImageBitmap(productIcon)
                    }
                }

                // Не работает 403 Forrible
                Picasso.get()
                    .load(productsItem.generalIconProductSting)
                    .noFade()
                    .into(generalIconProductImageView)

            } ?: run {
                productsItem.generalIconProduct?.let { generalIconProductImageView.setImageResource(it) } ?:
                kotlin.run{generalIconProductImageView.setImageResource(R.drawable.product_by_offer_exmplae)}
            }


            if (productsItem.favoritelIconProduct) {
                favoritelIconProductImageView.visibility = visible
                favoritelIconProductImageView.setImageResource(R.drawable.unlike_favorite_products_icon_heart)
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

            productsItem.nameOfProduct?.let {
                productItemTextView.visibility = View.VISIBLE
                productItemTextView.text = it
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return listOnProductsByOfferItems?.get(position)?.hashCode()?.toLong() ?: 0
    }

}