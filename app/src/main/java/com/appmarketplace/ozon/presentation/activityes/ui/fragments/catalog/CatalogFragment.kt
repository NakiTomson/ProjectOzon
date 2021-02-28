package com.appmarketplace.ozon.presentation.activityes.ui.fragments.catalog


import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.adapters.CombinationProductsAdapter
import com.appmarketplace.ozon.presentation.adapters.MultipleTypesAdapter
import com.appmarketplace.ozon.presentation.rowType.CategoryRowType
import com.appmarketplace.ozon.presentation.rowType.Resource
import kotlinx.android.synthetic.main.fragment_catalog.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*

class CatalogFragment : Fragment() {


    lateinit var  catalogViewModel:CatalogViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_catalog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        catalogViewModel = ViewModelProvider(this).get(CatalogViewModel::class.java)

        catalogViewModel.getCatalogProducts()

        val navController = findNavController()

        val combinationProductAdapterViewPager = CombinationProductsAdapter()
        val adapterMultiple = MultipleTypesAdapter()
        adapterMultiple.setHasStableIds(true)
        mutipleHomeRecyclerViewCatalog.layoutManager =  LinearLayoutManager(context)
        mutipleHomeRecyclerViewCatalog.adapter = adapterMultiple
        mutipleHomeRecyclerViewCatalog.setHasFixedSize(false)

        catalogViewModel.categoryProductliveData.observe(viewLifecycleOwner, { resource ->

            if (gettingErrors(resource)) {
                resource.data?.let { lists ->

                    Log.v("FRTGYHUI","e ${lists[0][0].category}")
                    combinationProductAdapterViewPager.setData(lists)

                    val categoryRowType = CategoryRowType(combinationProductAdapterViewPager)
                    adapterMultiple.setData(categoryRowType)

                    categoryRowType.clickOnCategoryItem =
                        object : CategoryRowType.ClickCategoryListener {
                            override fun onClickItem(data: String) {
                                val bundle = Bundle()
                                bundle.putString("category", data)
                                navController.navigate(R.id.productsListFragment2, bundle)
                            }
                        }
                }
            } else {
                errorhandling("ERROR CATEGORY", resource)
            }
        })


    }

    fun <T> gettingErrors(resource: Resource<T>): Boolean {
        return !(resource.status == Resource.Status.ERROR || resource.status == Resource.Status.LOADING || resource.data == null || resource.exception != null)
    }

    fun <T> errorhandling(name: String, resource: Resource<T>) {
        Log.v(name, "${resource.exception?.message}")
        Log.v(name, "${resource.exception}")
        Log.v(name, "${resource.exception?.stackTrace}")
        Log.v(name, "${resource.status}")
    }
}