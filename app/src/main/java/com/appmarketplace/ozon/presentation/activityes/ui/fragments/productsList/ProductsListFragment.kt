package com.appmarketplace.ozon.presentation.activityes.ui.fragments.productsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem
import com.appmarketplace.ozon.domain.repositories.DataBaseRepository
import com.appmarketplace.ozon.domain.repositories.ListProductRepository
import com.appmarketplace.ozon.presentation.OzonApp
import com.appmarketplace.ozon.presentation.activityes.*
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.home.HomeFragmentDirections
import com.appmarketplace.ozon.presentation.adapters.ProductItemAdapter
import com.appmarketplace.ozon.presentation.rowType.ProductsRowType
import kotlinx.android.synthetic.main.fragment_products_list.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import javax.inject.Inject
import javax.inject.Named


class ProductsListFragment : Fragment() {



    init {
        OzonApp.appComponent.inject(productsListFragment = this)
    }


    @Inject
    lateinit var repository: DataBaseRepository

    @Inject
    @field : Named("bestbuy")
    lateinit var listProductRepositoryImpl: ListProductRepository


    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository)
    }

    private val viewModel: ProductsListViewModel by viewModels {
        ProductsListViewModelFactory(listProductRepositoryImpl)
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
                val action = HomeFragmentDirections.actionGlobalDetailsProductFragement(
                    product = product
                )
                navController.navigate(action)
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

        viewModel.searchProductsResultList.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    productsAdapter.setData(lists.list)
                    lists.requestName?.let { searchTextInput.setText(it) }
                    stopLoadingsOneFrame()
                }
            } else {
                errorhandling("ERROR SEARCH PRODUCTS", resource)
            }
        })


        viewModel.productsResultList.observe(viewLifecycleOwner, Observer {resource->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    productsAdapter.setData(lists.list)
                    stopLoadingsOneFrame()
                }
            } else {
                errorhandling("ERROR RESULT PRODUCTS", resource)
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

    fun stopLoadingsOneFrame(){
        progressBar.visibility = View.GONE
    }



}