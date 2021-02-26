package com.appmarketplace.ozon.presentation.rowType

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.presentation.Interfaces.RowType
import com.appmarketplace.ozon.presentation.factory.ViewHolderFactory

class RegistrationRowType :RowType {


    var setClickAutorization:onClickAutorization? = null

    interface onClickAutorization{
        fun onClick()
    }
    override fun getItemViewType(): Int {
        return RowType.REGISTRATION_ROW_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val registrationViewHolder = viewHolder as ViewHolderFactory.RegistrationViewHolder
        registrationViewHolder.bind()
        registrationViewHolder.financeButton.setOnClickListener {
            setClickAutorization?.onClick()
        }
    }
}