package com.app.marketPlace.presentation.rowType

import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.presentation.interfaces.RowType
import com.app.marketPlace.presentation.factory.ViewHolderFactory

data class RegistrationRowType(val randomId: Double = Math.random() *123) :RowType {

    var setOnAuthorizationClickListener:AuthorizationClickListener? = null

    fun interface AuthorizationClickListener{
        fun onClick()
    }
    override fun getItemViewType(): Int {
        return RowType.REGISTRATION_ROW_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val holder = viewHolder as ViewHolderFactory.RegistrationViewHolder
        holder.bind()
        holder.financeButton.setOnClickListener {
            setOnAuthorizationClickListener?.onClick()
        }
    }
}