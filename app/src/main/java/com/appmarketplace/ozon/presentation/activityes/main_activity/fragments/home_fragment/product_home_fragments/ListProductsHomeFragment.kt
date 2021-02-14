package com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.home_fragment.product_home_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.Interfaces.RowType
import com.appmarketplace.ozon.presentation.adapters.*
import com.appmarketplace.ozon.presentation.data.*

import kotlinx.android.synthetic.main.general_inner_fragment.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ListProductsHomeFragment : Fragment() {


    private var startBannerAdapterViewPager: BoardingItemAdapter = BoardingItemAdapter()
    private var centerBannerAdapterViewPager: BoardingItemAdapter =  BoardingItemAdapter()
    private var downBannerAdapterViewPager: BoardingItemAdapter = BoardingItemAdapter()

    private var combinationProductAdapterViewPager: CombinationProductsAdapter = CombinationProductsAdapter()
    private var liveItemAdapterViewPager: LiveItemAdapter = LiveItemAdapter()

    private var  containerProductsAdapter: ContainerProductsAdapter = ContainerProductsAdapter(3)
    private var  containerProductsAdapterTwo: ContainerProductsAdapter = ContainerProductsAdapter(3)
    private var  containerProductsAdapterThree: ContainerProductsAdapter = ContainerProductsAdapter(3)
    private var  containerProductsAdapterFoure: ContainerProductsAdapter = ContainerProductsAdapter(2)

    private lateinit var viewModel: ListProductsHomeViewModel

    private var adapterMultiple:MultipleTypesAdapter = MultipleTypesAdapter()

    private var mainItems: MutableList<RowType> = ArrayList()

    var isPopOutLogic = false

    override fun onCreate(savedInstanceState: Bundle?) {
        isPopOutLogic = true
        viewModel = ViewModelProvider(this).get(ListProductsHomeViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.general_inner_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (isPopOutLogic){
            mainItems = ArrayList()
            viewModel.startLoadingData()
            loadData()
        }else{
            mainItems = viewModel.mainItemsSave
            adapterMultiple.setData(mainItems)
            setupAdapter()
        }
    }

    fun loadData(){
        adapterMultiple.setHasStableIds(true)
        setupAdapter()
        startBannerGettingData()
        categoryGettingData()
        historyGettingData()
        liveGettingData()
        productOneGettingData()
        productTwoGettingData()
        centerBannerGettingData()
        productThreeGettingData()
        downBannerGettingData()
        productFoureGettingData()
    }

    fun setupAdapter(){
        mutipleHomeRecyclerView.layoutManager = LinearLayoutManager(context)
        mutipleHomeRecyclerView.adapter = adapterMultiple
    }
    fun<T> gettingErrors(resource: Resource<T>): Boolean {
        return !(resource.status == Resource.Status.ERROR || resource.status == Resource.Status.LOADING || resource.data == null || resource.exception != null)
    }

    fun<T> errorhandling(name: String, resource: Resource<T>) {
        Log.v(name,"${resource.exception?.message}")
        Log.v(name,"${resource.exception}")
        Log.v(name,"${resource.status}")
    }

    fun startBannerGettingData(){
        viewModel.bannerListStart.observe(viewLifecycleOwner, Observer {resource->
            if ( gettingErrors(resource)){
                resource.data?.let {lists->
                    startBannerAdapterViewPager.setData(lists)
                    mainItems.add(BannerRowType(startBannerAdapterViewPager))
                    adapterMultiple.setData(mainItems)
                }
            }else{
                errorhandling("ERROR BOARDING START",resource)
            }
        })
    }

    fun categoryGettingData(){
        viewModel.categoryProductliveData.observe(viewLifecycleOwner, Observer {resource ->
            if ( gettingErrors(resource)){
                resource.data?.let {lists->
                    combinationProductAdapterViewPager.setData(lists)
                    mainItems.add(CategoryRowType(combinationProductAdapterViewPager))
                    adapterMultiple.setData(mainItems)
                }
            }else{
                errorhandling("ERROR CATEGORY",resource)
            }
        })
    }

    fun historyGettingData(){
        viewModel.historyItemsLiveData.observe(viewLifecycleOwner, Observer {resource->
            if ( gettingErrors(resource)){
                resource.data?.let {lists->
                    mainItems.add(HistoryRowType(lists))
                    adapterMultiple.setData(mainItems)
                }
            }else{
                errorhandling("ERROR HISTORY",resource)
            }
        })
    }

    fun liveGettingData(){
        viewModel.liveItemsLiveData.observe(viewLifecycleOwner, Observer {resource->
            if ( gettingErrors(resource)){
                resource.data?.let {lists->
                    liveItemAdapterViewPager.setData(lists)
                    mainItems.add(LiveRowType(liveItemAdapterViewPager))
                    adapterMultiple.setData(mainItems)
                }
            }else{
                errorhandling("ERROR LIVE",resource)
            }
        })
    }

    fun productOneGettingData(){
        viewModel.listPoductsLiveData.observe(viewLifecycleOwner, Observer {resource->
            if ( gettingErrors(resource)){
                resource.data?.let {lists->
                    containerProductsAdapter.setData(lists)
                    mainItems.add(ProductsRowType(containerProductsAdapter))
                    mainItems.add(RegistrationRowType())
                    adapterMultiple.setData(mainItems)
                }
            }else{
                errorhandling("ERROR PRODUCT 1",resource)
            }
        })
    }

    fun productTwoGettingData(){
        viewModel.listPoductsLiveData2.observe(viewLifecycleOwner, Observer {resource ->
            if ( gettingErrors(resource)){
                resource.data?.let {lists->
                    containerProductsAdapterTwo.setData(lists)
                    mainItems.add(ProductsRowType(containerProductsAdapterTwo))
                    adapterMultiple.setData(mainItems)
                }
            }else{
                errorhandling("ERROR PRODUCT 2",resource)
            }
        })
    }

    fun centerBannerGettingData(){
        viewModel.bannerListCenter.observe(viewLifecycleOwner, Observer {resource->
            if ( gettingErrors(resource)){
                resource.data?.let {lists->
                    centerBannerAdapterViewPager.setData(lists)
                    mainItems.add(BannerRowType(centerBannerAdapterViewPager))
                    adapterMultiple.setData(mainItems)
                }
            }else{
                errorhandling("ERROR Banner Center",resource)
            }
        })
    }

    fun productThreeGettingData(){
        viewModel.listPoductsLiveData3.observe(viewLifecycleOwner, Observer {resource->
            if ( gettingErrors(resource)){
                resource.data?.let {lists->
                    containerProductsAdapterThree.setData(lists)
                    mainItems.add(ProductsRowType(containerProductsAdapterThree))
                    adapterMultiple.setData(mainItems)
                }
            }else{
                errorhandling("ERROR Produt 3",resource)
            }
        })
    }

    fun downBannerGettingData(){
        viewModel.bannerListDown.observe(viewLifecycleOwner, Observer {resource->
            if ( gettingErrors(resource)){
                resource.data?.let {lists->
                    downBannerAdapterViewPager.setData(lists)
                    mainItems.add(BannerRowType(downBannerAdapterViewPager))
                    adapterMultiple.setData(mainItems)
                }
            }else{
                errorhandling("ERROR  Banner Down",resource)
            }
        })
    }

    fun productFoureGettingData(){
        viewModel.listPoductsLiveData4.observe(viewLifecycleOwner, Observer {resource->
            if ( gettingErrors(resource)){
                resource.data?.let {lists->
                    Log.v("TAGTIME","re10 ${ SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().time)}")
                    containerProductsAdapterFoure.setData(lists)
                    mainItems.add(ProductsRowType(containerProductsAdapterFoure))
                    adapterMultiple.setData(mainItems)
                }
            }else{
                errorhandling("ERROR  Product 4",resource)
            }
        })
    }

    override fun onDestroyView() {
        isPopOutLogic = false
        viewModel.mainItemsSave = mainItems
        super.onDestroyView()
    }

}
