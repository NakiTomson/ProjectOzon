package com.app.marketPlace.presentation.activities.ui.fragments.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.marketPlace.R
import com.app.marketPlace.presentation.activities.MainViewModel
import com.app.marketPlace.presentation.adapters.ProductAdapter
import com.app.marketPlace.presentation.interfaces.ProductRowType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_basket.*
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_favorite.startShopping

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val mainViewModel: MainViewModel by activityViewModels ()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteList.layoutManager = GridLayoutManager(activity,2)
        val productsAdapter = ProductAdapter()
        productsAdapter.setHasStableIds(true)
        favoriteList.setHasFixedSize(true)
        favoriteList.apply {
            adapter = productsAdapter
            postponeEnterTransition()
            viewTreeObserver
                .addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
        }

        productsAdapter.setClickHeartProduct = ProductRowType.ClickListener { productsItem ->
            mainViewModel.deleteProduct(productsItem)
            productsAdapter.deleteProduct(productsItem)
        }

        productsAdapter.setClickBasketProduct = ProductRowType.ClickListener { productsItem ->
            mainViewModel.insertOrDeleteBasket(productsItem)
        }

        productsAdapter.setClickListenerProduct = ProductRowType.ProductClickListener { product, view ->
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
            productsAdapter.setData(mainViewModel.mapperFromDb?.mapListFavoriteDB(list)!!)
        })
    }
}

