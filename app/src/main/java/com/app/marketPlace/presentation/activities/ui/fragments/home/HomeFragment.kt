package com.app.marketPlace.presentation.activities.ui.fragments.home


import android.os.Bundle
import android.transition.*
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
import com.app.marketPlace.data.remote.models.Categories
import com.app.marketPlace.data.remote.models.Stories
import com.app.marketPlace.domain.exception.NotFoundRealizationException
import com.app.marketPlace.domain.models.CombineProducts
import com.app.marketPlace.domain.models.LiveStreamItem
import com.app.marketPlace.presentation.activities.MainViewModel
import com.app.marketPlace.presentation.adapters.*
import com.app.marketPlace.presentation.extensions.launchWhenCreated
import com.app.marketPlace.presentation.extensions.launchWhenStarted
import com.app.marketPlace.presentation.factory.Resource
import com.app.marketPlace.presentation.factory.TypeResource
import com.app.marketPlace.presentation.interfaces.ProductRowType
import com.app.marketPlace.presentation.rowType.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.row_type_banner.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import kotlinx.coroutines.flow.onEach
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val mainViewModel: MainViewModel by activityViewModels()

//    val TAG = HomeFragment::class.java.simpleName

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

        val anim: Animation = AnimationUtils.loadAnimation(this.context, R.anim.lunge_from_bottom)
        val controller = LayoutAnimationController(anim)

        viewModel.completed.onEach { completed ->
            if (completed == null)return@onEach

            if (viewModel.resDataFlow.replayCache.all { it.result.data == null }){
                showError()
                return@onEach
            }
            showSuccess()
            multipleRowTypeRecyclerView.layoutAnimation = controller
            adapterMultiple.setNextDataListener = MultipleAdapter.OnNextDataListener { page ->
                if (viewModel.resDataFlow.replayCache.size <= 11){
                    viewModel.loadAdditionalData(page)
                }
            }
        }.launchWhenStarted(lifecycleScope)

        mainViewModel.networkConnection.observe(viewLifecycleOwner, {
            viewModel.netConnectionState = it
        })
    }

    private fun setupAdapter(multipleAdapter: MultipleAdapter) {
        multipleAdapter.setHasStableIds(true)
        multipleRowTypeRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
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
        viewModel.resDataFlow.onEach { resource->
            if (resource.result.data == null)return@onEach

            when(resource){
                is TypeResource.Banners -> {
                    setBanners(multipleAdapter, resource.result)
                }
                is TypeResource.Category -> {
                    setCategories(multipleAdapter,resource.result)
                }
                is TypeResource.Story -> {
                    setStories(multipleAdapter, resource.result)
                }
                is TypeResource.LiveStreams -> {
                    setLiveStreams(multipleAdapter, resource.result)
                }
                is TypeResource.Product -> {
                    val productAdapter = ProductAdapter()
                    val rowProduct = ProductRowType(resource.result.data!!.list, resource.result.data.spain, productAdapter)
                    setProducts(multipleAdapter, resource.result,rowProduct)
                }
                is TypeResource.HorizontalProduct->{
                    val productAdapter = ProductHorizontalAdapter()
                    val rowProduct = ProductHorizontalRowType(resource.result.data!!.list, resource.result.data.spain, productAdapter)
                    setProducts(multipleAdapter, resource.result,rowProduct)
                }
                is TypeResource.Registration -> {
                    setRegistration(multipleAdapter)
                }
                is TypeResource.Undefined -> {
                    throw NotFoundRealizationException("type non found ${resource.result.data} ${resource::class.java}")
                }
            }
        }.launchWhenCreated(lifecycleScope)
    }

    private fun setBanners(multipleAdapter: MultipleAdapter, resource: Resource<MutableList<Banner>>) {
        val bannerAdapter = BannerAdapter()
        val banner = BannerRowType(bannerAdapter)

        resource.data?.let { bannerAdapter.setItems(it) }
        multipleAdapter.setData(banner)
        banner.setOnBannerClickListener = BannerRowType.BannerListener { imageUrl: String, view: View ->
            val extras = FragmentNavigatorExtras(
                view to imageUrl
            )
            navigateToMock(mockImage = imageUrl, extras = extras)
        }
    }

    private fun setCategories(multipleAdapter: MultipleAdapter, resource: Resource<List<Categories>>) {
        val combinationAdapter = CombinationAdapter()

        combinationAdapter.setData(resource.data!!.take(10), resource.data.takeLast(10))

        val categoryRowType = CategoryRowType(combinationAdapter)
        multipleAdapter.setData(categoryRowType)
        categoryRowType.setOnCategoryClickListener = CategoryRowType.ClickCategoryListener { data ->
            val bundle = Bundle()
            bundle.putString("category", data)
            navController.navigate(R.id.productsListFragment, bundle)
        }
    }

    private fun setStories(multipleAdapter: MultipleAdapter,resource: Resource<Stories>) {
        val stories = StoryRowType(resource.data)
        multipleAdapter.setData(stories)
        stories.setOnStoriesClickListener = StoryRowType.HistoryListener { listOf, position, imageView ->
            val extras = FragmentNavigatorExtras(
                imageView to (listOf[position]+position)
            )
            navigateToMock(listOf = listOf, position = position, extras = extras)
        }
    }

    private fun setLiveStreams(multipleAdapter: MultipleAdapter, resource: Resource<LiveStreamItem>) {
        val liveStreamAdapter = LiveStreamAdapter()
        val rowTypeLive = LiveRowType(liveStreamAdapter)
        val videoDialog = LiveVideoDialog()

        multipleAdapter.setData(TopSloganRowType(getString(R.string.marketPlaceLive)))
        liveStreamAdapter.setData(resource.data!!)
        multipleAdapter.setData(rowTypeLive)

        rowTypeLive.setOnLiveStreamClickListener = LiveRowType.LiveListener { _, _ ->
            videoDialog.show(childFragmentManager, LiveVideoDialog.TAG)
        }
    }

    private fun setRegistration(multipleAdapter: MultipleAdapter) {
        val rowType = RegistrationRowType()
        rowType.setOnAuthorizationClickListener = RegistrationRowType.AuthorizationClickListener {
            navController.navigate(R.id.signInFragment)
        }
        multipleAdapter.setData(rowType)
    }

    private fun setProducts(multipleAdapter: MultipleAdapter, resource: Resource<CombineProducts>, rowProduct: ProductRowType) {
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
                view to product.icon!!
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

    private fun navigateToMock(mockImage: String = "", liveStream: String = "",
        extras: FragmentNavigator.Extras, listOf: List<String>? = null, position: Int? = null) {
        val bundle = Bundle()
        bundle.putString("arg1", mockImage)
        bundle.putString("arg2", liveStream)
        val action = HomeFragmentDirections.actionHomeFragmentToMockFragment(imageUrl = mockImage, videoUrl = liveStream, arrayHistory = listOf?.toTypedArray(), position = position ?: 0)
        navController.navigate(action, extras)
    }

    private fun showError() {
        homeMockIsEmpty.visibility = View.VISIBLE
    }

    private fun showSuccess() {
        homeMockIsEmpty.visibility = View.GONE
    }
}