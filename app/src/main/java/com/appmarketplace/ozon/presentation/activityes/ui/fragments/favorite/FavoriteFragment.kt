package com.appmarketplace.ozon.presentation.activityes.ui.fragments.favorite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem
import com.appmarketplace.ozon.presentation.adapters.ProductItemAdapter
import com.appmarketplace.ozon.presentation.rowType.ProductsRowType
import kotlinx.android.synthetic.main.fragment_favorite.*

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



        favoriteList.layoutManager = GridLayoutManager(activity,2)
        val productsAdapter = ProductItemAdapter()
        productsAdapter.setHasStableIds(true)
        favoriteList.adapter = productsAdapter

        productsAdapter.setClickHeartProduct = object :ProductsRowType.OnClickHeart{
            override fun onClickHeart(productsItem: OnProductItem) {
                viewModel.deleteProduct(productsItem)
                productsAdapter.deleteProduct(productsItem)
            }
        }

        viewModel.getFavoriteProducts()
        viewModel.productsLive.observe(viewLifecycleOwner, Observer { list ->

            productsAdapter.setData(list.map {
                OnProductItem(
                    type = OnProductItem.Type.ProductWithName(),
                    generalIconProductSting = it.iconProduct!!,
                    favoritelIconProduct = it.isFavorite,
                    productDiscount = it.productDiscount,
                    isBestseller = it.isBestseller!!,
                    priceWithDiscount = it.priceWithDiscount,
                    priceOlD = it.priceOlD,
                    goToBasket = it.goToBasket!!,
                    nameOfProduct = it.nameOfProduct,
                    shortDescription = it.shortDescription,
                    longDescription = it.longDescription,
                    images = it.images,
                    company = it.company,
                    color = it.color,
                    skuId = it.id
                )
            })
        })
    }

}

