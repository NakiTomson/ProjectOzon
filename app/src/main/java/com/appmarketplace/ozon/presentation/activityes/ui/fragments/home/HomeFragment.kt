package com.appmarketplace.ozon.presentation.activityes.ui.fragments.home



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
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {
//    lateinit var  navController:NavController


//    var hide:Boolean = false
//
//    var callback: OnBackPressedCallback? = null
//
//    var statePopOut:Boolean = false


    private lateinit var viewModel:HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel =  ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.startLoading()

        val startBannerAdapterViewPager = BannerAdapter()
        val centerBannerAdapterViewPager = BannerAdapter()
        val downBannerAdapterViewPager = BannerAdapter()

        val combinationProductAdapterViewPager = CombinationProductsAdapter()
        val liveItemAdapterViewPager = LiveItemAdapter()

        val containerProductsAdapter = ContainerProductsAdapter(3)
        val containerProductsAdapterTwo = ContainerProductsAdapter(3)
        val containerProductsAdapterThree = ContainerProductsAdapter(3)
        val containerProductsAdapterFoure = ContainerProductsAdapter(2)

        val adapterMultiple = MultipleTypesAdapter()

        val mainItems: MutableSet<RowType> = mutableSetOf()


        setupAdapter(adapterMultiple, mainItems)

        startBannerGettingData(adapterMultiple, mainItems, startBannerAdapterViewPager)
        categoryGettingData(adapterMultiple, mainItems, combinationProductAdapterViewPager)
        historyGettingData(adapterMultiple, mainItems)
        liveGettingData(adapterMultiple, mainItems, liveItemAdapterViewPager)
        productOneGettingData(adapterMultiple, mainItems, containerProductsAdapter)
        productTwoGettingData(adapterMultiple, mainItems, containerProductsAdapterTwo)
        centerBannerGettingData(adapterMultiple, mainItems, centerBannerAdapterViewPager)
        productThreeGettingData(adapterMultiple, mainItems, containerProductsAdapterThree)
        downBannerGettingData(adapterMultiple, mainItems, downBannerAdapterViewPager)
        productFoureGettingData(adapterMultiple, mainItems, containerProductsAdapterFoure)

//        val navController = findNavController()
//        navController = navHostFragment.navController
//        requireActivity().onBackPressedDispatcher.addCallback(
//            viewLifecycleOwner,
//            backPressCallBack()
//        )

//
//        val editText = activity?.findViewById<EditText>(R.id.searchTextInput)
//
//
//        editText?.setOnTouchListener { v, event ->
//
//            if (navController.currentDestination?.id != R.id.searchHintProductHomeFragment){
//                navController.navigate(R.id.action_homeFragment_to_searchHintProductHomeFragment)
//            }
//
//            v.performClick()
//            v.onTouchEvent(event)
//
//        }

    }


    fun setupAdapter(adapterMultiple: MultipleTypesAdapter, mainItems: MutableSet<RowType>) {
        adapterMultiple.setHasStableIds(true)
        mutipleHomeRecyclerView.layoutManager = LinearLayoutManager(context)
        mutipleHomeRecyclerView.adapter = adapterMultiple
        mutipleHomeRecyclerView.setHasFixedSize(false);
        adapterMultiple.setData(mainItems)
    }


    fun <T> gettingErrors(resource: Resource<T>): Boolean {
        return !(resource.status == Resource.Status.ERROR || resource.status == Resource.Status.LOADING || resource.data == null || resource.exception != null)
    }

    fun <T> errorhandling(name: String, resource: Resource<T>) {
        Log.v(name, "${resource.exception?.message}")
        Log.v(name, "${resource.exception}")
        Log.v(name, "${resource.status}")
    }

    fun startBannerGettingData(
        adapterMultiple: MultipleTypesAdapter,
        mainItems: MutableSet<RowType>,
        startBannerAdapterViewPager: BannerAdapter
    ) {

        viewModel.bannerListStart.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    startBannerAdapterViewPager.setData(lists)
                    mainItems.add(BannerRowType(startBannerAdapterViewPager))
                    adapterMultiple.setData(mainItems)
                }
            } else {
                errorhandling("ERROR BOARDING START", resource)
            }
        })
    }

    fun categoryGettingData(
        adapterMultiple: MultipleTypesAdapter,
        mainItems: MutableSet<RowType>,
        combinationProductAdapterViewPager: CombinationProductsAdapter
    ) {
        viewModel.categoryProductliveData.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    combinationProductAdapterViewPager.setData(lists)
                    mainItems.add(CategoryRowType(combinationProductAdapterViewPager))
                    adapterMultiple.setData(mainItems)
                }
            } else {
                errorhandling("ERROR CATEGORY", resource)
            }
        })
    }

    fun historyGettingData(
        adapterMultiple: MultipleTypesAdapter,
        mainItems: MutableSet<RowType>
    ) {
        viewModel.historyItemsLiveData.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    mainItems.add(HistoryRowType(lists))
                    adapterMultiple.setData(mainItems)
                }
            } else {
                errorhandling("ERROR HISTORY", resource)
            }
        })
    }

    fun liveGettingData(
        adapterMultiple: MultipleTypesAdapter,
        mainItems: MutableSet<RowType>,
        liveItemAdapterViewPager: LiveItemAdapter
    ) {
        viewModel.liveItemsLiveData.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    liveItemAdapterViewPager.setData(lists)
                    mainItems.add(LiveRowType(liveItemAdapterViewPager))
                    adapterMultiple.setData(mainItems)
                }
            } else {
                errorhandling("ERROR LIVE", resource)
            }
        })
    }

    fun productOneGettingData(
        adapterMultiple: MultipleTypesAdapter,
        mainItems: MutableSet<RowType>,
        containerProductsAdapter: ContainerProductsAdapter
    ) {
        viewModel.listPoductsLiveData.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    containerProductsAdapter.setData(lists)
                    mainItems.add(ProductsRowType(containerProductsAdapter))
                    mainItems.add(RegistrationRowType(0))
                    adapterMultiple.setData(mainItems)
                }
            } else {
                errorhandling("ERROR PRODUCT 1", resource)
            }
        })
    }

    fun productTwoGettingData(
        adapterMultiple: MultipleTypesAdapter,
        mainItems: MutableSet<RowType>,
        containerProductsAdapterTwo: ContainerProductsAdapter
    ) {
        viewModel.listPoductsLiveData2.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    containerProductsAdapterTwo.setData(lists)
                    mainItems.add(ProductsRowType(containerProductsAdapterTwo))
                    adapterMultiple.setData(mainItems)
                }
            } else {
                errorhandling("ERROR PRODUCT 2", resource)
            }
        })
    }

    fun centerBannerGettingData(
        adapterMultiple: MultipleTypesAdapter,
        mainItems: MutableSet<RowType>,
        centerBannerAdapterViewPager: BannerAdapter
    ) {
        viewModel.bannerListCenter.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    centerBannerAdapterViewPager.setData(lists)
                    mainItems.add(BannerRowType(centerBannerAdapterViewPager))
                    adapterMultiple.setData(mainItems)
                }
            } else {
                errorhandling("ERROR Banner Center", resource)
            }
        })
    }

    fun productThreeGettingData(
        adapterMultiple: MultipleTypesAdapter,
        mainItems: MutableSet<RowType>,
        containerProductsAdapterThree: ContainerProductsAdapter
    ) {
        viewModel.listPoductsLiveData3.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    containerProductsAdapterThree.setData(lists)
                    mainItems.add(ProductsRowType(containerProductsAdapterThree))
                    adapterMultiple.setData(mainItems)
                }
            } else {
                errorhandling("ERROR Produt 3", resource)
            }
        })
    }

    fun downBannerGettingData(
        adapterMultiple: MultipleTypesAdapter,
        mainItems: MutableSet<RowType>,
        downBannerAdapterViewPager: BannerAdapter
    ) {
        viewModel.bannerListDown.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    downBannerAdapterViewPager.setData(lists)
                    mainItems.add(BannerRowType(downBannerAdapterViewPager))
                    adapterMultiple.setData(mainItems)
                }
            } else {
                errorhandling("ERROR  Banner Down", resource)
            }
        })
    }

    fun productFoureGettingData(
        adapterMultiple: MultipleTypesAdapter,
        mainItems: MutableSet<RowType>,
        containerProductsAdapterFoure: ContainerProductsAdapter
    ) {
        viewModel.listPoductsLiveData4.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v(
                        "TAGTIME",
                        "re10 ${SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().time)}"
                    )
                    containerProductsAdapterFoure.setData(lists)
                    mainItems.add(ProductsRowType(containerProductsAdapterFoure))
                    adapterMultiple.setData(mainItems)
                }
            } else {
                errorhandling("ERROR  Product 4", resource)
            }
        })
    }








//    fun backPressCallBack() = object : OnBackPressedCallback(true) {
//        override fun handleOnBackPressed() {
//            if (navController.backStack.size >2){
//                navController.popBackStack()
//            }
//        }
//    }
//
//
//    override fun onHiddenChanged(hidden: Boolean) {
//        hide = hidden
//        if (hidden){
//            callback?.remove()
//        }else{
//            callback = backPressCallBack()
//            requireActivity().onBackPressedDispatcher.addCallback(this, callback!!)
//        }
//        super.onHiddenChanged(hidden)
//    }




}

