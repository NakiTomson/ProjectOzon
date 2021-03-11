package com.app.marketPlace.presentation.activities.ui.fragments.productsList

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.ChangeTransform
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.marketPlace.R
import com.app.marketPlace.domain.modelsUI.OnProductItem
import com.app.marketPlace.domain.repositories.DataBaseRepository
import com.app.marketPlace.domain.repositories.HomeRepository
import com.app.marketPlace.presentation.MarketPlaceApp
import com.app.marketPlace.presentation.activities.*
import com.app.marketPlace.presentation.activities.ui.fragments.home.HomeFragmentDirections
import com.app.marketPlace.presentation.adapters.ProductItemAdapter
import com.app.marketPlace.presentation.rowType.ProductsRowType
import kotlinx.android.synthetic.main.fragment_details_product.*
import kotlinx.android.synthetic.main.fragment_products_list.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import javax.inject.Inject
import javax.inject.Named


class ProductsListFragment : Fragment() {



    init {
        MarketPlaceApp.appComponent.inject(productsListFragment = this)
    }


    @Inject
    lateinit var repository: DataBaseRepository

    @Inject
    @field : Named("bestbuy")
    lateinit var listProductRepositoryImpl: HomeRepository


    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository)
    }

    private val viewModel: ProductsListViewModel by viewModels {
        ProductsListViewModelFactory(listProductRepositoryImpl)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_products_list, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val navController = findNavController()

        foundProductsRecyclerView.layoutManager = GridLayoutManager(activity,3)
        val productsAdapter = ProductItemAdapter()
        productsAdapter.setHasStableIds(true)
        foundProductsRecyclerView.adapter =  productsAdapter


        productsAdapter.setClickHeartProduct = object :ProductsRowType.OnClickListener{
            override fun onClick(productsItem: OnProductItem) {
                mainViewModel.insertOrDeleteFavoriteProduct(productsItem)
            }
        }

        productsAdapter.setClickBasketProduct = object :ProductsRowType.OnClickListener{
            override fun onClick(productsItem: OnProductItem) {
                mainViewModel.insertOrDeleteBasket(productsItem)
            }
        }

        productsAdapter.setClickListenerProduct = object :ProductsRowType.OnProductClickListener{

            override fun clickProduct(product: OnProductItem, imageView: ImageView) {

                val extras = FragmentNavigatorExtras(
                    imageView to product.generalIconProductSting!!
                )
                val action = HomeFragmentDirections.actionGlobalDetailsProductFragment(
                    product = product
                )
                navController.navigate(action,extras)
            }
        }

        val searchWord = requireArguments().getString("arg1")
        val category = requireArguments().getString("category")

        searchWord?.let {
            viewModel.loadProductsByWord(it)
            searchTextInput.setText(it.replace("&search="," "))
        }

        category?.let {
            viewModel.getProductsByCategory(it)
        }

        viewModel.searchProductsResultList.observe(viewLifecycleOwner, { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    productsAdapter.setData(lists.list)
                    lists.requestName?.let { searchTextInput.setText(it) }
                    stopLoadingsOneFrame()
                }
            } else {
                errorHandling("ERROR SEARCH PRODUCTS", resource)
            }
        })


        viewModel.productsResultList.observe(viewLifecycleOwner, { resource->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    productsAdapter.setData(lists.list)
                    stopLoadingsOneFrame()
                }
            } else {
                errorHandling("ERROR RESULT PRODUCTS", resource)
            }
        })


        searchTextInput?.setOnTouchListener { v, event ->
            if (navController.currentDestination?.id != R.id.searchHintProductHomeFragment){
                val keywords = searchTextInput.text.toString().trim().replace(" ", "&search=")
                val bundle = Bundle()
                bundle.putString("arg1", keywords)
                navController.navigate(R.id.searchHintProductHomeFragment,bundle)
            }
            v.performClick()
            v.onTouchEvent(event)
        }

    }

    private fun stopLoadingsOneFrame(){
        progressBar.visibility = View.GONE
    }



}