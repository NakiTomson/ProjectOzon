package com.appmarketplace.ozon.presentation.data

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.presentation.Interfaces.RowType
import com.appmarketplace.ozon.presentation.factory.ViewHolderFactory

class RegistrationRowType:RowType {

    override fun getItemViewType(): Int {
        return RowType.REGISTRATION_ROW_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val registrationViewHolder = viewHolder as ViewHolderFactory.RegistrationViewHolder
        registrationViewHolder.bind()
        registrationViewHolder.financeButton.setOnClickListener {
            Toast.makeText(viewHolder.itemView.context,"Пошли в регистрацию", Toast.LENGTH_SHORT).show()
        }
    }
}