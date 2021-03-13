package com.app.marketPlace.presentation.activities.ui.fragments.productsList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.marketPlace.R
import com.app.marketPlace.domain.models.ProductItem
import com.app.marketPlace.domain.repositories.DataBaseRepository
import com.app.marketPlace.domain.repositories.AppRepository
import com.app.marketPlace.presentation.MarketPlaceApp
import com.app.marketPlace.presentation.activities.*
import com.app.marketPlace.presentation.activities.ui.fragments.home.HomeFragmentDirections
import com.app.marketPlace.presentation.adapters.ProductItemAdapter
import com.app.marketPlace.presentation.rowType.ProductsRowType
import kotlinx.android.synthetic.main.fragment_products_list.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import javax.inject.Inject


class ProductsListFragment : Fragment(R.layout.fragment_products_list) {

    init {
        MarketPlaceApp.appComponent.inject(productsListFragment = this)
    }

    @Inject
    lateinit var repository: DataBaseRepository

    @Inject
    lateinit var homeRepository: AppRepository


    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(repository)
    }

    private val viewModel: ProductsListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        foundProductsRecyclerView.layoutManager = GridLayoutManager(activity,3)
        val productsAdapter = ProductItemAdapter()
        productsAdapter.setHasStableIds(true)
        foundProductsRecyclerView.adapter =  productsAdapter

        productsAdapter.setClickHeartProduct = object :ProductsRowType.ClickListener{
            override fun onClick(productsItem: ProductItem) {
                mainViewModel.insertOrDeleteFavoriteProduct(productsItem)
            }
        }

        productsAdapter.setClickBasketProduct = ProductsRowType.ClickListener { productsItem ->
            mainViewModel.insertOrDeleteBasket(productsItem)
        }

        productsAdapter.setClickListenerProduct = ProductsRowType.ProductClickListener { product, imageView ->
            val extras = FragmentNavigatorExtras(imageView to product.generalIconProductSting!!)
            val action = HomeFragmentDirections.actionGlobalDetailsProductFragment(product = product)
            navController.navigate(action, extras)
        }

        val searchWord = requireArguments().getString("arg1")
        val category = requireArguments().getString("category")

        searchWord?.let {
            viewModel.loadProductsByWord(it)
            searchTextInput.setText(it.replace("&search="," "))
        }

        category?.let {
            viewModel.loadProductsByCategory(it)
        }

        viewModel.searchProductsResultList.observe(viewLifecycleOwner, { resource ->
            productsAdapter.setData(resource.data!!.list)
            resource.data.requestName?.let { searchTextInput.setText(it) }
            stopProgressBar()
        })

        viewModel.productsResultList.observe(viewLifecycleOwner, { resource->
            productsAdapter.setData(resource.data!!.list)
            stopProgressBar()
        })

        searchTextInput?.setOnTouchListener { view, event ->
            if (navController.currentDestination?.id != R.id.searchHintProductHomeFragment){
                val keywords = searchTextInput.text.toString().trim().replace(" ", "&search=")
                val bundle = Bundle()
                bundle.putString("arg1", keywords)
                navController.navigate(R.id.searchHintProductHomeFragment,bundle)
            }
            view.performClick()
            view.onTouchEvent(event)
        }
    }

    private fun stopProgressBar(){
        progressBar.visibility = View.GONE
    }

}