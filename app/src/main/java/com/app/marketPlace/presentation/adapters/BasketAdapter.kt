package com.app.marketPlace.presentation.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.R
import com.app.marketPlace.domain.exception.NotFoundRealizationException
import com.app.marketPlace.domain.models.Product
import com.app.marketPlace.presentation.interfaces.ProductRowType
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_horizontal_product.view.*


class BasketAdapter : RecyclerView.Adapter<BasketAdapter.CategoryOfferItemProductViewHolder>() {

    private var productsList: MutableList<Product>? = arrayListOf()

    var setClickListenerProduct: ProductRowType.ProductClickListener? = null

    var setClickHeartProduct: ProductRowType.ClickListener? = null

    var setOnBasketDelete: ProductRowType.ClickListener? = null


    fun setData(list: List<Product>) {
        productsList?.clear()
        productsList?.addAll(list)
        notifyDataSetChanged()
    }

    fun setData(item: Product) {
        productsList?.add(item)
        notifyDataSetChanged()
    }

    fun deleteProduct(productsItem: Product) {
        productsList?.remove(productsItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryOfferItemProductViewHolder {
        return CategoryOfferItemProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_horizontal_product, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryOfferItemProductViewHolder, position: Int) {
        productsList?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount() = productsList?.size ?: 0

    inner class CategoryOfferItemProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageIcon: ImageView = itemView.generalIconProductImageView
        private val isFavorite:TextView = itemView.favoriteIconProductImageView
        private val priceMinusDiscount: TextView = itemView.priceWithDiscountTextView
        private val price: TextView = itemView.priceOlDTextView
        private val name: TextView = itemView.nameOfProduct
        private val product: ConstraintLayout = itemView.product
        private val delete: TextView = itemView.textViewDelete

        val visible = View.VISIBLE

        fun bind(productsItem: Product) {

            imageIcon.transitionName = productsItem.icon

            when(productsItem.type){
                Product.Type.OnlyImage ->{
                    setOnlyImage(productsItem)
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
                else -> {throw NotFoundRealizationException(productsItem.type)}
            }

            delete.setOnClickListener {
                setOnBasketDelete?.onClick(productsItem)
            }
            product.setOnClickListener {
                productsItem.images?.set(0,productsItem.icon!!)
                setClickListenerProduct?.clickProduct(productsItem,imageIcon)
            }
            isFavorite.setOnClickListener {

                if(!productsItem.isFavorite){
                    productsItem.isFavorite = true

                    isFavorite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_favorite_products_icon_heart, 0, 0, 0)
                    setClickHeartProduct?.onClick(productsItem)
                    Toast.makeText(itemView.context,"Добавлено в избранное",Toast.LENGTH_SHORT).show()
                }else{
                    productsItem.isFavorite = false
                    isFavorite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_unlike_favorite_products_icon_heart, 0, 0, 0)

                    setClickHeartProduct?.onClick(productsItem)
                    Toast.makeText(itemView.context,"Удалено из избранного",Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun setOnlyImage(productsItem: Product) {
            productsItem.icon?.let {
                Picasso.with(itemView.context)
                    .load(productsItem.icon)
                    .into(imageIcon)
            }
        }

        private fun setNonName(productsItem: Product) {
            isFavorite.visibility = visible

            if (productsItem.isFavorite) {
                isFavorite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_favorite_products_icon_heart, 0, 0, 0)
            }

            productsItem.discount?.let {
                priceMinusDiscount.visibility = visible
                price.visibility = visible
                priceMinusDiscount.text = productsItem.priceMinusDiscount
                price.text = productsItem.price
            } ?: run {
                productsItem.priceMinusDiscount?.let {
                    priceMinusDiscount.visibility = visible
                    priceMinusDiscount.text = productsItem.priceMinusDiscount
                    priceMinusDiscount.setTextColor(Color.GRAY)
                }
            }
        }

        private fun setWithName(productsItem: Product) {
            productsItem.name?.let {
                name.visibility = visible
                name.text = it
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return productsList?.get(position)?.hashCode()?.toLong() ?: 0
    }
}