package com.appmarketplace.ozon.presentation.activityes.ui.fragments.home


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.Interfaces.RowType
import com.appmarketplace.ozon.presentation.adapters.*
import com.appmarketplace.ozon.presentation.data.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.waitMillis
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread


class HomeFragment : Fragment() {


    private lateinit var viewModel: HomeViewModel

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

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.startLoading()


        val startBannerAdapterViewPager = BannerAdapter()
        val centerBannerAdapterViewPager = BannerAdapter()
        val downBannerAdapterViewPager = BannerAdapter()

        val combinationProductAdapterViewPager = CombinationProductsAdapter()
        val liveItemAdapterViewPager = LiveItemAdapter()


        val adapterMultiple = MultipleTypesAdapter()



        setupAdapter(adapterMultiple)


        startBannerGettingData(adapterMultiple, startBannerAdapterViewPager)
        categoryGettingData(adapterMultiple, combinationProductAdapterViewPager)
        historyGettingData(adapterMultiple)
        liveGettingData(adapterMultiple, liveItemAdapterViewPager)
        productOneGettingData(adapterMultiple)
        productTwoGettingData(adapterMultiple)
        centerBannerGettingData(adapterMultiple, centerBannerAdapterViewPager)
        productThreeGettingData(adapterMultiple)
        downBannerGettingData(adapterMultiple, downBannerAdapterViewPager)
        productFoureGettingData(adapterMultiple)

//        stopLoadingsOneFrame()
        val navController = findNavController()

        val editText = searchTextInput

        editText?.setOnTouchListener { v, event ->
            if (navController.currentDestination?.id != R.id.searchHintProductHomeFragment){
                navController.navigate(R.id.action_homeFragment_to_searchHintProductHomeFragment)
            }
            v.performClick()
            v.onTouchEvent(event)
        }

    }


    fun setupAdapter(adapterMultiple: MultipleTypesAdapter) {
        adapterMultiple.setHasStableIds(true)
        mutipleHomeRecyclerView.layoutManager = LinearLayoutManager(context)
        mutipleHomeRecyclerView.adapter = adapterMultiple
        mutipleHomeRecyclerView.setHasFixedSize(false)
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
        startBannerAdapterViewPager: BannerAdapter
    ) {

        viewModel.bannerListStart.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v(
                        "TAGTIME",
                        "re1 ${SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().time)}"
                    )
                    startBannerAdapterViewPager.setData(lists)
                    adapterMultiple.setData(BannerRowType(startBannerAdapterViewPager))
                }
            } else {
                errorhandling("ERROR BOARDING START", resource)
            }
        })
    }

    fun categoryGettingData(
        adapterMultiple: MultipleTypesAdapter,
        combinationProductAdapterViewPager: CombinationProductsAdapter
    ) {
        viewModel.categoryProductliveData.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v(
                        "TAGTIME",
                        "re2 ${SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().time)}"
                    )
                    combinationProductAdapterViewPager.setData(lists)
                    adapterMultiple.setData(CategoryRowType(combinationProductAdapterViewPager))
                }
            } else {
                errorhandling("ERROR CATEGORY", resource)
            }
        })
    }

    fun historyGettingData(
        adapterMultiple: MultipleTypesAdapter) {
        viewModel.historyItemsLiveData.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v(
                        "TAGTIME",
                        "re3 ${SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().time)}"
                    )
                    adapterMultiple.setData(HistoryRowType(lists))
                }
            } else {
                errorhandling("ERROR HISTORY", resource)
            }
        })
    }

    fun liveGettingData(
        adapterMultiple: MultipleTypesAdapter,
        liveItemAdapterViewPager: LiveItemAdapter
    ) {
        viewModel.liveItemsLiveData.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v(
                        "TAGTIME",
                        "re4 ${SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().time)}"
                    )
                    liveItemAdapterViewPager.setData(lists)
                    adapterMultiple.setData(TopSloganRowType("Ozon Live - успей купить со скидкой!"))
                    adapterMultiple.setData(LiveRowType(liveItemAdapterViewPager))
                }
            } else {
                errorhandling("ERROR LIVE", resource)
            }
        })
    }

    fun productOneGettingData(
        adapterMultiple: MultipleTypesAdapter
    ) {
        viewModel.listPoductsLiveData.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v("TAGTIME", "re5 ${SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().time)}"
                    )
                    lists.topStringOffer?.let { adapterMultiple.setData(TopSloganRowType(it)) }
                    adapterMultiple.setData(ProductsRowType(lists.list, 3))
                    lists.bottonStringOffer?.let { adapterMultiple.setData(BottomSloganRowType(it)) }
                    adapterMultiple.setData(RegistrationRowType(0))
                }
            } else {
                errorhandling("ERROR PRODUCT 1", resource)
            }
        })
    }

    fun productTwoGettingData(
        adapterMultiple: MultipleTypesAdapter
    ) {
        viewModel.listPoductsLiveData2.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v(
                        "TAGTIME",
                        "re6 ${SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().time)}"
                    )
                    lists.topStringOffer?.let { adapterMultiple.setData(TopSloganRowType(it)) }
                    adapterMultiple.setData(ProductsRowType(lists.list, 3))
                    lists.bottonStringOffer?.let { adapterMultiple.setData(BottomSloganRowType(it)) }
                }
            } else {
                errorhandling("ERROR PRODUCT 2", resource)
            }
        })
    }

    fun centerBannerGettingData(
        adapterMultiple: MultipleTypesAdapter,
        centerBannerAdapterViewPager: BannerAdapter
    ) {
        viewModel.bannerListCenter.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v(
                        "TAGTIME",
                        "re7 ${SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().time)}"
                    )
                    centerBannerAdapterViewPager.setData(lists)
                    adapterMultiple.setData(BannerRowType(centerBannerAdapterViewPager))
                }
            } else {
                errorhandling("ERROR Banner Center", resource)
            }
        })
    }

    fun productThreeGettingData(
        adapterMultiple: MultipleTypesAdapter
    ) {
        viewModel.listPoductsLiveData3.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v(
                        "TAGTIME",
                        "re8 ${SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().time)}"
                    )
                    lists.topStringOffer?.let { adapterMultiple.setData(TopSloganRowType(it)) }
                    adapterMultiple.setData(ProductsRowType(lists.list, 3))
                    lists.bottonStringOffer?.let { adapterMultiple.setData(BottomSloganRowType(it)) }
                }
            } else {
                errorhandling("ERROR Produt 3", resource)
            }
        })
    }

    fun downBannerGettingData(
        adapterMultiple: MultipleTypesAdapter,
        downBannerAdapterViewPager: BannerAdapter
    ) {
        viewModel.bannerListDown.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v(
                        "TAGTIME",
                        "re9 ${SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().time)}"
                    )
                    downBannerAdapterViewPager.setData(lists)
                    adapterMultiple.setData(BannerRowType(downBannerAdapterViewPager))
                }
            } else {
                errorhandling("ERROR  Banner Down", resource)
            }
        })
    }

    fun productFoureGettingData(
        adapterMultiple: MultipleTypesAdapter) {
        viewModel.listPoductsLiveData4.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v(
                        "TAGTIME",
                        "re10 ${SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().time)}"
                    )
                    lists.topStringOffer?.let { adapterMultiple.setData(TopSloganRowType(it)) }
                    adapterMultiple.setData(ProductsRowType(lists.list, 2))
                    lists.bottonStringOffer?.let { adapterMultiple.setData(BottomSloganRowType(it)) }
                    stopLoadingsOneFrame()
                }
            } else {
                errorhandling("ERROR  Product 4", resource)
            }
        })
    }

    fun stopLoadingsOneFrame(){
        Log.v("TAGVISNBILE","VSIS")
        loadingFrame.visibility = View.GONE
    }

}

