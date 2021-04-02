package com.app.marketPlace.presentation.interfaces

import androidx.recyclerview.widget.RecyclerView

interface RowType {

    fun getItemViewType(): Int

    fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?)

    companion object{
        var BANNER_ROW_TYPE = 0
        var CATEGORY_ROW_TYPE = 1
        var HISTORY_ROW_TYPE = 2
        var LIVE_ROW_TYPE = 3
        var REGISTRATION_ROW_TYPE = 4
        var PRODUCTS_ROW_TYPE = 5
        var PRODUCTS_SLOGAN_TOP_TYPE = 6
        var PRODUCTS_SLOGAN_BOTTOM_TYPE = 7
        var PRODUCTS_SLOGAN_COMPLEX_TYPE = 8
        var PRODUCTS_HORIZONTAL_ROW_TYPE = 9
    }
}