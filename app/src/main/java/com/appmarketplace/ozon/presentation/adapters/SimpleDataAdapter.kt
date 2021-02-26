package com.appmarketplace.ozon.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.domain.modelsUI.CategoryPath
import kotlinx.android.synthetic.main.item_simple.view.*

class SimpleDataAdapter() : RecyclerView.Adapter<SimpleDataAdapter.SimpleDataAdapterViewHolder>() {

    val listItems:MutableList<CategoryPath> = ArrayList()


    var setOnClickCategoryListener:OnClickCategoryListener? = null

    interface OnClickCategoryListener{
        fun onClickCategory(path: String)
    }

    fun setData(list: MutableList<CategoryPath>?){
        listItems.clear()
        list?.let { listItems.addAll(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleDataAdapterViewHolder {
        return SimpleDataAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_simple,parent,false))
    }

    override fun onBindViewHolder(holder: SimpleDataAdapterViewHolder, position: Int) {
        holder.bind(listItems[position])
    }

    override fun getItemCount(): Int {
        return  listItems.size
    }

    inner class SimpleDataAdapterViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val namePath =itemView.namePath
        val rootSimpleItem =itemView.rootSimpleItem
        fun bind(path: CategoryPath){
            namePath.text = path.name
            rootSimpleItem.setOnClickListener {
                path.id?.let { it1 -> setOnClickCategoryListener?.onClickCategory(it1) }
            }
        }
    }
}