package com.appmarketplace.ozon.presentation.activityes.ui.fragments.favorite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem
import com.appmarketplace.ozon.presentation.adapters.ProductItemAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_products_list.*

class FavoriteFragment : Fragment() {



    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)


        viewModel.getFavoriteProducts()


        favoriteList.layoutManager = GridLayoutManager(activity,2)
        val productsAdapter = ProductItemAdapter()
        productsAdapter.setHasStableIds(true)
        favoriteList.adapter = productsAdapter
        viewModel.productsLive.observe(viewLifecycleOwner, Observer { list ->

            Log.v("TGYHUJIKO","Daada $list")
            productsAdapter.setData(list.map {
                OnProductItem(
                    type = OnProductItem.Type.GetType().getType(it.type),
                    generalIconProductSting = it.iconProduct!!,
                    favoritelIconProduct = it.isFavorite,
//                    productDiscount = it.productDiscount!!,
                    isBestseller = it.isBestseller!!,
                    priceWithDiscount = it.priceWithDiscount!!,
                    priceOlD = it.priceOlD!!,
                    goToBasket = it.goToBasket!!,
                    nameOfProduct = it.nameOfProduct!!,
                    shortDescription = it.shortDescription!!,
                    longDescription = it.longDescription!!,
                    images = it.images!!,
                    company = it.company!!,
                    color = it.color!!,
                )
            })
        })
    }

}