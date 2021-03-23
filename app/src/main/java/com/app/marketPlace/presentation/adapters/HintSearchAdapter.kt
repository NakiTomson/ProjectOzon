package com.app.marketPlace.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.R
import kotlinx.android.synthetic.main.item_hint_search.view.*

class HintSearchAdapter : RecyclerView.Adapter<HintSearchAdapter.ViewHolderHintSearchProducts>() {

    private var listHits:ArrayList<String> = ArrayList()

    lateinit var setOnHintProductsClickListener: HintProductsListener

    fun interface HintProductsListener {
        fun onHintSelected(hintProduct: String)
    }

    fun setHints(hint: MutableList<String>){
        listHits.clear()
        listHits.addAll(hint)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHintSearchProducts {
        return ViewHolderHintSearchProducts(LayoutInflater.from(parent.context).inflate(R.layout.item_hint_search, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderHintSearchProducts, position: Int) {
        listHits[position].let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return listHits.size
    }

    override fun getItemId(position: Int): Long {
        return listHits[position].hashCode().toLong()
    }

    inner class ViewHolderHintSearchProducts(itemView: View):RecyclerView.ViewHolder(itemView){

        private val texViewHint = itemView.exampleSearchHints

        fun bind(hint: String){
            texViewHint.text = hint
            texViewHint.setOnClickListener {
                setOnHintProductsClickListener.onHintSelected(texViewHint.text.toString())
            }
        }
    }
}