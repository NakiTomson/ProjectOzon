package com.appmarketplace.ozon.presentation.activityes.ui.fragments.productsList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.home.HomeFragmentDirections
import com.appmarketplace.ozon.presentation.adapters.ProductItemAdapter
import com.appmarketplace.ozon.presentation.rowType.ProductsRowType
import com.appmarketplace.ozon.presentation.rowType.Resource
import kotlinx.android.synthetic.main.fragment_products_list.*
import kotlinx.android.synthetic.main.toolbar_custom.*


class ProductsListFragment : Fragment() {


    private lateinit var viewModel: ProductsListViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_products_list, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductsListViewModel::class.java)

        val navController = findNavController()
        foundProductsRecyclerView.layoutManager = GridLayoutManager(activity,3)
        val productsAdapter = ProductItemAdapter()
        productsAdapter.setHasStableIds(true)
        foundProductsRecyclerView.adapter =  productsAdapter


        productsAdapter.setClickListenerProduct = object :ProductsRowType.OnClickProduct{

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
        loadingFrame.visibility = View.GONE
    }


    fun <T> gettingErrors(resource: Resource<T>): Boolean {
        return !(resource.status == Resource.Status.ERROR || resource.status == Resource.Status.LOADING || resource.data == null || resource.exception != null)
    }

    fun <T> errorhandling(name: String, resource: Resource<T>) {
        Log.v(name, "${resource.exception?.message}")
        Log.v(name, "${resource.exception}")
        Log.v(name, "${resource.status}")
    }

}