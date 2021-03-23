package com.app.marketPlace.presentation.activities.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.marketPlace.R
import com.app.marketPlace.data.remote.models.Banner
import com.app.marketPlace.data.remote.models.Stories
import com.app.marketPlace.domain.exception.NotFoundRealizationException
import com.app.marketPlace.domain.models.CombineProductsItem
import com.app.marketPlace.domain.models.LiveStreamItem
import com.app.marketPlace.presentation.activities.MainViewModel
import com.app.marketPlace.presentation.rowType.Resource.Type
import com.app.marketPlace.presentation.adapters.*
import com.app.marketPlace.presentation.extensions.launchWhenCreated
import com.app.marketPlace.presentation.interfaces.ProductRowType
import com.app.marketPlace.presentation.rowType.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.row_type_banner.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import kotlinx.coroutines.flow.onEach
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val mainViewModel: MainViewModel by activityViewModels()
    val TAG = HomeFragment::class.java.simpleName

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var navController:NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        val adapterMultiple = MultipleAdapter()

        setupAdapter(adapterMultiple)
        setupData(adapterMultiple)

        searchTextInput?.setOnTouchListener { view, event ->
            if (navController.currentDestination?.id != R.id.searchHintProductHomeFragment){
                navController.navigate(R.id.action_homeFragment_to_searchHintProductHomeFragment)
            }
            view.performClick()
            view.onTouchEvent(event)
        }

        val anim: Animation = AnimationUtils.loadAnimation(
            this.context,
            R.anim.lunge_from_bottom
        )

        val controller = LayoutAnimationController(anim)
        viewModel.completed.observe(viewLifecycleOwner, {
            multipleHomeRecyclerView.layoutAnimation = controller
            adapterMultiple.setNextDataListener = MultipleAdapter.OnNextDataListener {
                viewModel.loadAdditionalData()
            }
        })
        mainViewModel.networkConnection.observe(viewLifecycleOwner, {

        })
    }

    private fun setupAdapter(multipleAdapter: MultipleAdapter) {
        multipleAdapter.setHasStableIds(true)
        multipleHomeRecyclerView.layoutManager = LinearLayoutManager(context)

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

    private fun setupData(multipleAdapter: MultipleAdapter){
        viewModel.data.onEach { resource->
            if (resource.data == null)return@onEach
            when(resource.type){
                is Type.BANNER -> {
                    setBanners(multipleAdapter, resource as Resource<MutableList<Banner>>)
                }
                is Type.CATEGORIES -> {
                    setCategories(multipleAdapter, resource as Resource<MutableList<MutableList<Banner>>>)
                }
                is Type.STORIES -> {
                    setStories(multipleAdapter, resource as Resource<Stories>)
                }
                is Type.STREAMS -> {
                    setLiveStreams(multipleAdapter, resource as Resource<LiveStreamItem>)
                }
                is Type.PRODUCT -> {
                    resource as Resource<CombineProductsItem>
                    val productAdapter = ProductAdapter()
                    val rowProduct = ProductsRowType(resource.data!!.list, resource.data.spain, productAdapter)
                    setProducts(multipleAdapter, resource,rowProduct)
                }
                is Type.HORIZONTAL_PRODUCT->{
                    resource as Resource<CombineProductsItem>
                    val productAdapter = ProductHorizontalAdapter()
                    val rowProduct = ProductsHorizontalRowType(resource.data!!.list, resource.data.spain, productAdapter)
                    setProducts(multipleAdapter, resource,rowProduct)
                }
                is Type.UNDEFINED -> {
                    throw NotFoundRealizationException("type non found ${resource.data} ${resource.type}")
                }
            }
        }.launchWhenCreated(lifecycleScope)
    }

    private fun setBanners(multipleAdapter: MultipleAdapter, resource: Resource<MutableList<Banner>>) {
        val bannerAdapter  = BannerAdapter()
        resource.data!!.forEach { bannerAdapter.setItem(it) }
        val banner = BannerRowType(bannerAdapter)
        multipleAdapter.setData(banner)
        banner.setOnBannerClickListener = BannerRowType.BannerListener { imageUrl: String, view: View ->
            val extras = FragmentNavigatorExtras(
                view to imageUrl
            )
            navigateToMock(mockImage = imageUrl, extras = extras)
        }
    }

    private fun setCategories(multipleAdapter: MultipleAdapter, resource: Resource<MutableList<MutableList<Banner>>>) {
        val combinationAdapter = CombinationAdapter()
        combinationAdapter.setData(resource.data!!)
        val categoryRowType = CategoryRowType(combinationAdapter)
        multipleAdapter.setData(categoryRowType)
        categoryRowType.setOnCategoryItemClickListener = CategoryRowType.ClickCategoryListener { data ->
            val bundle = Bundle()
            bundle.putString("category", data)
            navController.navigate(R.id.productsListFragment, bundle)
        }
    }

    private fun setStories(multipleAdapter: MultipleAdapter,resource: Resource<Stories>) {
        val stories = HistoryRowType(resource.data)
        multipleAdapter.setData(stories)
        stories.setOnStoriesClickListener = HistoryRowType.HistoryListener { listOf, position, imageView ->
            val extras = FragmentNavigatorExtras(
                imageView to listOf[position]
            )
            navigateToMock(listOf = listOf, position = position, extras = extras)
        }
    }

    private fun setLiveStreams(multipleAdapter: MultipleAdapter, resource: Resource<LiveStreamItem>) {
        val liveStreamAdapter = LiveStreamAdapter()
        liveStreamAdapter.setData(resource.data!!)
        liveStreamAdapter.setData(resource.data)
        multipleAdapter.setData(TopSloganRowType(getString(R.string.marketPlaceLive)))
        val rowTypeLive = LiveRowType(liveStreamAdapter)
        multipleAdapter.setData(rowTypeLive)
        rowTypeLive.setOnLiveStreamClickListener = LiveRowType.LiveListener { liveUrl, view ->
            val extras = FragmentNavigatorExtras(view to liveUrl)
            navigateToMock(liveStream = liveUrl, extras = extras)
        }
    }


    private fun setProducts(multipleAdapter: MultipleAdapter, resource: Resource<CombineProductsItem>, rowProduct: ProductRowType) {

        resource.data!!.topOffer?.let {
            multipleAdapter.setData(TopSloganRowType(it))
        }

        multipleAdapter.setData(rowProduct)

        rowProduct.setOnHeartProductClickListener = ProductRowType.ClickListener { product ->
            mainViewModel.insertOrDeleteFavoriteProduct(product)
        }
        rowProduct.setOnBasketProductClickListener = ProductRowType.ClickListener { product ->
            mainViewModel.insertOrDeleteBasket(product)
        }
        rowProduct.setOnProductClickListener = ProductRowType.ProductClickListener { product, view ->

            val extras = FragmentNavigatorExtras(
                view to product.generalIconProductSting!!
            )
            val action = HomeFragmentDirections.actionGlobalDetailsProductFragment(
                product = product

            )
            navController.navigate(action, extras)
        }
        resource.data.bottomOffer?.let {
            multipleAdapter.setData(BottomSloganRowType(it))
        }
    }

    private fun navigateToMock(mockImage: String = "", liveStream: String = "", extras: FragmentNavigator.Extras, listOf: List<String>? = null, position: Int? = null) {
        val bundle = Bundle()
        bundle.putString("arg1", mockImage)
        bundle.putString("arg2", liveStream)
        val action = HomeFragmentDirections.actionHomeFragmentToMockFragment(imageUrl = mockImage, videoUrl = liveStream, arrayHistory = listOf?.toTypedArray(), position = position ?: 0)
        navController.navigate(action, extras)
    }
}