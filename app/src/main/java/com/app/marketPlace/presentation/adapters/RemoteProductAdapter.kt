package com.app.marketPlace.presentation.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.R
import com.app.marketPlace.domain.mappers.MapperToDb
import com.app.marketPlace.domain.models.Product
import com.app.marketPlace.presentation.interfaces.ProductRowType
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product.view.*


class RemoteProductAdapter : PagingDataAdapter<Product,RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    companion object {

        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.skuId == newItem.skuId
            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? CategoryOfferItemProductViewHolder)?.bind( MapperToDb.reMapProduct(getItem(position)))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CategoryOfferItemProductViewHolder.getInstance(parent)
    }

    internal class CategoryOfferItemProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        companion object {
            fun getInstance(parent: ViewGroup): CategoryOfferItemProductViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.item_product, parent, false)
                return CategoryOfferItemProductViewHolder(view)
            }
            var setClickListenerProduct: ProductRowType.ProductClickListener? = null

            var setClickHeartProduct: ProductRowType.ClickListener? = null

            var setClickBasketProduct: ProductRowType.ClickListener? = null
        }

        private val iconProduct: ImageView = itemView.generalIconProductImageView
        private val addToFavorite: ImageView = itemView.favoriteIconProductImageView
        private val productDiscountPercent: TextView = itemView.productDiscountTextView
        private val isBestseller: TextView = itemView.isBestsellerTextView
        private val priceMinusDiscount: TextView = itemView.priceWithDiscountTextView
        private val price: TextView = itemView.priceOlDTextView
        private val addToBasket: TextView = itemView.buttonAddToBasket
        private val nameProduct: TextView = itemView.nameOfProduct
        private val rootLayout: ConstraintLayout = itemView.product
        private val shimmer = itemView.shimmerLayout

        private val visible = View.VISIBLE

        fun bind(product:Product?) {
            if (product == null)return

            iconProduct.transitionName = product.icon
            if (product.isBasket){
                addToBasket.text = "В корзине"
                addToBasket.setBackgroundResource(R.drawable.button_added)
            }

            addToBasket.setOnClickListener {
                if (!product.isBasket){
                    product.isBasket = true
                    Toast.makeText(itemView.context, "Добавлено в корзину", Toast.LENGTH_SHORT).show()
                    addToBasket.setBackgroundResource(R.drawable.button_added)
                    setClickBasketProduct?.onClick(product)
                }else{
                    product.isBasket = false
                    addToBasket.setBackgroundResource(R.drawable.button_next)
                    Toast.makeText(itemView.context, "Удалено из корзины", Toast.LENGTH_SHORT).show()
                    setClickBasketProduct?.onClick(product)
                }
            }

            when(product.type){

                Product.Type.OnlyImage -> {
                    setOnlyImage(product)
                }
                Product.Type.ProductNoNBasket -> {
                    product.isCanGoToBasket = false
                    setOnlyImage(product)
                    setNonName(product)
                    setWithName(product)
                }
                Product.Type.ProductNonName -> {
                    setOnlyImage(product)
                    setNonName(product)
                }
                Product.Type.ProductWithName -> {
                    setOnlyImage(product)
                    setNonName(product)
                    setWithName(product)
                }

                Product.Type.ProductHorizontal -> {
                    setOnlyImage(product)
                    setNonName(product)
                    setWithName(product)
                }
            }

            rootLayout.setOnClickListener {
                product.images?.set(0, product.icon!!)
                setClickListenerProduct?.clickProduct(product, iconProduct)
            }

            addToFavorite.setOnClickListener {
                if(!product.isFavorite){
                    product.isFavorite = true
                    addToFavorite.setImageResource(R.drawable.like_favorite_products_icon_heart)
                    setClickHeartProduct?.onClick(product)
                    Toast.makeText(itemView.context, "Добавлено в избранное", Toast.LENGTH_SHORT).show()
                }else{
                    product.isFavorite = false
                    addToFavorite.setImageResource(R.drawable.unlike_favorite_products_icon_heart)
                    setClickHeartProduct?.onClick(product)
                    Toast.makeText(itemView.context, "Удалено из избранного", Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun setOnlyImage(productsItem: Product) {
            productsItem.icon?.let {
                Picasso.with(itemView.context)
                    .load(productsItem.icon)
                    .into(iconProduct, object : Callback {
                        override fun onSuccess() {
                            shimmer.stopShimmer()
                            shimmer.setShimmer(null)
                        }

                        override fun onError() {
                            shimmer.stopShimmer()
                            shimmer.setShimmer(null)
                        }
                    })
            }
        }

        private fun setNonName(productsItem: Product) {
            addToFavorite.visibility = visible

            if (productsItem.isFavorite) {
                addToFavorite.setImageResource(R.drawable.like_favorite_products_icon_heart)
            }

            productsItem.discount?.let {
                productDiscountPercent.visibility = visible
                price.visibility = visible
                productDiscountPercent.text = productsItem.discount
                price.text = productsItem.price
            } ?: run {
                productsItem.priceMinusDiscount?.let {
                    priceMinusDiscount.setTextColor(Color.GRAY)
                }
            }

            priceMinusDiscount.text = productsItem.priceMinusDiscount
            priceMinusDiscount.visibility = visible

            if (productsItem.isBestseller) {
                isBestseller.visibility = View.VISIBLE
            }

            if (productsItem.isCanGoToBasket) {
                addToBasket.visibility = View.VISIBLE
            }
        }

        private fun setWithName(productsItem: Product) {
            productsItem.name?.let {
                nameProduct.visibility = visible
                nameProduct.text = it
            }
        }
    }
}