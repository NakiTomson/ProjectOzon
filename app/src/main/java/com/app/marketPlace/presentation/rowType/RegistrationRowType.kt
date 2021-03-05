package com.app.marketPlace.presentation.rowType

import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.presentation.interfaces.RowType
import com.app.marketPlace.presentation.factory.ViewHolderFactory

class RegistrationRowType :RowType {


    var setClickAuthorization:AuthorizationClickListener? = null

    interface AuthorizationClickListener{
        fun onClick()
    }
    override fun getItemViewType(): Int {
        return RowType.REGISTRATION_ROW_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val registrationViewHolder = viewHolder as ViewHolderFactory.RegistrationViewHolder
        registrationViewHolder.bind()
        registrationViewHolder.financeButton.setOnClickListener {
            setClickAuthorization?.onClick()
        }
    }
}