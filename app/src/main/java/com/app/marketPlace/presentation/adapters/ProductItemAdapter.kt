package com.app.marketPlace.presentation.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.R
import com.app.marketPlace.domain.mappers.Mapper.reMapProduct
import com.app.marketPlace.domain.models.ProductItem
import com.app.marketPlace.presentation.rowType.ProductsRowType

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product.view.*


class ProductItemAdapter : RecyclerView.Adapter<ProductItemAdapter.CategoryOfferItemProductViewHolder>() {

    private var listOnProductsByOfferItems: MutableList<ProductItem>? = arrayListOf()

    var setClickListenerProduct: ProductsRowType.ProductClickListener? = null

    var setClickHeartProduct: ProductsRowType.ClickListener? = null

    var setClickBasketProduct: ProductsRowType.ClickListener? = null


    fun setData(list: List<ProductItem>) {
        listOnProductsByOfferItems?.clear()
        listOnProductsByOfferItems?.addAll(list)
        notifyDataSetChanged()
    }

    fun deleteProduct(productsItem: ProductItem) {
        listOnProductsByOfferItems?.remove(productsItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryOfferItemProductViewHolder {
        return CategoryOfferItemProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryOfferItemProductViewHolder, position: Int) {
        listOnProductsByOfferItems?.get(position)?.let { holder.bind( reMapProduct(it)) }
    }

    override fun getItemCount() = listOnProductsByOfferItems?.size ?: 0

    inner class CategoryOfferItemProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val generalIconProductImageView: ImageView = itemView.generalIconProductImageView
        private val favoriteIconProductImageView: ImageView = itemView.favoriteIconProductImageView
        private val productDiscountTextViewPercent:TextView = itemView.productDiscountTextView
        private val isBestsellerTextView:TextView = itemView.isBestsellerTextView
        private val priceWithDiscountTextView:TextView = itemView.priceWithDiscountTextView
        private val priceOlDTextView:TextView = itemView.priceOlDTextView
        private val buttonAddToBasket:TextView = itemView.buttonAddToBasket
        private val productItemTextView:TextView = itemView.nameOfProduct
        private val product:ConstraintLayout = itemView.product

        private val visible = View.VISIBLE

        fun bind(productsItem: ProductItem) {
            generalIconProductImageView.transitionName = productsItem.generalIconProductSting

            if (productsItem.productInBasket){
                buttonAddToBasket.text = "В корзине"
                buttonAddToBasket.setBackgroundResource(R.drawable.button_added)
            }

            buttonAddToBasket.setOnClickListener {
                Log.v("BINFS","REEE 2")
                if (!productsItem.productInBasket){
                    productsItem.productInBasket = true
                    Toast.makeText(itemView.context,"Добавлено в корзину",Toast.LENGTH_SHORT).show()
//                    buttonAddToBasket.text = "В корзине"
                    buttonAddToBasket.setBackgroundResource(R.drawable.button_added)
                    setClickBasketProduct?.onClick(productsItem)
                }else{
                    productsItem.productInBasket = false
//                    buttonAddToBasket.text = "В корзину"
                    buttonAddToBasket.setBackgroundResource(R.drawable.button_next)
                    Toast.makeText(itemView.context,"Удалено из корзины",Toast.LENGTH_SHORT).show()
                    setClickBasketProduct?.onClick(productsItem)
                }
            }

            when(productsItem.type){

                ProductItem.Type.OnlyImage ->{
                    setOnlyImage(productsItem)
                }
                ProductItem.Type.ProductNonName ->{
                    setOnlyImage(productsItem)
                    setNonName(productsItem)
                }
                ProductItem.Type.ProductWithName->{
                    setOnlyImage(productsItem)
                    setNonName(productsItem)
                    setWithName(productsItem)
                }
            }

            product.setOnClickListener {
                generalIconProductImageView.transitionName = productsItem.generalIconProductSting
                productsItem.images?.set(0,productsItem.generalIconProductSting!!)
                setClickListenerProduct?.clickProduct(productsItem,generalIconProductImageView)
            }

            favoriteIconProductImageView.setOnClickListener {
                if(!productsItem.favoriteIconProduct){
                    productsItem.favoriteIconProduct = true
                    favoriteIconProductImageView.setImageResource(R.drawable.like_favorite_products_icon_heart)
                    setClickHeartProduct?.onClick(productsItem)
                    Toast.makeText(itemView.context,"Добавлено в избранное",Toast.LENGTH_SHORT).show()
                }else{
                    productsItem.favoriteIconProduct = false
                    favoriteIconProductImageView.setImageResource(R.drawable.unlike_favorite_products_icon_heart)
                    setClickHeartProduct?.onClick(productsItem)
                    Toast.makeText(itemView.context,"Удалено из избранного",Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun setOnlyImage(productsItem: ProductItem) {
            productsItem.generalIconProductSting?.let {
                Picasso.with(itemView.context)
                    .load(productsItem.generalIconProductSting)
                    .into(generalIconProductImageView)
            } ?: run {
                productsItem.generalIconProduct?.let { generalIconProductImageView.setImageResource(it) } ?:
                kotlin.run{generalIconProductImageView.setImageResource(R.drawable.product_by_offer_example)}
            }
        }

        private fun setNonName(productsItem: ProductItem) {

            favoriteIconProductImageView.visibility = visible

            if (productsItem.favoriteIconProduct) {
                favoriteIconProductImageView.setImageResource(R.drawable.like_favorite_products_icon_heart)
            }

            productsItem.productDiscount?.let {
                productDiscountTextViewPercent.visibility = visible
                priceWithDiscountTextView.visibility = visible
                priceOlDTextView.visibility = visible

                productDiscountTextViewPercent.text = productsItem.productDiscount
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

        private fun setWithName(productsItem: ProductItem) {
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