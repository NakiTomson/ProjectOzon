package com.app.marketPlace.presentation.activities.ui.fragments.catalog


import android.os.Bundle

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.marketPlace.R
import com.app.marketPlace.data.remote.models.Banner
import com.app.marketPlace.presentation.activities.errorHandling
import com.app.marketPlace.presentation.activities.gettingErrors
import com.app.marketPlace.presentation.adapters.CategoryAdapter
import com.app.marketPlace.presentation.rowType.CategoryRowType
import kotlinx.android.synthetic.main.fragment_catalog.*

class CatalogFragment : Fragment(R.layout.fragment_catalog) {


    private val viewModel: CatalogViewModel by viewModels()

    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCatalogProducts()

        navController = findNavController()

        val categoryAdapter = CategoryAdapter()
        setupCategoriesAdapter(categoryAdapter)
        setupCategories(categoryAdapter)
    }

    private fun setupCategoriesAdapter(categoryAdapter: CategoryAdapter) {
        categoryProductsAdapter.layoutManager =  GridLayoutManager(context,4)
        categoryProductsAdapter.adapter = categoryAdapter
        categoryProductsAdapter.setHasFixedSize(false)

        categoryAdapter.setOnCategoryClickListener = CategoryRowType.ClickCategoryListener { data ->
            val bundle = Bundle()
            bundle.putString("category", data)
            navController.navigate(R.id.productsListFragment, bundle)
        }
    }

    private fun setupCategories(categoryAdapter: CategoryAdapter) {
        viewModel.categoryProductLiveData.observe(viewLifecycleOwner, { resource ->
            categoryAdapter.setData(resource.data?.flatten() as MutableList<Banner>)
        })
    }
}