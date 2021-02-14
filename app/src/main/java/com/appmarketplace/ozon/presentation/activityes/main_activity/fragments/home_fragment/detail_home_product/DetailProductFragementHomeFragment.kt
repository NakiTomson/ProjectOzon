package com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.home_fragment.detail_home_product

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appmarketplace.ozon.R

class DetailProductFragementHomeFragment : Fragment() {

    companion object {
        fun newInstance() = DetailProductFragementHomeFragment()
    }

    private lateinit var viewModel: DetailProductHomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.detail_product_fragement_home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailProductHomeViewModel::class.java)

    }

}