package com.app.marketPlace.presentation.activities.ui.fragments.productsList

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.marketPlace.R
import com.app.marketPlace.presentation.activities.*
import com.app.marketPlace.presentation.activities.ui.fragments.home.HomeFragmentDirections
import com.app.marketPlace.presentation.adapters.ProductAdapter
import com.app.marketPlace.presentation.extensions.launchWhenStarted
import com.app.marketPlace.presentation.interfaces.ProductRowType
import com.app.marketPlace.presentation.rowType.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_products_list.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProductsListFragment : Fragment(R.layout.fragment_products_list) {

    private val mainViewModel: MainViewModel by activityViewModels()

    private val viewModel: ProductsListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        foundProductsRecyclerView.layoutManager = GridLayoutManager(activity,3)
        val productsAdapter = ProductAdapter()
        productsAdapter.setHasStableIds(true)

        foundProductsRecyclerView.apply {
            adapter = productsAdapter
            postponeEnterTransition()
            viewTreeObserver
                .addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
        }

        val anim: Animation = AnimationUtils.loadAnimation(
            this.context,
            R.anim.lunge_from_bottom
        )

        val controller = LayoutAnimationController(anim)

        productsAdapter.setClickHeartProduct = ProductRowType.ClickListener { productsItem ->
            mainViewModel.insertOrDeleteFavoriteProduct(productsItem)
        }

        productsAdapter.setClickBasketProduct = ProductRowType.ClickListener { productsItem ->
            mainViewModel.insertOrDeleteBasket(productsItem)
        }

        productsAdapter.setClickListenerProduct = ProductRowType.ProductClickListener { product, imageView ->
            val extras = FragmentNavigatorExtras(imageView to product.generalIconProductSting!!)
            val action = HomeFragmentDirections.actionGlobalDetailsProductFragment(product = product)
            navController.navigate(action, extras)
        }

        val searchWord = requireArguments().getString("arg1")
        val category = requireArguments().getString("category")

        if (!searchWord.isNullOrEmpty()){
            viewModel.loadProductsByWord(searchWord)
            searchTextInput.setText(searchWord.replace("&search="," "))
        }

        category?.let {
            viewModel.loadProductsByCategory(it)
        }

        viewModel.productsList.onEach {resource->
            if (resource.status == Resource.Status.LOADING){
                return@onEach
            }
            stopProgressBar()
            if (resource.data == null ){
                showError()
                return@onEach
            }
            resource.data.requestName?.let {
                searchTextInput.setText(it.replace("&search="," "))
            }
            productsAdapter.setData(resource.data.list)
            setAnim(controller)
            showSuccess()
        }.launchWhenStarted(lifecycleScope)

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
    private fun stopProgressBar(){
        progressBar.visibility = View.GONE
    }

    private fun showError() {
        productListMockIsEmpty.visibility = View.VISIBLE
    }

    private fun showSuccess() {
        productListMockIsEmpty.visibility = View.GONE
    }
}