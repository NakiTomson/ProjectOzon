package com.app.marketPlace.presentation.activities.ui.fragments.catalog


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.marketPlace.R
import com.app.marketPlace.domain.modelsUI.OnBoardingItem
import com.app.marketPlace.presentation.activities.errorHandling
import com.app.marketPlace.presentation.activities.gettingErrors
import com.app.marketPlace.presentation.adapters.CategoryAdapter
import com.app.marketPlace.presentation.adapters.CombinationProductsAdapter
import com.app.marketPlace.presentation.adapters.MultipleTypesAdapter
import com.app.marketPlace.presentation.rowType.CategoryRowType
import kotlinx.android.synthetic.main.fragment_catalog.*

class CatalogFragment : Fragment() {


    private lateinit var  catalogViewModel:CatalogViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_catalog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        catalogViewModel = ViewModelProvider(this).get(CatalogViewModel::class.java)

        catalogViewModel.getCatalogProducts()
        val navController = findNavController()

        val categoryAdapter = CategoryAdapter()

        categoryProductsAdapter.layoutManager =  GridLayoutManager(context,5)
        categoryProductsAdapter.adapter = categoryAdapter
        categoryProductsAdapter.setHasFixedSize(false)

        catalogViewModel.categoryProductLiveData.observe(viewLifecycleOwner, { resource ->

            if (gettingErrors(resource)) {
                resource.data?.let { lists ->

                    categoryAdapter.setData(lists.flatten() as MutableList<OnBoardingItem>)

                    categoryAdapter.clickOnCategoryItem =
                        object : CategoryRowType.ClickCategoryListener {
                            override fun onClickItem(data: String) {
                                val bundle = Bundle()
                                bundle.putString("category", data)
                                navController.navigate(R.id.productsListFragment, bundle)
                            }
                        }
                }
            } else {
                errorHandling("ERROR CATEGORY", resource)
            }
        })
    }
}