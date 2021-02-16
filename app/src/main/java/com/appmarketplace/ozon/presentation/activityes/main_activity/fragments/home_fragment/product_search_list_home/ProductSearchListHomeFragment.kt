package com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.home_fragment.product_search_list_home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.home_fragment.hint_search_home_product.SearchHintProductHomeViewModel
import com.appmarketplace.ozon.presentation.adapters.ContainerProductsAdapter
import com.appmarketplace.ozon.presentation.data.BannerRowType
import com.appmarketplace.ozon.presentation.data.Resource
import kotlinx.android.synthetic.main.product_search_list_home_fragment.*

class ProductSearchListHomeFragment : Fragment() {



    private lateinit var viewModel: SearchHintProductHomeViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.product_search_list_home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SearchHintProductHomeViewModel::class.java)


        foundProductsRecyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter =ContainerProductsAdapter(3)
        foundProductsRecyclerView.adapter =  adapter



        viewModel.searchProductsResultList.observe(viewLifecycleOwner, Observer { resource ->
            Log.v("LISENTER", "LISENTER")
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    adapter.setData(lists)
                }
            } else {
                errorhandling("ERROR BOARDING START", resource)
            }
        })


//        viewModel.searchProductsResultList.observe(viewLifecycleOwner, Observer {resource->
//            Log.v("LISENTER","LISENTER")
//            if ( gettingErrors(resource)){
//                resource.data?.let {lists->
//                    adapter.setData(lists)
//                }
//            }else{
//                errorhandling("ERROR BOARDING START",resource)
//            }
//        })

    }

    fun<T> gettingErrors(resource: Resource<T>): Boolean {
        return !(resource.status == Resource.Status.ERROR || resource.status == Resource.Status.LOADING || resource.data == null || resource.exception != null)
    }

    fun<T> errorhandling(name: String, resource: Resource<T>) {
        Log.v(name,"${resource.exception?.message}")
        Log.v(name,"${resource.exception}")
        Log.v(name,"${resource.status}")
    }

}