package com.appmarketplace.ozon.presentation.activityes.ui.fragments.products_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.adapters.MultipleTypesAdapter
import com.appmarketplace.ozon.presentation.adapters.ProductItemAdapter
import com.appmarketplace.ozon.presentation.data.ProductsRowType
import com.appmarketplace.ozon.presentation.data.Resource
import kotlinx.android.synthetic.main.fragment_products_list.*
import kotlinx.android.synthetic.main.toolbar_custom.*


class ProductsListFragment : Fragment() {



    private lateinit var viewModel: ProductsListViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductsListViewModel::class.java)

        foundProductsRecyclerView.layoutManager = GridLayoutManager(activity,3)
        val productsAdapter = ProductItemAdapter()
        productsAdapter.setHasStableIds(true)
        foundProductsRecyclerView.adapter =  productsAdapter

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
                errorhandling("ERROR BOARDING START", resource)
            }
        })


        viewModel.productsResultList.observe(viewLifecycleOwner, Observer {resource->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    productsAdapter.setData(lists.list)
                    stopLoadingsOneFrame()
                }
            } else {
                errorhandling("ERROR BOARDING START", resource)
            }
        })


        val navHostFragment = findNavController()

        searchTextInput?.setOnTouchListener { v, event ->
            if (navHostFragment.currentDestination?.id != R.id.searchHintProductHomeFragment){
                val keywords = searchTextInput.text.toString().trim().replace(" ", "&search=")
                val bundle = Bundle()
                bundle.putString("arg1", keywords)
                navHostFragment.navigate(R.id.searchHintProductHomeFragment,bundle)
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