package com.app.marketPlace.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.R
import com.app.marketPlace.domain.models.CategoryPath
import kotlinx.android.synthetic.main.item_simple.view.*

class SimpleAdapter : RecyclerView.Adapter<SimpleAdapter.SimpleDataAdapterViewHolder>() {

    private val listItems:MutableList<CategoryPath> = ArrayList()

    var setOnClickCategoryListener:OnClickCategoryListener? = null

    fun interface OnClickCategoryListener{
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
        //ExoPlayer
        return  listItems.size
    }

    inner class SimpleDataAdapterViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        private val namePath: TextView =itemView.namePath
        private val rootSimpleItem:ConstraintLayout =itemView.rootSimpleItem
        fun bind(path: CategoryPath){
            namePath.text = path.name
            rootSimpleItem.setOnClickListener {
                path.id?.let { it1 -> setOnClickCategoryListener?.onClickCategory(it1) }
            }
        }
    }
}