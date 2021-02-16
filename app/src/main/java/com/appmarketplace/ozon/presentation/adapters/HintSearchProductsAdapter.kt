package com.appmarketplace.ozon.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.R
import kotlinx.android.synthetic.main.item_hint_search.view.*

class HintSearchProductsAdapter (): RecyclerView.Adapter<HintSearchProductsAdapter.ViewHolderHintSearchProducts>() {

    var listHits:ArrayList<String> = ArrayList()


    fun setHints(hint: MutableList<String>){
        listHits.clear()

        listHits.addAll(hint)
        notifyDataSetChanged()
    }

    fun setHint(hint: String, position: Int){
        listHits.add(position, hint)
        notifyItemChanged(position)
    }

    fun setHint(hint: String){
        listHits.add(hint)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHintSearchProducts {
        return ViewHolderHintSearchProducts(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_hint_search,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolderHintSearchProducts, position: Int) {
        listHits[position].let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return listHits.size ?: 0
    }


    override fun getItemId(position: Int): Long {
        return listHits[position].hashCode().toLong()
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(hasStableIds)
    }

    class ViewHolderHintSearchProducts(itemView: View):RecyclerView.ViewHolder(itemView){

        val texViewHint = itemView.exampleSearchHitns

        fun bind(hint: String){
            texViewHint.text = hint
        }
    }
}