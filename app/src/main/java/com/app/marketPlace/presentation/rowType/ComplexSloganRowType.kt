package com.app.marketPlace.presentation.rowType

import android.graphics.Typeface
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.R
import com.app.marketPlace.presentation.interfaces.RowType
import com.app.marketPlace.presentation.factory.ViewHolderFactory

data class ComplexSloganRowType(val item: Slogan) : RowType {

    override fun getItemViewType(): Int {
        return RowType.PRODUCTS_SLOGAN_COMPLEX_TYPE
    }

    val visible = View.VISIBLE
    val gone = View.GONE

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {

        val holder: ViewHolderFactory.ComplexSloganOfferProduct = viewHolder as ViewHolderFactory.ComplexSloganOfferProduct
        holder.bind(item)
        when (item) {
            is Slogan.SetBestseller -> {
                holder.topText.visibility = visible
                holder.topText.text = holder.itemView.context.getString(R.string.bestseller)
                holder.generalText.text = item.company
            }

            is Slogan.SetPrice -> {
                holder.generalText.text = item.actualPrice
                holder.childTextView.text = item.oldPrice
                holder.childTextView.visibility = visible
                holder.imageNext.visibility = visible
                holder.imageNext.setOnClickListener {
                    Toast.makeText(holder.itemView.context, holder.itemView.context.getString(R.string.next), Toast.LENGTH_SHORT).show()
                }
            }
            is Slogan.SetSimpleOffer -> {
                holder.generalText.text = item.offer
                holder.generalText.setPadding(0, 0, 0, 20)
                holder.generalText.textSize = 16f
                holder.generalText.setTypeface(holder.generalText.typeface, Typeface.NORMAL)
                holder.imageNext.visibility = visible
                holder.imageNext.setOnClickListener {
                    Toast.makeText(holder.itemView.context, holder.itemView.context.getString(R.string.next), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    sealed class Slogan {
        class SetBestseller(val company: String) : Slogan()
        class SetPrice(val actualPrice: String, val oldPrice: String) : Slogan()
        class SetSimpleOffer(val offer: String) : Slogan()
    }
}