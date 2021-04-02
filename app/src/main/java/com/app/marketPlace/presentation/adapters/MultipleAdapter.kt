package com.app.marketPlace.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.presentation.factory.ViewHolderFactory.create
import com.app.marketPlace.presentation.interfaces.RowType
import java.util.*

class MultipleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataSet: MutableList<RowType> = ArrayList(30)

    var setNextDataListener: OnNextDataListener? = null

    fun interface OnNextDataListener {
        fun onNextData(size: Int)
    }

    @Volatile
    private var countPosition = -1

    fun setData(dataSets: RowType) {
        dataSet.add(dataSets)
        ++countPosition
        notifyItemChanged(countPosition)
    }

    override fun getItemViewType(position: Int): Int {
        return dataSet[position].getItemViewType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return create(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        dataSet[position].onBindViewHolder(holder)
        if (position > dataSet.size - 2 && setNextDataListener != null) {
            setNextDataListener!!.onNextData(dataSet.size)
        }
    }

    override fun getItemId(position: Int): Long {
        val id = dataSet[position].hashCode().toString().replace("-", "")
        return id.toLong()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}