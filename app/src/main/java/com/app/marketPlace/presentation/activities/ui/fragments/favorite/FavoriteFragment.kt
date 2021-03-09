package com.app.marketPlace.presentation.activities.ui.fragments.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.marketPlace.R
import com.app.marketPlace.domain.modelsUI.OnProductItem
import com.app.marketPlace.domain.repositories.DataBaseRepository
import com.app.marketPlace.presentation.MarketPlaceApp
import com.app.marketPlace.presentation.activities.MainViewModel
import com.app.marketPlace.presentation.activities.MainViewModelFactory
import com.app.marketPlace.presentation.adapters.ProductItemAdapter
import com.app.marketPlace.presentation.rowType.ProductsRowType
import kotlinx.android.synthetic.main.fragment_favorite.*
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    init {
        MarketPlaceApp.appComponent.inject(favoriteFragment = this)
    }

    @Inject
    lateinit var repository: DataBaseRepository

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        favoriteList.layoutManager = GridLayoutManager(activity,2)
        val productsAdapter = ProductItemAdapter()
        productsAdapter.setHasStableIds(true)
        favoriteList.adapter = productsAdapter

        productsAdapter.setClickHeartProduct = object :ProductsRowType.OnClickListener{
            override fun onClick(productsItem: OnProductItem) {
                mainViewModel.deleteProduct(productsItem)
                productsAdapter.deleteProduct(productsItem)
            }
        }

        productsAdapter.setClickListenerProduct = object : ProductsRowType.OnProductClickListener {

            override fun clickProduct(product: OnProductItem, imageView: ImageView) {

                val extras = FragmentNavigatorExtras(
                    imageView to product.generalIconProductSting!!
                )
                val action = FavoriteFragmentDirections.actionGlobalDetailsProductFragment(
                    product = product
                )
                findNavController().navigate(action,extras)
            }
        }


        mainViewModel.favorite.observe(viewLifecycleOwner, Observer { list ->

            if (list.isNullOrEmpty()) frameFavorite.visibility = View.VISIBLE else frameFavorite.visibility = View.GONE
            if (list == null) return@Observer
            productsAdapter.setData(
                list.map {
                    OnProductItem(
                        type = OnProductItem.Type.ProductWithName,
                        generalIconProductSting = it.iconProduct,
                        favoriteIconProduct = it.isFavorite,
                        productDiscount = it.productDiscount,
                        isBestseller = it.isBestseller,
                        priceWithDiscount = it.priceWithDiscount,
                        priceOlD = it.priceOlD,
                        goToBasket = it.goToBasket,
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

