package com.appmarketplace.ozon.presentation.data

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.Interfaces.RowType
import com.appmarketplace.ozon.presentation.adapters.BannerAdapter
import com.appmarketplace.ozon.presentation.factory.ViewHolderFactory

data class BottomSloganRowType(val sloganOffer: String) :RowType{

    override fun getItemViewType(): Int {
        return RowType.PRODUCTS_SLOGAN_BOTTOM_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val bunnerViewHolder: ViewHolderFactory.BottomSloganOfferProduct = viewHolder as  ViewHolderFactory.BottomSloganOfferProduct
        bunnerViewHolder.bind(sloganOffer)

    }
}