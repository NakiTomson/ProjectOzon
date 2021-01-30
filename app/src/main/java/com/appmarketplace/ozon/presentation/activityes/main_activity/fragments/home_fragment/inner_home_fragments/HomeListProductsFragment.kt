package com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.home_fragment.inner_home_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.Interfaces.RowType
import com.appmarketplace.ozon.presentation.adapters.*
import com.appmarketplace.ozon.presentation.data.*
import kotlinx.android.synthetic.main.general_inner_fragment.*


class HomeListProductsFragment : Fragment() {

    companion object {
        fun newInstance() = HomeListProductsFragment()
    }


    private lateinit var adsAdapterViewPager: BoardingItemAdapter
    private lateinit var combinationProductAdapterViewPager: CombinationProductsAdapter
    private lateinit var liveItemAdapterViewPager: LiveItemAdapter


    private lateinit var containerProductsAdapter: ContainerProductsAdapter
    private lateinit var containerProductsAdapterTwo: ContainerProductsAdapter


    private lateinit var viewModel: HomeListProductsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.general_inner_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeListProductsViewModel::class.java)
        setupAdapters()
    }

    fun setupAdapters(){

        adsAdapterViewPager = BoardingItemAdapter(viewModel.getListDataAds())
        combinationProductAdapterViewPager = CombinationProductsAdapter(viewModel.allDataForCategoryList())
        liveItemAdapterViewPager = LiveItemAdapter(viewModel.getListItemStreamNow())

        containerProductsAdapter = ContainerProductsAdapter(viewModel.getListProductsByBestOffer())
        containerProductsAdapterTwo = ContainerProductsAdapter(viewModel.getListProductsByBestOfferTwo())

        val items: List<RowType> = listOf(
            BannerRowType(adsAdapterViewPager),
            CategoryRowType(combinationProductAdapterViewPager),
            HistoryRowType(),
            LiveRowType(liveItemAdapterViewPager),
            ProductsRowType(containerProductsAdapterTwo),
            RegistrationRowType(),
            ProductsRowType(containerProductsAdapter)
        )


        mutipleHomeRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapterMultiple  = MultipleTypesAdapter(items)
        mutipleHomeRecyclerView.adapter = adapterMultiple

    }


}