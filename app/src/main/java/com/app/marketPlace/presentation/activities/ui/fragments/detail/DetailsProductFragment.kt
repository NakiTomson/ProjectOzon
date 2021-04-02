package com.app.marketPlace.presentation.activities.ui.fragments.detail

import android.graphics.Color
import android.os.Bundle
import android.transition.*
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.fragment.app.*
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.app.marketPlace.R
import com.app.marketPlace.data.remote.models.Banner
import com.app.marketPlace.domain.mappers.MapperToDb
import com.app.marketPlace.domain.models.Product
import com.app.marketPlace.presentation.activities.MainViewModel
import com.app.marketPlace.presentation.activities.ui.fragments.description.DescriptionFragment
import com.app.marketPlace.presentation.adapters.BorderAdapter
import com.app.marketPlace.presentation.adapters.ProductAdapter
import com.app.marketPlace.presentation.adapters.SimpleCategoriesAdapter
import com.app.marketPlace.presentation.extensions.launchWhenStarted
import com.app.marketPlace.presentation.interfaces.ProductRowType
import com.app.marketPlace.presentation.rowType.BannerRowType
import com.app.marketPlace.presentation.rowType.CategoryRowType
import com.app.marketPlace.presentation.factory.Resource
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_details_product.*
import kotlinx.android.synthetic.main.product_details_bottom.*
import kotlinx.android.synthetic.main.product_details_center.*
import kotlinx.android.synthetic.main.product_details_top.*
import kotlinx.coroutines.flow.onEach
import kotlin.math.abs

@AndroidEntryPoint
class DetailsProductFragment : Fragment(R.layout.fragment_details_product) {

    private val mainViewModel: MainViewModel by activityViewModels()

    private val args: DetailsProductFragmentArgs by navArgs()

    private var transaction:Boolean = false

    private var indexTab = 0

    lateinit var navController: NavController

    private val viewModel: DetailsProductViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedElementEnterTransition = TransitionSet().apply {
            addTransition(ChangeImageTransform())
            addTransition(ChangeBounds())
            addTransition(ChangeTransform())
        }

        navController = findNavController()

        val detailProduct:Product? = args.product

        val nameProduct:String =
            detailProduct?.name?.replace("-", "")!!.replace("  ", " ")

        val searchWord = nameProduct
            .substring(0, nameProduct.indexOf(' ', nameProduct.indexOf(' ') + 7))
            .trim()

        val pathId = detailProduct.categoryPath?.get(detailProduct.categoryPath.size -1)?.id ?: null

        viewModel.getListEquivalentProducts(searchWord)
        pathId?.let {
            viewModel.getListSimilarCategory(pathId)
        }

        detailProduct.categoryPath?.get(detailProduct.categoryPath.size -1)?.id ?: "null"


        initView(MapperToDb.reMapProduct(detailProduct))


        val bundle = bundleOf(
            "longDescription" to detailProduct.longDescription,
            "shortDescription" to detailProduct.shortDescription
        )

        if (savedInstanceState == null && !transaction){
            childFragmentManager.commit {
                setReorderingAllowed(true)
                add<DescriptionFragment>(
                    R.id.frameDescriptionContainer,
                    args = bundle,
                    tag = "LONG"
                )
            }
            transaction = true
        }

        val tab: TabLayout.Tab? = tabsLayout.getTabAt(indexTab)
        tab?.select()

        tabsLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        childFragmentManager.commit {
                            setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
                            replace<DescriptionFragment>(R.id.frameDescriptionContainer, args = bundle, tag = "LONG")
                            indexTab = 0
                        }
                    }
                    1 -> {
                        childFragmentManager.commit {
                            setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
                            replace<DescriptionFragment>(R.id.frameDescriptionContainer, args = bundle, tag = "SHORT")
                            indexTab  = 1
                        }
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun initView(detailProduct: Product){
        val navController = findNavController()
        val mAuth = FirebaseAuth.getInstance()

        val basket = activity?.findViewById<MaterialButton>(R.id.in_basket_button)

        basket?.visibility = View.VISIBLE
        basket?.text = String.format(
            resources.getString(R.string.inBasket),
            detailProduct.priceMinusDiscount
        )
        sellerName.text = detailProduct.company
        priceWithDiscountTextView.text = detailProduct.priceMinusDiscount
        if (detailProduct.price != detailProduct.priceMinusDiscount){
            priceOlDTextView.visibility = View.VISIBLE
            priceOlDTextView.text = detailProduct.price
            priceWithDiscountTextView.setTextColor(Color.RED)
        }
        nameOfProduct.text = detailProduct.name
        currentColor.text = detailProduct.color

        if (detailProduct.isBasket){
            activity?.in_basket_button?.setBackgroundResource(R.drawable.button_added)
        }else{
            activity?.in_basket_button?.setBackgroundResource(R.drawable.button_next)
        }

        activity?.in_basket_button?.setOnClickListener {
            if (detailProduct.isBasket){
                detailProduct.isBasket = false
                it.setBackgroundResource(R.drawable.button_next)
            }else{
                detailProduct.isBasket = true
                it.setBackgroundResource(R.drawable.button_added)
            }
            mainViewModel.insertOrDeleteBasket(detailProduct)
        }

        offerOne.setOnClickListener {
            val action = DetailsProductFragmentDirections.actionDetailsProductFragmentToMockFragment(
                arrayHistory = null
            )
            navController.navigate(action)
        }

        imageNextMoxyData.setOnClickListener {
            val action = DetailsProductFragmentDirections.actionDetailsProductFragmentToMockFragment(
                arrayHistory = null
            )
            navController.navigate(action)
        }

        notifyMeAboutChange.setOnClickListener {
            Toast.makeText(context, "Уведомление Включено", Toast.LENGTH_SHORT).show()
        }

        addToGift.setOnClickListener {
            val action = DetailsProductFragmentDirections.actionDetailsProductFragmentToMockFragment(
                mockGiftImage = detailProduct.images?.get(0) ?: "",
                arrayHistory = null
            )
            navController.navigate(action)
        }

        byeOneClick.setOnClickListener {
            handleByeOneClick(mAuth,detailProduct)
        }

        setupBannerAdapter(detailProduct)
        setupSimpleAdapter(detailProduct)
        setupProducts()
    }


    private fun handleByeOneClick(mAuth:FirebaseAuth,detailProduct: Product){
        if (mAuth.currentUser != null){
            val bundle = Bundle()
            bundle.putStringArrayList("images", arrayListOf(detailProduct.icon))
            bundle.putString("oldPrice", detailProduct.price)
            bundle.putString(
                "discount", ((detailProduct.price?.replace("$", "")?.trim())?.toFloat()
                    ?.minus(
                        (detailProduct.priceMinusDiscount?.replace("$", "")?.trim())!!.toFloat()
                    )).toString()
            )
            bundle.putString("finalPrice", detailProduct.priceMinusDiscount)
            navController.navigate(R.id.makingOrderFragment, bundle)
        }else {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setTitle(getString(R.string.regTitle))
            builder.setMessage(getString(R.string.regMessage))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.regYes)) { dialog, id ->
                    navController.navigate(R.id.signInFragment)
                }
                .setNegativeButton(getString(R.string.regNo)) { dialog, id -> dialog.cancel()
                }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }
    }


    private fun setupBannerAdapter(detailProduct: Product){
        val adapterImages = BorderAdapter()

        detailProduct.images?.forEach {
            adapterImages.setItem(Banner(imageUrl = it))
        }

        postponeEnterTransition()

        imageDetailViewPager.adapter = adapterImages

        adapterImages.setCompleteListener = BannerRowType.CompleteListener {
            startPostponedEnterTransition()
        }

        setupIndicator(adapterImages.itemCount)
        setCurrentIndicator(0)

        imageDetailViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        imageDetailViewPager.clipToPadding = false
        imageDetailViewPager.clipChildren = false
        imageDetailViewPager.offscreenPageLimit = 3
        imageDetailViewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = (0.85f + r * 0.15f)
        }
        imageDetailViewPager.setPageTransformer(compositePageTransformer)
    }

    private fun setupSimpleAdapter(detailProduct: Product){
        val simpleAdapter = SimpleCategoriesAdapter()
        simpleAdapter.setOnCategoryClickListener = CategoryRowType.ClickCategoryListener2 { category,view ->
            val bundle = Bundle()
            bundle.putString("category", category.id)
            navController.navigate(R.id.productsListFragment, bundle)
        }

        detailProduct.categoryPath?.let {categories->
            categories.forEach { it.image = resources.getString(R.string.linkMockImagePhone) }
            simpleAdapter.setData(categories)
        }
        similarCategory.layoutManager = LinearLayoutManager(context)
        similarCategory.adapter = simpleAdapter
    }

    private fun setupProducts(){
        viewModel.data.onEach {resource->
            if (resource.status != Resource.Status.COMPLETED)return@onEach
            if (resource.data == null) return@onEach

            val adapter = ProductAdapter()
            adapter.setClickListenerProduct = ProductRowType.ProductClickListener { product, imageView ->
                val extras = FragmentNavigatorExtras(imageView to product.icon!!)
                val action = DetailsProductFragmentDirections.actionGlobalDetailsProductFragment(product = product)
                navController.navigate(action, extras)
            }

            adapter.setClickHeartProduct = ProductRowType.ClickListener { productsItem ->
                mainViewModel.insertOrDeleteFavoriteProduct(productsItem)
            }

            adapter.setClickBasketProduct = ProductRowType.ClickListener { productsItem ->
                mainViewModel.insertOrDeleteBasket(productsItem)
            }

            val animation: Animation = AnimationUtils.loadAnimation(
                context,
                R.anim.appearances_out
            )
            val controller = LayoutAnimationController(animation)

            if(resource.data.requestName.isNullOrEmpty()){
                adapter.setData(resource.data.list)
                listProductsEquivalent.adapter = adapter
                listProductsEquivalent.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                listProductsEquivalent.layoutAnimation = controller
                groupEquivalent.visibility = View.VISIBLE
            }else{
                adapter.setData(resource.data.list)
                listProductsSimilar.adapter = adapter
                listProductsSimilar.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                listProductsEquivalent.layoutAnimation = controller
                groupSimilar.visibility = View.VISIBLE
            }
        }.launchWhenStarted(lifecycleScope)
    }


    private fun setCurrentIndicator(position: Int) {
        val childCount  = indicators_container.childCount
        for (i in 0 until  childCount){
            val imageView = indicators_container.getChildAt(i) as ImageView
            if (i == position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext().applicationContext,
                        R.drawable.indicator_active_background
                    )
                )
            }else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext().applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }
    }

    private fun setupIndicator(itemCount: Int) {
        if (indicators_container.children.count() >0) return
        val indicators = arrayOfNulls<ImageView>(itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8, 0, 8, 0)
        for ( i in indicators.indices){
            indicators[i] = ImageView(context)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext().applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
                it.layoutParams = layoutParams
                indicators_container.addView(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<MaterialButton>(R.id.in_basket_button)?.visibility = View.VISIBLE
    }

    override fun onStop() {
        activity?.findViewById<MaterialButton>(R.id.in_basket_button)?.visibility = View.GONE
        super.onStop()
    }
}
