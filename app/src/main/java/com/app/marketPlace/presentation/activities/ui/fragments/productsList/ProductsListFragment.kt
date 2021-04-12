package com.app.marketPlace.presentation.activities.ui.fragments.productsList

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.app.marketPlace.R
import com.app.marketPlace.data.utils.Constants
import com.app.marketPlace.domain.models.Product
import com.app.marketPlace.domain.models.Params
import com.app.marketPlace.presentation.activities.MainViewModel
import com.app.marketPlace.presentation.activities.ui.fragments.home.HomeFragmentDirections
import com.app.marketPlace.presentation.adapters.LoaderStateAdapter
import com.app.marketPlace.presentation.adapters.RemoteProductAdapter
import com.app.marketPlace.presentation.adapters.RemoteProductAdapter.CategoryOfferItemProductViewHolder.Companion.setClickBasketProduct
import com.app.marketPlace.presentation.adapters.RemoteProductAdapter.CategoryOfferItemProductViewHolder.Companion.setClickHeartProduct
import com.app.marketPlace.presentation.adapters.RemoteProductAdapter.CategoryOfferItemProductViewHolder.Companion.setClickListenerProduct
import com.app.marketPlace.presentation.interfaces.ProductRowType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_products_list.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsListFragment : Fragment(R.layout.fragment_products_list) {

    private val mainViewModel: MainViewModel by activityViewModels()

    private val viewModel: ProductsListViewModel by viewModels()

    private var searchWord: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchWord = requireArguments().getString("arg1")
        val category = requireArguments().getString("category")

        val params  = Params.ProductsParams(pageSize = "40", typeProduct = Product.Type.ProductWithName)

        searchWord?.let {
            params.attributes  = Constants.attrSearch
            params.pathId  = searchWord.toString()
            params.requestName = searchWord.toString()
        }
        category?.let {
            params.attributes  = Constants.attrCategoryPathId
            params.pathId = category.toString()
        }
        viewModel.setDefault(params)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val navController = findNavController()

        val productsAdapter = RemoteProductAdapter()
        val loaderStateAdapter = LoaderStateAdapter { productsAdapter.retry() }


        foundProductsRecyclerView.apply {
            layoutManager = GridLayoutManager(activity, 3)
            adapter = productsAdapter.withLoadStateFooter(loaderStateAdapter)
            postponeEnterTransition()
            viewTreeObserver
                .addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
        }

        val anim: Animation = AnimationUtils.loadAnimation(this.context, R.anim.lunge_from_bottom)
        val controller = LayoutAnimationController(anim)

        viewModel.productsList.observe(viewLifecycleOwner, { sources->
            lifecycleScope.launch {
                setAnim(controller)
                productsAdapter.submitData(sources)
            }
        })



        lifecycleScope.launch {
            productsAdapter.loadStateFlow.collectLatest { loadState ->
                progressBar.isVisible = loadState.refresh is LoadState.Loading
//            retry.isVisible = loadStates.refresh !is LoadState.Loading
                productListMockIsEmpty.isVisible = (loadState.refresh is LoadState.Error || loadState.refresh is LoadState.NotLoading)
                        && productsAdapter.itemCount < 1 && loadState.append.endOfPaginationReached
            }
        }

        if (!searchWord.isNullOrEmpty()) {
            searchTextInput.setText(searchWord!!.replace("&search=", " "))
        }

        setClickHeartProduct = ProductRowType.ClickListener { productsItem ->
            mainViewModel.insertOrDeleteFavoriteProduct(productsItem)
        }

        setClickBasketProduct = ProductRowType.ClickListener { productsItem ->
            mainViewModel.insertOrDeleteBasket(productsItem)
        }

        setClickListenerProduct = ProductRowType.ProductClickListener { product, imageView ->
            val extras = FragmentNavigatorExtras(imageView to product.icon!!)
            val action = HomeFragmentDirections.actionGlobalDetailsProductFragment(product = product)
            navController.navigate(action, extras)
        }

        searchTextInput?.setOnTouchListener { view, event ->
            if (navController.currentDestination?.id != R.id.searchHintProductHomeFragment){
                val keywords = searchTextInput.text.toString().trim().replace(" ", "&search=")
                val bundle = Bundle()
                bundle.putString("arg1", keywords)
                navController.navigate(R.id.searchHintProductHomeFragment,bundle)
            }
            view.performClick()
            view.onTouchEvent(event)
        }

        foundProductsRecyclerView.viewTreeObserver.addOnPreDrawListener {
            foundProductsRecyclerView?.layoutAnimation?.start()
            true
        }
    }

    private fun setAnim(navController: LayoutAnimationController) {
        foundProductsRecyclerView.layoutAnimation = navController
    }

}