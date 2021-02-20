package com.appmarketplace.ozon.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.pojo.OnLiveItem
import com.appmarketplace.ozon.presentation.pojo.OnOfferProductsItem


class ContainerProductsAdapter(val spain:Int) :
    RecyclerView.Adapter<ContainerProductsAdapter.OnProductsByOfferAdapterViewHolder>() {

    val listOnProductsByOfferItems: MutableList<OnOfferProductsItem> = ArrayList()

    fun setData(items:MutableList<OnOfferProductsItem>) {
        listOnProductsByOfferItems.clear()
        listOnProductsByOfferItems.addAll(items)
        notifyDataSetChanged()
    }

    fun setItem(position: Int,items: OnOfferProductsItem) {
        listOnProductsByOfferItems[position] = items
        notifyItemChanged(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnProductsByOfferAdapterViewHolder {
        return OnProductsByOfferAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_container_products,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OnProductsByOfferAdapterViewHolder, position: Int) {
        holder.bind(listOnProductsByOfferItems[position])
    }

    override fun getItemCount(): Int {
        return listOnProductsByOfferItems.size
    }

    inner class OnProductsByOfferAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val topStringOffer = itemView.findViewById<TextView>(R.id.productsCategoryOffer)
        private val bottonStringOffer  = itemView.findViewById<TextView>(R.id.textViewBottonOffer)
        private val imageNextAllFilms  = itemView.findViewById<ImageView>(R.id.imageNextAllFilms)
        private val prodctsRecyclerView  = itemView.findViewById<RecyclerView>(R.id.recyclerViewCategory)

        fun bind(onProductsByOfferItem: OnOfferProductsItem) {

            onProductsByOfferItem.topStringOffer?.let {
                topStringOffer.visibility = View.VISIBLE
                topStringOffer.text = onProductsByOfferItem.topStringOffer
            }

            onProductsByOfferItem.bottonStringOffer?.let {
                imageNextAllFilms.visibility = View.VISIBLE
                bottonStringOffer.visibility = View.VISIBLE
                bottonStringOffer.text = onProductsByOfferItem.bottonStringOffer
            }


            prodctsRecyclerView.layoutManager = GridLayoutManager(itemView.context, spain)
            val productAdapter = ProductItemAdapter()
            productAdapter.setData(onProductsByOfferItem.list)
            prodctsRecyclerView.adapter = productAdapter

        }
    }

}