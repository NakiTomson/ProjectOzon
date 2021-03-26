package com.app.marketPlace.presentation.activities.ui.fragments.catalog


import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.*
import com.app.marketPlace.R
import com.app.marketPlace.data.remote.models.Categories
import com.app.marketPlace.presentation.adapters.CategoryAdapter2
import com.app.marketPlace.presentation.rowType.CategoryRowType
import com.app.marketPlace.presentation.rowType.Resource
import kotlinx.android.synthetic.main.fragment_catalog.*
import kotlinx.android.synthetic.main.item_simple.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext


class CatalogFragment : Fragment(R.layout.fragment_catalog) {

    private val viewModel: CatalogViewModel by viewModels()

    lateinit var navController: NavController


    private val args: CatalogFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedElementEnterTransition = TransitionSet().apply {
            addTransition(ChangeImageTransform())
            addTransition(ChangeBounds())
            addTransition(ChangeTransform())
        }

        postponeEnterTransition()

        val subCategories = args.subCategories

        navController = findNavController()

        val categoryAdapter = CategoryAdapter2()
        setupCategoriesAdapter(categoryAdapter, subCategories)
        setupCategories(categoryAdapter)

        searchTextInput?.setOnTouchListener { view, event ->
            if (navController.currentDestination?.id != R.id.searchHintProductHomeFragment){
                navController.navigate(R.id.action_homeFragment_to_searchHintProductHomeFragment)
            }
            view.performClick()
            view.onTouchEvent(event)
        }

    }

    private fun setupCategoriesAdapter(
        categoryAdapter: CategoryAdapter2,
        subCategories: Array<Categories>?
    ) {

        val layoutManager = GridLayoutManager(context, 1)
        categoryProductsAdapter.layoutManager =  layoutManager
        categoryProductsAdapter.adapter = categoryAdapter
        categoryProductsAdapter.setHasFixedSize(false)

        subCategories?.let { sub->

            val listCategories = mutableListOf<Categories>()
            listCategories.add(Categories(name = "Back",back = R.drawable.ic_left ))
            listCategories.addAll(sub.asList())
            viewModel.categoryProductLiveData.value = Resource(
                Resource.Status.COMPLETED,
                listCategories
            )
            lifecycleScope.launch {
                delay(300)
                shimmerLayoutCatalog.stopShimmer()
                shimmerLayoutCatalog.setShimmer(null)
            }

        } ?: run {
            val dividerItemDecoration = DividerItemDecoration(
                categoryProductsAdapter.context,
                layoutManager.orientation
            )
            categoryProductsAdapter.addItemDecoration(dividerItemDecoration);
            viewModel.getCatalogProducts()
            shimmerLayoutCatalog.stopShimmer()
            shimmerLayoutCatalog.setShimmer(null)
        }

        (view?.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }


        categoryAdapter.setOnCategoryClickListener = CategoryRowType.ClickCategoryListener2 { category, view ->

            category.back?.let {
                navController.popBackStack()
                return@ClickCategoryListener2
            }
            when(category.subCategories.isNullOrEmpty()){
                true -> {
                    val bundle = Bundle()
                    bundle.putString("category", category.id)
                    navController.navigate(R.id.productsListFragment, bundle)
                }
                false -> {
                    val extras = FragmentNavigatorExtras(view to category.name!!)
                    val array = ArrayList(category.subCategories).toTypedArray()
                    array[0] = Categories(
                        name = category.name,
                        id = category.id,
                        backgroundColorSelected = R.color.colorShimmer
                    )
                    val action = CatalogFragmentDirections.actionGlobalCatalogFragment(
                        array
                    )
                    navController.navigate(action, extras)
                }
            }
        }
    }

    private fun setupCategories(categoryAdapter: CategoryAdapter2) {
        viewModel.categoryProductLiveData.observe(viewLifecycleOwner, { resource ->
            categoryAdapter.setData(resource.data!!)
        })
    }
}