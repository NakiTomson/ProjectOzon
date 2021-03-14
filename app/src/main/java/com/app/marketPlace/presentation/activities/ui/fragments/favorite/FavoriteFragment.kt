package com.app.marketPlace.presentation.activities.ui.fragments.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.marketPlace.R
import com.app.marketPlace.domain.mappers.Mapper
import com.app.marketPlace.domain.models.ProductItem
import com.app.marketPlace.domain.repositories.DataBaseRepository
import com.app.marketPlace.presentation.MarketPlaceApp
import com.app.marketPlace.presentation.activities.MainViewModel
import com.app.marketPlace.presentation.activities.MainViewModelFactory
import com.app.marketPlace.presentation.adapters.ProductItemAdapter
import com.app.marketPlace.presentation.rowType.ProductsRowType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_favorite.*
import javax.inject.Inject

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    init {
        MarketPlaceApp.appComponent.inject(favoriteFragment = this)
    }

    @Inject
    lateinit var repository: DataBaseRepository

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteList.layoutManager = GridLayoutManager(activity,2)
        val productsAdapter = ProductItemAdapter()
        productsAdapter.setHasStableIds(true)
        favoriteList.adapter = productsAdapter

        productsAdapter.setClickHeartProduct = ProductsRowType.ClickListener { productsItem ->
            mainViewModel.deleteProduct(productsItem)
            productsAdapter.deleteProduct(productsItem)
        }

        productsAdapter.setClickBasketProduct = ProductsRowType.ClickListener { productsItem ->
            mainViewModel.insertOrDeleteBasket(productsItem)
        }

        productsAdapter.setClickListenerProduct = ProductsRowType.ProductClickListener { product, view ->
            val extras = FragmentNavigatorExtras(view to product.generalIconProductSting!!)
            val action = FavoriteFragmentDirections.actionGlobalDetailsProductFragment(product = product)
            findNavController().navigate(action, extras)
        }

        startShopping.setOnClickListener {
            requireActivity().bottomNavigationView.selectedItemId = R.id.home
        }

        mainViewModel.favorite.observe(viewLifecycleOwner, Observer { list ->
            if (list.isNullOrEmpty()) frameFavorite.visibility = View.VISIBLE else frameFavorite.visibility = View.GONE
            if (list == null) return@Observer
            productsAdapter.setData(Mapper.MapperToUi.mapListFavoriteDB(list)!!)
        })
    }
}

