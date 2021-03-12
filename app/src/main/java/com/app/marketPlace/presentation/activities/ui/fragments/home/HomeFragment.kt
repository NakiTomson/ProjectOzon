package com.app.marketPlace.presentation.activities.ui.fragments.home


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.marketPlace.R
import com.app.marketPlace.domain.mappers.Mapper
import com.app.marketPlace.domain.modelsUI.OnProductItem
import com.app.marketPlace.domain.repositories.DataBaseRepository
import com.app.marketPlace.presentation.MarketPlaceApp
import com.app.marketPlace.presentation.activities.MainViewModel
import com.app.marketPlace.presentation.activities.MainViewModelFactory
import com.app.marketPlace.presentation.activities.errorHandling
import com.app.marketPlace.presentation.activities.gettingErrors
import com.app.marketPlace.presentation.adapters.*
import com.app.marketPlace.presentation.rowType.*
import com.google.firebase.auth.FirebaseAuth
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class HomeFragment : Fragment() {

    init {
        MarketPlaceApp.appComponent.inject(homeFragment = this)
    }

    @Inject
    lateinit var repository: DataBaseRepository

    @Inject
    lateinit var mapper: Mapper

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository)
    }

    private lateinit var viewModel: HomeViewModel

    lateinit var navController:NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
        productForeGettingData(adapterMultiple)


        val editText = searchTextInput
        editText?.setOnTouchListener { v, event ->
            if (navController.currentDestination?.id != R.id.searchHintProductHomeFragment){
                navController.navigate(R.id.action_homeFragment_to_searchHintProductHomeFragment)
            }
            v.performClick()
            v.onTouchEvent(event)
        }

    }

    fun navigateToMock(mockImage: String = "", liveStream: String = "", extras: FragmentNavigator.Extras, listOf: List<String>? = null, position: Int? = null) {
        val bundle = Bundle()
        bundle.putString("arg1", mockImage)
        bundle.putString("arg2", liveStream)

        val action = HomeFragmentDirections.actionHomeFragmentToMockFragment(
            imageUrl = mockImage,
            videoUrl = liveStream,
            arrayHistory = listOf?.toTypedArray(),
            position = position ?: 0
        )
        navController.navigate(action, extras)
    }


    private fun setupAdapter(adapterMultiple: MultipleTypesAdapter) {
        adapterMultiple.setHasStableIds(true)
        multipleHomeRecyclerView.layoutManager =  LinearLayoutManager(context)
        multipleHomeRecyclerView.adapter = adapterMultiple

        multipleHomeRecyclerView.apply {
            adapter = adapterMultiple
            postponeEnterTransition()
            viewTreeObserver
                .addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
        }
        multipleHomeRecyclerView.setHasFixedSize(false)
    }

    private fun startBannerGettingData(adapterMultiple: MultipleTypesAdapter,startBannerAdapterViewPager: BannerAdapter) {

        viewModel.bannerListStart.observe(viewLifecycleOwner, { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v("TAGTIME", "re1 ${SimpleDateFormat("HH:mm:ss.SSS", Locale.GERMAN).format(Calendar.getInstance().time)}")
                    lists.forEach { startBannerAdapterViewPager.setItem(it) }

                    val banner = BannerRowType(startBannerAdapterViewPager)
                    adapterMultiple.setData(banner)

                    banner.bannerClickListener = object : BannerRowType.BannerListener {
                        override fun onClickBanner(imageUrl: String, imageOnBoarding: RoundedImageView) {
                            val extras = FragmentNavigatorExtras(
                                imageOnBoarding to imageUrl
                            )
                            navigateToMock(mockImage = imageUrl, extras = extras)
                        }
                    }
                }
            } else {
                errorHandling("ERROR BOARDING START", resource)
            }
        })
    }

    private fun categoryGettingData(adapterMultiple: MultipleTypesAdapter, combinationProductAdapterViewPager: CombinationProductsAdapter) {
        viewModel.categoryProductLiveData.observe(viewLifecycleOwner, { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v("TAGTIME", "re2 ${SimpleDateFormat("HH:mm:ss.SSS", Locale.GERMAN).format(Calendar.getInstance().time)}")
                    combinationProductAdapterViewPager.setData(lists)

                    val categoryRowType = CategoryRowType(combinationProductAdapterViewPager)
                    adapterMultiple.setData(categoryRowType)

                    categoryRowType.clickOnCategoryItem = object : CategoryRowType.ClickCategoryListener {
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

    private fun historyGettingData(adapterMultiple: MultipleTypesAdapter) {
        viewModel.historyItemsLiveData.observe(viewLifecycleOwner, { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v("TAGTIME", "re3 ${SimpleDateFormat("HH:mm:ss.SSS",Locale.GERMAN).format(Calendar.getInstance().time)}")
                    val historyData = HistoryRowType(lists)
                    adapterMultiple.setData(historyData)
                    historyData.setHistoryClickListener = object : HistoryRowType.HistoryListener {
                        override fun onClick(listOf: List<String>, position: Int, imageView: ImageView) {
                            val extras = FragmentNavigatorExtras(
                                imageView to listOf[position]
                            )
                            navigateToMock(listOf = listOf, position = position, extras = extras)
                        }
                    }
                }
            } else {
                errorHandling("ERROR HISTORY", resource)
            }
        })
    }

    private fun liveGettingData(adapterMultiple: MultipleTypesAdapter, liveItemAdapterViewPager: LiveItemAdapter) {
        viewModel.liveItemsLiveData.observe(viewLifecycleOwner, { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v("TAGTIME", "re4 ${SimpleDateFormat("HH:mm:ss.SSS",Locale.GERMAN).format(Calendar.getInstance().time)}")
                    liveItemAdapterViewPager.setData(lists)
                    liveItemAdapterViewPager.setData(lists)
                    adapterMultiple.setData(TopSloganRowType("MarketPlace Live - успей купить со скидкой!"))
                    val rowTypeLive = LiveRowType(liveItemAdapterViewPager)
                    adapterMultiple.setData(rowTypeLive)

                    rowTypeLive.setLiveClickListener = object : LiveRowType.LiveListener {

                        override fun onClick(liveUrl: String, view: ImageView) {

                            val extras = FragmentNavigatorExtras(
                                view to liveUrl
                            )
                            navigateToMock(liveStream = liveUrl,extras = extras)
                        }
                    }
                }

            } else {
                errorHandling("ERROR LIVE", resource)
            }
        })
    }

    private fun productOneGettingData(adapterMultiple: MultipleTypesAdapter) {
        viewModel.listProductsLiveData.observe(viewLifecycleOwner, { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v("TAGTIME", "re5 ${SimpleDateFormat("HH:mm:ss.SSS",Locale.GERMAN).format(Calendar.getInstance().time)}")
                    lists.topStringOffer?.let { adapterMultiple.setData(TopSloganRowType(it)) }

                    val productAdapter = ProductItemAdapter()
                    val rowProduct = ProductsRowType(lists.list, 3,productAdapter)
                    adapterMultiple.setData(rowProduct)


                    rowProduct.setClickHeartProduct = object :ProductsRowType.OnClickListener{
                        override fun onClick(productsItem: OnProductItem) {
                            mainViewModel.insertOrDeleteFavoriteProduct(productsItem)
                        }
                    }

                    rowProduct.setClickBasketProduct = object : ProductsRowType.OnClickListener {
                        override fun onClick(productsItem: OnProductItem) {
                            mainViewModel.insertOrDeleteBasket(productsItem)
                        }
                    }

                    rowProduct.setClickListenerProduct = object : ProductsRowType.OnProductClickListener {
                        override fun clickProduct(product: OnProductItem, imageView: ImageView) {

                            val extras = FragmentNavigatorExtras(
                                imageView to product.generalIconProductSting!!
                            )

                            val action = HomeFragmentDirections.actionGlobalDetailsProductFragment(
                                product = product
                            )
                            navController.navigate(action,extras)
                        }
                    }

                    lists.boltonStringOffer?.let { adapterMultiple.setData(BottomSloganRowType(it)) }

                    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
                    if (mAuth.currentUser == null){
                        val regRowType = RegistrationRowType()
                        adapterMultiple.setData(regRowType)
                        regRowType.setClickAuthorization = object : RegistrationRowType.AuthorizationClickListener{
                            override fun onClick() {
                                navController.navigate(R.id.signInFragment)
                            }
                        }
                    }
                }
            } else {
                errorHandling("ERROR PRODUCT 1", resource)
            }
        })
    }

    private fun productTwoGettingData(adapterMultiple: MultipleTypesAdapter) {
        viewModel.listProductsLiveData2.observe(viewLifecycleOwner, { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v("TAGTIME", "re6 ${SimpleDateFormat("HH:mm:ss.SSS",Locale.GERMAN).format(Calendar.getInstance().time)}")
                    lists.topStringOffer?.let { adapterMultiple.setData(TopSloganRowType(it)) }

                    val productAdapter = ProductItemAdapter()
                    val rowProduct = ProductsRowType(lists.list, 3, productAdapter)
                    adapterMultiple.setData(rowProduct)

                    rowProduct.setClickHeartProduct = object :ProductsRowType.OnClickListener{
                        override fun onClick(productsItem: OnProductItem) {
                            mainViewModel.insertOrDeleteFavoriteProduct(productsItem)
                        }
                    }

                    rowProduct.setClickBasketProduct = object : ProductsRowType.OnClickListener {
                        override fun onClick(productsItem: OnProductItem) {
                            mainViewModel.insertOrDeleteBasket(productsItem)
                        }
                    }


                    rowProduct.setClickListenerProduct = object : ProductsRowType.OnProductClickListener {

                        override fun clickProduct(product: OnProductItem, imageView: ImageView) {

                            val extras = FragmentNavigatorExtras(
                                imageView to product.generalIconProductSting!!
                            )

                            val action = HomeFragmentDirections.actionGlobalDetailsProductFragment(
                                product = product
                            )
                            navController.navigate(action,extras)
                        }
                    }
                    lists.boltonStringOffer?.let { adapterMultiple.setData(BottomSloganRowType(it)) }
                }
            } else {
                errorHandling("ERROR PRODUCT 2", resource)
            }
        })
    }

    private fun centerBannerGettingData(adapterMultiple: MultipleTypesAdapter, centerBannerAdapterViewPager: BannerAdapter) {
        viewModel.bannerListCenter.observe(viewLifecycleOwner, { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v("TAGTIME", "re7 ${SimpleDateFormat("HH:mm:ss.SSS",Locale.GERMAN).format(Calendar.getInstance().time)}")

                    lists.forEach { centerBannerAdapterViewPager.setItem(it) }

                    val banner = BannerRowType(centerBannerAdapterViewPager)
                    adapterMultiple.setData(banner)
                    banner.bannerClickListener = object : BannerRowType.BannerListener {
                        override fun onClickBanner(imageUrlBest: String, imageOnBoarding: RoundedImageView) {
                            val extras = FragmentNavigatorExtras(
                                imageOnBoarding to imageUrlBest
                            )
                            navigateToMock(mockImage = imageUrlBest, extras = extras)
                        }
                    }
                }
            } else {
                errorHandling("ERROR Banner Center", resource)
            }
        })
    }

    private fun productThreeGettingData(adapterMultiple: MultipleTypesAdapter) {
        viewModel.listProductsLiveData3.observe(viewLifecycleOwner, { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v("TAGTIME", "re8 ${SimpleDateFormat("HH:mm:ss.SSS",Locale.GERMAN).format(Calendar.getInstance().time)}")
                    lists.topStringOffer?.let { adapterMultiple.setData(TopSloganRowType(it)) }


                    val productAdapter = ProductItemAdapter()
                    val rowProduct = ProductsRowType(lists.list, 3, productAdapter)
                    adapterMultiple.setData(rowProduct)

                    rowProduct.setClickHeartProduct = object :ProductsRowType.OnClickListener{
                        override fun onClick(productsItem: OnProductItem) {
                            mainViewModel.insertOrDeleteFavoriteProduct(productsItem)
                        }
                    }

                    rowProduct.setClickBasketProduct = object : ProductsRowType.OnClickListener {
                        override fun onClick(productsItem: OnProductItem) {
                            mainViewModel.insertOrDeleteBasket(productsItem)
                        }
                    }

                    rowProduct.setClickListenerProduct = object : ProductsRowType.OnProductClickListener {

                        override fun clickProduct(product: OnProductItem, imageView: ImageView) {
                            val extras = FragmentNavigatorExtras(
                                imageView to product.generalIconProductSting!!
                            )
                            val action = HomeFragmentDirections.actionGlobalDetailsProductFragment(
                                product = product
                            )
                            navController.navigate(action,extras)
                        }
                    }

                    lists.boltonStringOffer?.let { adapterMultiple.setData(BottomSloganRowType(it)) }
                }
            } else {
                errorHandling("ERROR Product 3", resource)
            }
        })
    }

    private fun downBannerGettingData(adapterMultiple: MultipleTypesAdapter, downBannerAdapterViewPager: BannerAdapter) {
        viewModel.bannerListDown.observe(viewLifecycleOwner, { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v("TAGTIME", "re9 ${SimpleDateFormat("HH:mm:ss.SSS",Locale.GERMAN).format(Calendar.getInstance().time)}")

                    lists.forEach { downBannerAdapterViewPager.setItem(it) }
                    val banner = BannerRowType(downBannerAdapterViewPager)

                    adapterMultiple.setData(banner)

                    banner.bannerClickListener = object : BannerRowType.BannerListener {
                        override fun onClickBanner(imageUrl: String, imageOnBoarding: RoundedImageView) {

                            val extras = FragmentNavigatorExtras(
                                imageOnBoarding to imageUrl
                            )
                            navigateToMock(mockImage = imageUrl, extras = extras)
                        }
                    }
                }
            } else {
                errorHandling("ERROR  Banner Down", resource)
            }
        })
    }

    private fun productForeGettingData(adapterMultiple: MultipleTypesAdapter) {
        viewModel.listProductsLiveData4.observe(viewLifecycleOwner, { resource ->
            if (gettingErrors(resource)) {
                resource.data?.let { lists ->
                    Log.v("TAGTIME", "re10 ${SimpleDateFormat("HH:mm:ss.SSS",Locale.GERMAN).format(Calendar.getInstance().time)}")
                    lists.topStringOffer?.let { adapterMultiple.setData(TopSloganRowType(it)) }

                    val productAdapter = ProductItemAdapter()
                    val rowProduct = ProductsRowType(lists.list, 2, productAdapter)
                    adapterMultiple.setData(rowProduct)

                    rowProduct.setClickHeartProduct = object :ProductsRowType.OnClickListener{
                        override fun onClick(productsItem: OnProductItem) {
                            mainViewModel.insertOrDeleteFavoriteProduct(productsItem)
                        }
                    }

                    rowProduct.setClickBasketProduct = object : ProductsRowType.OnClickListener {
                        override fun onClick(productsItem: OnProductItem) {
                            mainViewModel.insertOrDeleteBasket(productsItem)
                        }
                    }

                    rowProduct.setClickListenerProduct = object : ProductsRowType.OnProductClickListener {

                        override fun clickProduct(product: OnProductItem, imageView: ImageView) {

                            val extras = FragmentNavigatorExtras(
                                imageView to product.generalIconProductSting!!
                            )

                            val action = HomeFragmentDirections.actionGlobalDetailsProductFragment(
                                product = product
                            )
                            navController.navigate(action,extras)
                        }
                    }
                    lists.boltonStringOffer?.let { adapterMultiple.setData(BottomSloganRowType(it)) }
                    stopLoadingsOneFrame()
                }
            } else {
                stopLoadingsOneFrame()
                errorHandling("ERROR  Product 4", resource)
            }
        })
    }

    private fun stopLoadingsOneFrame(){
        loadingFrame.visibility = View.GONE
    }

}

