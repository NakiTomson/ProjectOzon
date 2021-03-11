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
import com.app.marketPlace.domain.mappers.Mapper
import com.app.marketPlace.domain.modelsUI.OnProductItem
import com.app.marketPlace.domain.repositories.DataBaseRepository
import com.app.marketPlace.presentation.MarketPlaceApp
import com.app.marketPlace.presentation.activities.MainViewModel
import com.app.marketPlace.presentation.activities.MainViewModelFactory
import com.app.marketPlace.presentation.adapters.ProductItemAdapter
import com.app.marketPlace.presentation.rowType.ProductsRowType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_favorite.*
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    init {
        MarketPlaceApp.appComponent.inject(favoriteFragment = this)
    }

    @Inject
    lateinit var repository: DataBaseRepository

    @Inject
    lateinit var mapper: Mapper

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

        productsAdapter.setClickBasketProduct = object :ProductsRowType.OnClickListener{
            override fun onClick(productsItem: OnProductItem) {
                mainViewModel.insertOrDeleteBasket(productsItem)
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


        startShopping.setOnClickListener {
            requireActivity().bottomNavigationView.selectedItemId = R.id.home
        }

        mainViewModel.favorite.observe(viewLifecycleOwner, Observer { list ->
            if (list.isNullOrEmpty()) frameFavorite.visibility = View.VISIBLE else frameFavorite.visibility = View.GONE
            if (list == null) return@Observer
            productsAdapter.setData(mapper.mapDbBasketToUi(list)!!)
        })

    }

}

