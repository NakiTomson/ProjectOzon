package com.appmarketplace.ozon.presentation.activityes.ui.fragments.home


import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem
import com.appmarketplace.ozon.presentation.adapters.*
import com.appmarketplace.ozon.presentation.rowType.*
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {


    private lateinit var viewModel: HomeViewModel

    lateinit var navController:NavController
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



        navController = findNavController()


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


        val editText = searchTextInput
        editText?.setOnTouchListener { v, event ->
            if (navController.currentDestination?.id != R.id.searchHintProductHomeFragment){
                navController.navigate(R.id.action_homeFragment_to_searchHintProductHomeFragment)
            }
            v.performClick()
            v.onTouchEvent(event)
        }

    }

    fun navigateToMock(
        mockImage: String = "",
        liveStrem: String = "",
        extras: FragmentNavigator.Extras,
        listOf: List<String>? = null,
        position: Int? = null
    ){

        val bundle = Bundle()
        bundle.putString("arg1", mockImage)
        bundle.putString("arg2", liveStrem)


        val action = HomeFragmentDirections.actionHomeFragmentToMockFragment(
            imageUrl = mockImage,
            videUrl = liveStrem,
            arrayHistory = listOf?.toTypedArray(),
            position = position ?: 0
        )

        navController.navigate(action, extras)
    }



    fun setupAdapter(adapterMultiple: MultipleTypesAdapter) {
        adapterMultiple.setHasStableIds(true)
        mutipleHomeRecyclerView.layoutManager =  LinearLayoutManager(context)
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
                    val banner = BannerRowType(startBannerAdapterViewPager)
                    adapterMultiple.setData(banner)

                    banner.bannerClickListener = object : BannerRowType.BannerListener {
                        override fun onClickBanner(
                            imageUrl: String,
                            imageOnboarding: RoundedImageView
                        ) {
                            val extras = FragmentNavigatorExtras(
                                imageOnboarding to imageUrl
                            )
                            navigateToMock(mockImage = imageUrl, extras = extras)
                        }
                    }

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

                    val categoryRowType = CategoryRowType(combinationProductAdapterViewPager)
                    adapterMultiple.setData(categoryRowType)

                    categoryRowType.clickOnCategoryItem =
                        object : CategoryRowType.ClickCategoryListener {
                            override fun onClickItem(data: String) {
                                val bundle = Bundle()
                                bundle.putString("category", data)
                                navController.navigate(R.id.productsListFragment, bundle)
                            }
                        }
                }
            } else {
                errorhandling("ERROR CATEGORY", resource)
            }
        })
    }

    fun historyGettingData(adapterMultiple: MultipleTypesAdapter) {

        viewModel.historyItemsLiveData.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v(
                        "TAGTIME",
                        "re3 ${SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().time)}"
                    )

                    val historyData = HistoryRowType(lists)
                    adapterMultiple.setData(historyData)
                    historyData.historClickListener = object : HistoryRowType.HistoryListener {
                        override fun onClickHistory(
                            listOf: List<String>,
                            position: Int,
                            imageView: ImageView
                        ) {
                            val extras = FragmentNavigatorExtras(
                                imageView to listOf[position]
                            )
                            navigateToMock(listOf = listOf, position = position, extras = extras)
                        }
                    }
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

                    val rowTypeLive = LiveRowType(liveItemAdapterViewPager)
                    adapterMultiple.setData(rowTypeLive)

                    rowTypeLive.liveClickListener = object : LiveRowType.LiveListener {

                        override fun onClickLive(liveUrl: String) {
//                            navigateToMock(liveStrem = liveUrl, extras = extras, extras1 = extras)
                        }
                    }
                }

            } else {
                errorhandling("ERROR LIVE", resource)
            }
        })
    }

    fun productOneGettingData(adapterMultiple: MultipleTypesAdapter) {

        viewModel.listPoductsLiveData.observe(viewLifecycleOwner, { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v(
                        "TAGTIME",
                        "re5 ${SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().time)}"
                    )
                    lists.topStringOffer?.let { adapterMultiple.setData(TopSloganRowType(it)) }

                    val rowProduct = ProductsRowType(lists.list, 3)
                    adapterMultiple.setData(rowProduct)

                    rowProduct.setClickListenerProduct = object : ProductsRowType.OnClickProduct {
                        override fun clickProduct(product: OnProductItem) {
                            val action = HomeFragmentDirections.actionGlobalDetailsProductFragement(
                                product = product
                            )
                            navController.navigate(action)
                        }
                    }

                    lists.bottonStringOffer?.let { adapterMultiple.setData(BottomSloganRowType(it)) }
                    adapterMultiple.setData(RegistrationRowType(0))

                    rowProduct
                }
            } else {
                errorhandling("ERROR PRODUCT 1", resource)
            }
        })
    }

    fun productTwoGettingData(adapterMultiple: MultipleTypesAdapter) {

        viewModel.listPoductsLiveData2.observe(viewLifecycleOwner, { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v(
                        "TAGTIME",
                        "re6 ${SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().time)}"
                    )
                    lists.topStringOffer?.let { adapterMultiple.setData(TopSloganRowType(it)) }

                    val rowProduct = ProductsRowType(lists.list, 3)
                    adapterMultiple.setData(rowProduct)

                    rowProduct.setClickListenerProduct = object : ProductsRowType.OnClickProduct {

                        override fun clickProduct(product: OnProductItem) {
                            val action = HomeFragmentDirections.actionGlobalDetailsProductFragement(
                                product = product
                            )
                            navController.navigate(action)
                        }
                    }
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

        viewModel.bannerListCenter.observe(viewLifecycleOwner, { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v(
                        "TAGTIME",
                        "re7 ${SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().time)}"
                    )
                    centerBannerAdapterViewPager.setData(lists)

                    val banner = BannerRowType(centerBannerAdapterViewPager)
                    adapterMultiple.setData(banner)

                    banner.bannerClickListener = object : BannerRowType.BannerListener {
                        override fun onClickBanner(
                            imageUrlBest: String,
                            imageOnboarding: RoundedImageView
                        ) {

                            val extras = FragmentNavigatorExtras(
                                imageOnboarding to imageUrlBest
                            )

                            navigateToMock(mockImage = imageUrlBest, extras = extras)
                        }
                    }
                }
            } else {
                errorhandling("ERROR Banner Center", resource)
            }
        })
    }

    fun productThreeGettingData(adapterMultiple: MultipleTypesAdapter) {

        viewModel.listPoductsLiveData3.observe(viewLifecycleOwner, { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v(
                        "TAGTIME",
                        "re8 ${SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().time)}"
                    )
                    lists.topStringOffer?.let { adapterMultiple.setData(TopSloganRowType(it)) }

                    val rowProduct = ProductsRowType(lists.list, 3)
                    adapterMultiple.setData(rowProduct)

                    rowProduct.setClickListenerProduct = object : ProductsRowType.OnClickProduct {

                        override fun clickProduct(product: OnProductItem) {
                            val action = HomeFragmentDirections.actionGlobalDetailsProductFragement(
                                product = product
                            )
                            navController.navigate(action)
                        }
                    }

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

        viewModel.bannerListDown.observe(viewLifecycleOwner, { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v(
                        "TAGTIME",
                        "re9 ${SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().time)}"
                    )
                    downBannerAdapterViewPager.setData(lists)

                    val banner = BannerRowType(downBannerAdapterViewPager)
                    adapterMultiple.setData(banner)

                    banner.bannerClickListener = object : BannerRowType.BannerListener {
                        override fun onClickBanner(
                            imageUrl: String,
                            imageOnboarding: RoundedImageView
                        ) {

                            val extras = FragmentNavigatorExtras(
                                imageOnboarding to imageUrl
                            )

                            navigateToMock(mockImage = imageUrl, extras = extras)
                        }
                    }

                }
            } else {
                errorhandling("ERROR  Banner Down", resource)
            }
        })
    }

    fun productFoureGettingData(adapterMultiple: MultipleTypesAdapter) {

        viewModel.listPoductsLiveData4.observe(viewLifecycleOwner, { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v(
                        "TAGTIME",
                        "re10 ${SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().time)}"
                    )
                    lists.topStringOffer?.let { adapterMultiple.setData(TopSloganRowType(it)) }

                    val rowProduct = ProductsRowType(lists.list, 2)
                    adapterMultiple.setData(rowProduct)

                    rowProduct.setClickListenerProduct = object : ProductsRowType.OnClickProduct {
                        override fun clickProduct(product: OnProductItem) {
                            val action = HomeFragmentDirections.actionGlobalDetailsProductFragement(
                                product = product
                            )
                            navController.navigate(action)
                        }
                    }

                    lists.bottonStringOffer?.let { adapterMultiple.setData(BottomSloganRowType(it)) }
                    stopLoadingsOneFrame()
                }
            } else {
                stopLoadingsOneFrame()
                errorhandling("ERROR  Product 4", resource)
            }
        })
    }

    fun stopLoadingsOneFrame(){
        loadingFrame.visibility = View.GONE
    }
}

