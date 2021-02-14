package com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.home_fragment.search_list_home_product

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appmarketplace.ozon.R

class SearchListProductHomeFragment : Fragment() {

    companion object {
        fun newInstance() = SearchListProductHomeFragment()
    }

    private lateinit var viewModel: SearchListProductHomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_list_product_home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchListProductHomeViewModel::class.java)

    }

}