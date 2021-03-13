package com.app.marketPlace.presentation.activities.ui.fragments.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.marketPlace.R
import com.app.marketPlace.domain.repositories.DataBaseRepository
import com.app.marketPlace.presentation.MarketPlaceApp
import com.app.marketPlace.presentation.activities.MainViewModel
import com.app.marketPlace.presentation.activities.MainViewModelFactory

import com.app.marketPlace.presentation.adapters.*
import com.app.marketPlace.presentation.rowType.*
import com.google.firebase.auth.FirebaseAuth
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import javax.inject.Inject

class HomeFragment : Fragment(R.layout.fragment_home) {

    init {
        MarketPlaceApp.appComponent.inject(homeFragment = this)
    }

    @Inject
    lateinit var repository: DataBaseRepository

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(repository)
    }

    private val viewModel: HomeViewModel by viewModels()

    lateinit var navController:NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        viewModel.startLoading()

        val startBannerAdapterViewPager = BannerAdapter()
        val centerBannerAdapterViewPager = BannerAdapter()
        val downBannerAdapterViewPager = BannerAdapter()
        val combinationProductAdapterViewPager = CombinationProductsAdapter()
        val liveItemAdapterViewPager = LiveStreamAdapter()
        val adapterMultiple = MultipleAdapter()

        setupAdapter(adapterMultiple)
        setupBannerTop(adapterMultiple, startBannerAdapterViewPager)
        setupCategoryProducts(adapterMultiple, combinationProductAdapterViewPager)
        setupStories(adapterMultiple)
        setupLiveStreams(adapterMultiple, liveItemAdapterViewPager)
        setupFirstProducts(adapterMultiple)
        setupSecondProducts(adapterMultiple)
        setupBannerCenter(adapterMultiple, centerBannerAdapterViewPager)
        setupThirdProducts(adapterMultiple)
        setupBannerDown(adapterMultiple, downBannerAdapterViewPager)
        setupFourthProducts(adapterMultiple)
        setupFifthProducts(adapterMultiple)
        setupFinally()

        searchTextInput?.setOnTouchListener { view, event ->
            if (navController.currentDestination?.id != R.id.searchHintProductHomeFragment){
                navController.navigate(R.id.action_homeFragment_to_searchHintProductHomeFragment)
            }
            view.performClick()
            view.onTouchEvent(event)
        }
        adapterMultiple.setNextDataListener = MultipleAdapter.OnNextDataListener {
            viewModel.loadProductsFifth()
        }
    }


    private fun navigateToMock(
        mockImage: String = "",
        liveStream: String = "",
        extras: FragmentNavigator.Extras,
        listOf: List<String>? = null,
        position: Int? = null
    ) {
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

    private fun setupAdapter(multipleAdapter: MultipleAdapter) {
        multipleAdapter.setHasStableIds(true)
        multipleHomeRecyclerView.layoutManager = LinearLayoutManager(context)
        multipleHomeRecyclerView.adapter = multipleAdapter

        multipleHomeRecyclerView.apply {
            adapter = multipleAdapter
            postponeEnterTransition()
            viewTreeObserver
                .addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
        }
    }

    private fun setupBannerTop(multipleAdapter: MultipleAdapter, bannerAdapter: BannerAdapter) {
        viewModel.bannerListTop.observe(viewLifecycleOwner, { resource ->
            resource.data?.forEach { bannerAdapter.setItem(it) }
            val banner = BannerRowType(bannerAdapter)
            multipleAdapter.setData(banner)
            banner.setOnBannerClickListener = BannerRowType.BannerListener { imageUrl: String, view: RoundedImageView ->
                val extras = FragmentNavigatorExtras(
                    view to imageUrl
                )
                navigateToMock(mockImage = imageUrl, extras = extras)
            }
        })
    }

    private fun setupCategoryProducts(multipleAdapter: MultipleAdapter, combinationAdapter: CombinationProductsAdapter) {
        viewModel.categoryProducts.observe(viewLifecycleOwner, { resource ->
            combinationAdapter.setData(resource.data!!)
            val categoryRowType = CategoryRowType(combinationAdapter)
            multipleAdapter.setData(categoryRowType)
            categoryRowType.setOnCategoryItemClickListener = CategoryRowType.ClickCategoryListener { data ->
                val bundle = Bundle()
                bundle.putString("category", data)
                navController.navigate(R.id.productsListFragment, bundle)
            }
        })
    }

    private fun setupStories(multipleAdapter: MultipleAdapter) {
        viewModel.storiesItems.observe(viewLifecycleOwner, { resource ->
            val stories = HistoryRowType(resource.data)
            multipleAdapter.setData(stories)
            stories.setOnStoriesClickListener = HistoryRowType.HistoryListener { listOf, position, imageView ->
                val extras = FragmentNavigatorExtras(
                    imageView to listOf[position]
                )
                navigateToMock(listOf = listOf, position = position, extras = extras)
            }
        })
    }

    private fun setupLiveStreams(multipleAdapter: MultipleAdapter, liveStreamAdapter: LiveStreamAdapter) {
        viewModel.liveStreams.observe(viewLifecycleOwner, { resource ->
            liveStreamAdapter.setData(resource.data!!)
            liveStreamAdapter.setData(resource.data)
            multipleAdapter.setData(TopSloganRowType( getString(R.string.marketPlaceLive)))
            val rowTypeLive = LiveRowType(liveStreamAdapter)
            multipleAdapter.setData(rowTypeLive)
            rowTypeLive.setOnLiveStreamClickListener = LiveRowType.LiveListener { liveUrl, view ->
                val extras = FragmentNavigatorExtras(view to liveUrl)
                navigateToMock(liveStream = liveUrl, extras = extras)
            }
        })
    }

    private fun setupFirstProducts(multipleAdapter: MultipleAdapter) {
        viewModel.firstPartProducts.observe(viewLifecycleOwner, { resource ->
            resource.data!!.topStringOffer?.let { multipleAdapter.setData(TopSloganRowType(it)) }
            val productAdapter = ProductItemAdapter()
            val rowProduct = ProductsRowType(resource.data.list, 3, productAdapter)
            multipleAdapter.setData(rowProduct)

            rowProduct.setOnHeartProductClickListener = ProductsRowType.ClickListener { product ->
                mainViewModel.insertOrDeleteFavoriteProduct(product)
            }
            rowProduct.setOnBasketProductClickListener = ProductsRowType.ClickListener { product ->
                mainViewModel.insertOrDeleteBasket(product)
            }
            rowProduct.setOnProductClickListener = ProductsRowType.ProductClickListener { product, view ->
                val extras = FragmentNavigatorExtras(view to product.generalIconProductSting!!)
                val action = HomeFragmentDirections.actionGlobalDetailsProductFragment(product = product)
                navController.navigate(action, extras)
            }
            resource.data.boltonStringOffer?.let { multipleAdapter.setData(BottomSloganRowType(it)) }
            val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
            if (mAuth.currentUser == null) {
                val regRowType = RegistrationRowType()
                multipleAdapter.setData(regRowType)
                regRowType.setOnAuthorizationClickListener = RegistrationRowType.AuthorizationClickListener {
                    navController.navigate(R.id.signInFragment)
                }
            }
        })
    }

    private fun setupSecondProducts(multipleAdapter: MultipleAdapter) {
        viewModel.secondPartProducts.observe(viewLifecycleOwner, { resource ->
            resource.data!!.topStringOffer?.let { multipleAdapter.setData(TopSloganRowType(it)) }
            val productAdapter = ProductItemAdapter()
            val rowProduct = ProductsRowType(resource.data.list, 3, productAdapter)
            multipleAdapter.setData(rowProduct)
            rowProduct.setOnHeartProductClickListener = ProductsRowType.ClickListener { product ->
                mainViewModel.insertOrDeleteFavoriteProduct(product)
            }
            rowProduct.setOnBasketProductClickListener = ProductsRowType.ClickListener { product ->
                mainViewModel.insertOrDeleteBasket(product)
            }
            rowProduct.setOnProductClickListener = ProductsRowType.ProductClickListener { product, view ->
                val extras = FragmentNavigatorExtras(view to product.generalIconProductSting!!)
                val action = HomeFragmentDirections.actionGlobalDetailsProductFragment(product = product)
                navController.navigate(action, extras)
            }
            resource.data.boltonStringOffer?.let { multipleAdapter.setData(BottomSloganRowType(it)) }
        })
    }

    private fun setupBannerCenter(multipleAdapter: MultipleAdapter, centerBannerAdapterViewPager: BannerAdapter) {
        viewModel.bannerListCenter.observe(viewLifecycleOwner, { resource ->
            resource.data!!.forEach { centerBannerAdapterViewPager.setItem(it) }
            val banner = BannerRowType(centerBannerAdapterViewPager)
            multipleAdapter.setData(banner)
            banner.setOnBannerClickListener = BannerRowType.BannerListener { imageUrl, view ->
                val extras = FragmentNavigatorExtras(
                    view to imageUrl
                )
                navigateToMock(mockImage = imageUrl, extras = extras)
            }
        })
    }

    private fun setupThirdProducts(multipleAdapter: MultipleAdapter) {
        viewModel.thirdPartProducts.observe(viewLifecycleOwner, { resource ->
            resource.data!!.topStringOffer?.let { multipleAdapter.setData(TopSloganRowType(it)) }
            val productAdapter = ProductItemAdapter()
            val rowProduct = ProductsRowType(resource.data.list, 3, productAdapter)
            multipleAdapter.setData(rowProduct)
            rowProduct.setOnHeartProductClickListener = ProductsRowType.ClickListener { product ->
                mainViewModel.insertOrDeleteFavoriteProduct(product)
            }
            rowProduct.setOnBasketProductClickListener = ProductsRowType.ClickListener { product ->
                mainViewModel.insertOrDeleteBasket(product)
            }
            rowProduct.setOnProductClickListener = ProductsRowType.ProductClickListener { product, view ->
                val extras = FragmentNavigatorExtras(view to product.generalIconProductSting!!)
                val action = HomeFragmentDirections.actionGlobalDetailsProductFragment(product = product)
                navController.navigate(action, extras)
            }
            resource.data.boltonStringOffer?.let { multipleAdapter.setData(BottomSloganRowType(it)) }
        })
    }

    private fun setupBannerDown(multipleAdapter: MultipleAdapter, downBannerAdapterViewPager: BannerAdapter) {
        viewModel.bannerListDown.observe(viewLifecycleOwner, { resource ->
            resource.data!!.forEach { downBannerAdapterViewPager.setItem(it) }
            val banner = BannerRowType(downBannerAdapterViewPager)
            multipleAdapter.setData(banner)
            banner.setOnBannerClickListener = BannerRowType.BannerListener { imageUrl, view ->
                val extras = FragmentNavigatorExtras(view to imageUrl)
                navigateToMock(mockImage = imageUrl, extras = extras)
            }
        })
    }

    private fun setupFourthProducts(multipleAdapter: MultipleAdapter) {
        viewModel.fourthPartProducts.observe(viewLifecycleOwner, { resource ->
            resource.data!!.topStringOffer?.let { multipleAdapter.setData(TopSloganRowType(it)) }
            val productAdapter = ProductItemAdapter()
            val rowProduct = ProductsRowType(resource.data.list, 2, productAdapter)
            multipleAdapter.setData(rowProduct)
            rowProduct.setOnHeartProductClickListener = ProductsRowType.ClickListener { product ->
                mainViewModel.insertOrDeleteFavoriteProduct(product)
            }
            rowProduct.setOnBasketProductClickListener = ProductsRowType.ClickListener { product ->
                mainViewModel.insertOrDeleteBasket(product)
            }
            rowProduct.setOnProductClickListener = ProductsRowType.ProductClickListener { product, view ->
                val extras = FragmentNavigatorExtras(view to product.generalIconProductSting!!)
                val action = HomeFragmentDirections.actionGlobalDetailsProductFragment(product = product)
                navController.navigate(action, extras)
            }
            resource.data.boltonStringOffer?.let { multipleAdapter.setData(BottomSloganRowType(it)) }
        })
    }

    private fun setupFifthProducts(multipleAdapter: MultipleAdapter) {
        viewModel.fifthPartProducts.observe(viewLifecycleOwner, androidx.lifecycle.Observer { resource->
            resource.data!!.topStringOffer?.let { multipleAdapter.setData(TopSloganRowType(it)) }
            val productAdapter = ProductItemAdapter()
            val rowProduct = ProductsRowType(resource.data.list, 2, productAdapter)
            multipleAdapter.setData(rowProduct)
            rowProduct.setOnHeartProductClickListener = ProductsRowType.ClickListener { product ->
                mainViewModel.insertOrDeleteFavoriteProduct(product)
            }
            rowProduct.setOnBasketProductClickListener = ProductsRowType.ClickListener { product ->
                mainViewModel.insertOrDeleteBasket(product)
            }
            rowProduct.setOnProductClickListener = ProductsRowType.ProductClickListener { product, view ->
                val extras = FragmentNavigatorExtras(view to product.generalIconProductSting!!)
                val action = HomeFragmentDirections.actionGlobalDetailsProductFragment(product = product)
                navController.navigate(action, extras)
            }
            resource.data.boltonStringOffer?.let { multipleAdapter.setData(BottomSloganRowType(it)) }
        })
    }

    private fun setupFinally(){
        viewModel.completed.observe(viewLifecycleOwner, { loadingFrame.visibility = View.GONE })
    }
}

