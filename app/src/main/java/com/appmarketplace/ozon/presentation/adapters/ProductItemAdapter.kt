package com.appmarketplace.ozon.presentation.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.R

import com.appmarketplace.ozon.presentation.pojo.OnProductItem
import kotlinx.android.synthetic.main.item_product.view.*

class ProductItemAdapter(val listOnProductsByOfferItems: List<OnProductItem>) : RecyclerView.Adapter<ProductItemAdapter.CategoryOfferItemProductViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryOfferItemProductViewHolder {
        return CategoryOfferItemProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false))
    }

    override fun onBindViewHolder(holder: CategoryOfferItemProductViewHolder, position: Int) {
        holder.bind(listOnProductsByOfferItems[position])
    }

    override fun getItemCount() = listOnProductsByOfferItems.size

    inner class CategoryOfferItemProductViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val generalIconProductImageView = itemView.generalIconProductImageView


        val favoritelIconProductImageView = itemView.favoritelIconProductImageView
        val productDiscountTextViewProzent = itemView.productDiscountTextView
        val isBestsellerTextView = itemView.isBestsellerTextView
        val priceWithDiscountTextView = itemView.priceWithDiscountTextView
        val priceOlDTextView = itemView.priceOlDTextView
        val buttonAddToBasket = itemView.buttonAddToBasket

        fun bind(productsItem: OnProductItem) {
            val visible= View.VISIBLE
            generalIconProductImageView.setImageResource(productsItem.generalIconProduct)

            productsItem.favoritelIconProduct?.let {favoritelIconProduct->
                favoritelIconProductImageView.visibility = visible
                favoritelIconProductImageView.setImageResource(favoritelIconProduct)
            }

            productsItem.productDiscount?.let {
                productDiscountTextViewProzent.visibility = visible
                priceWithDiscountTextView.visibility = visible
                priceOlDTextView.visibility = visible

                productDiscountTextViewProzent.text =productsItem.productDiscount
                priceWithDiscountTextView.text = productsItem.priceWithDiscount
                priceOlDTextView.text = productsItem.priceOlD
            }?: run {
                productsItem.priceWithDiscount?.let {
                    priceWithDiscountTextView.visibility = visible
                    priceWithDiscountTextView.text = productsItem.priceWithDiscount
                    priceWithDiscountTextView.setTextColor(Color.GRAY)
                }

            }

            if (productsItem.isBestseller){
                isBestsellerTextView.visibility = View.VISIBLE
            }


            if (productsItem.goToBasket){
                buttonAddToBasket.visibility = View.VISIBLE
            }
        }
    }

}