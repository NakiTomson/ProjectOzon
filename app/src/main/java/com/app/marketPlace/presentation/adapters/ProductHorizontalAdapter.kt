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
import com.app.marketPlace.domain.mappers.MapperToDb.Companion.reMapProduct
import com.app.marketPlace.domain.models.Product
import com.app.marketPlace.presentation.interfaces.ProductRowType
import com.squareup.picasso.Callback

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product_horizontal.view.*


class ProductHorizontalAdapter : RecyclerView.Adapter<ProductHorizontalAdapter.CategoryOfferItemProductViewHolder>() {

    private var products: MutableList<Product>? = arrayListOf()

    var setClickListenerProduct: ProductRowType.ProductClickListener? = null

    var setClickHeartProduct: ProductRowType.ClickListener? = null

    var setClickBasketProduct: ProductRowType.ClickListener? = null


    fun setData(list: List<Product>) {
        products?.clear()
        products?.addAll(list)
        notifyDataSetChanged()
    }

    fun deleteProduct(productsItem: Product) {
        products?.remove(productsItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryOfferItemProductViewHolder {
        return CategoryOfferItemProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product_horizontal, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryOfferItemProductViewHolder, position: Int) {
        products?.get(position)?.let { holder.bind(reMapProduct(it)!!) }
    }

    override fun getItemCount() = products?.size ?: 0

    inner class CategoryOfferItemProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val iconProduct: ImageView = itemView.generalIconProductImageView
        private val addToFavorite: ImageView = itemView.favoriteIconProductImageView
        private val productDiscountPercent:TextView = itemView.productDiscountTextView
        private val isBestseller:TextView = itemView.isBestsellerTextView
        private val priceMinusDiscount:TextView = itemView.priceWithDiscountTextView
        private val price:TextView = itemView.priceOlDTextView
        private val addToBasket:TextView = itemView.buttonAddToBasket
        private val nameProduct:TextView = itemView.nameOfProduct
        private val rootLayout:ConstraintLayout = itemView.product
        private val shimmer = itemView.shimmerLayout

        private val visible = View.VISIBLE

        fun bind(productsItem: Product) {
            iconProduct.transitionName = productsItem.icon

            if (productsItem.isBasket){
                addToBasket.text = "В корзине"
                addToBasket.setBackgroundResource(R.drawable.button_added)
            }

            addToBasket.setOnClickListener {
                if (!productsItem.isBasket){
                    productsItem.isBasket = true
                    Toast.makeText(itemView.context,"Добавлено в корзину",Toast.LENGTH_SHORT).show()
//                    buttonAddToBasket.text = "В корзине"
                    addToBasket.setBackgroundResource(R.drawable.button_added)
                    setClickBasketProduct?.onClick(productsItem)
                }else{
                    productsItem.isBasket = false
//                    buttonAddToBasket.text = "В корзину"
                    addToBasket.setBackgroundResource(R.drawable.button_next)
                    Toast.makeText(itemView.context,"Удалено из корзины",Toast.LENGTH_SHORT).show()
                    setClickBasketProduct?.onClick(productsItem)
                }
            }

            when(productsItem.type){

                Product.Type.OnlyImage ->{
                    setOnlyImage(productsItem)
                }
                Product.Type.ProductNoNBasket->{
                    productsItem.isCanGoToBasket = false
                    setOnlyImage(productsItem)
                    setNonName(productsItem)
                    setWithName(productsItem)
                }
                Product.Type.ProductNonName ->{
                    setOnlyImage(productsItem)
                    setNonName(productsItem)
                }
                Product.Type.ProductWithName->{
                    setOnlyImage(productsItem)
                    setNonName(productsItem)
                    setWithName(productsItem)
                }

                Product.Type.ProductHorizontal->{
                    setOnlyImage(productsItem)
                    setNonName(productsItem)
                    setWithName(productsItem)
                }
            }

            rootLayout.setOnClickListener {
                iconProduct.transitionName = productsItem.icon
                productsItem.images?.set(0,productsItem.icon!!)
                setClickListenerProduct?.clickProduct(productsItem,iconProduct)
            }

            addToFavorite.setOnClickListener {
                if(!productsItem.isFavorite){
                    productsItem.isFavorite = true
                    addToFavorite.setImageResource(R.drawable.like_favorite_products_icon_heart)
                    setClickHeartProduct?.onClick(productsItem)
                    Toast.makeText(itemView.context,"Добавлено в избранное",Toast.LENGTH_SHORT).show()
                }else{
                    productsItem.isFavorite = false
                    addToFavorite.setImageResource(R.drawable.unlike_favorite_products_icon_heart)
                    setClickHeartProduct?.onClick(productsItem)
                    Toast.makeText(itemView.context,"Удалено из избранного",Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun setOnlyImage(productsItem: Product) {
            productsItem.icon?.let {
                Picasso.with(itemView.context)
                    .load(productsItem.icon)
                    .into(iconProduct,object :Callback{
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
                priceMinusDiscount.visibility = visible
                price.visibility = visible

                productDiscountPercent.text = productsItem.discount
                priceMinusDiscount.text = productsItem.priceMinusDiscount
                price.text = productsItem.price
            } ?: run {
                productsItem.priceMinusDiscount?.let {
                    priceMinusDiscount.visibility = visible
                    priceMinusDiscount.text = productsItem.priceMinusDiscount
                    priceMinusDiscount.setTextColor(Color.GRAY)
                }
            }

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

    override fun getItemId(position: Int): Long {
        return products?.get(position)?.hashCode()?.toLong() ?: 0
    }
}