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
import androidx.fragment.app.*
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
import com.app.marketPlace.domain.mappers.Mapper
import com.app.marketPlace.data.remote.models.Banner
import com.app.marketPlace.domain.models.ProductItem
import com.app.marketPlace.domain.repositories.DataBaseRepository
import com.app.marketPlace.presentation.MarketPlaceApp
import com.app.marketPlace.presentation.activities.MainViewModel
import com.app.marketPlace.presentation.activities.MainViewModelFactory
import com.app.marketPlace.presentation.activities.ui.fragments.description.DescriptionFragment
import com.app.marketPlace.presentation.adapters.BannerAdapter
import com.app.marketPlace.presentation.adapters.BorderAdapter
import com.app.marketPlace.presentation.adapters.ProductAdapter
import com.app.marketPlace.presentation.adapters.SimpleAdapter
import com.app.marketPlace.presentation.interfaces.ProductRowType
import com.app.marketPlace.presentation.rowType.BannerRowType
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_details_product.*
import kotlinx.android.synthetic.main.product_details_bottom.*
import kotlinx.android.synthetic.main.product_details_center.*
import kotlinx.android.synthetic.main.product_details_top.*
import javax.inject.Inject
import kotlin.math.abs


class DetailsProductFragment : Fragment(R.layout.fragment_details_product) {

    init {
        MarketPlaceApp.appComponent.inject(detailsProductFragment = this)
    }

    @Inject
    lateinit var repository: DataBaseRepository

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(repository)
    }

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

        val detailProduct:ProductItem? = args.product

        val nameProduct:String =
            detailProduct?.nameOfProduct?.replace("-", "")!!.replace("  ", " ")

        val searchWord = nameProduct
            .substring(0, nameProduct.indexOf(' ', nameProduct.indexOf(' ') + 7))
            .trim()

        viewModel.getListEquivalentProducts(searchWord)
        viewModel.getListSimilarCategory(detailProduct.categoryPath)


        initView(Mapper.reMapProduct(detailProduct))

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
                            setCustomAnimations(
                                R.anim.slide_in,
                                R.anim.fade_out,
                                R.anim.fade_in,
                                R.anim.slide_out
                            )
                            replace<DescriptionFragment>(R.id.frameDescriptionContainer, args = bundle, tag = "LONG")
                            indexTab = 0
                        }
                    }
                    1 -> {
                        childFragmentManager.commit {
                            setCustomAnimations(
                                R.anim.slide_in,
                                R.anim.fade_out,
                                R.anim.fade_in,
                                R.anim.slide_out
                            )
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

    private fun initView(detailProduct: ProductItem){
        val navController =findNavController()
        val mAuth = FirebaseAuth.getInstance()

        val basket = activity?.findViewById<MaterialButton>(R.id.in_basket_button)

        basket?.visibility = View.VISIBLE

        basket?.text = String.format(
            resources.getString(R.string.inBasket),
            detailProduct.priceWithDiscount
        )

        sellerName.text = detailProduct.company
        priceWithDiscountTextView.text = detailProduct.priceWithDiscount

        if (detailProduct.priceOlD != detailProduct.priceWithDiscount){
            priceOlDTextView.visibility = View.VISIBLE
            priceOlDTextView.text = detailProduct.priceOlD
            priceWithDiscountTextView.setTextColor(Color.RED)
        }
        nameOfProduct.text = detailProduct.nameOfProduct
        currentColor.text = detailProduct.color

        if (detailProduct.productInBasket){
            activity?.in_basket_button?.setBackgroundResource(R.drawable.button_added)
        }else{
            activity?.in_basket_button?.setBackgroundResource(R.drawable.button_next)
        }

        activity?.in_basket_button?.setOnClickListener {
            if (detailProduct.productInBasket){
                detailProduct.productInBasket = false
                it.setBackgroundResource(R.drawable.button_next)
            }else{
                detailProduct.productInBasket = true
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

        imageNext.setOnClickListener {
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
        setupSimilarAdapter()
        setupEquivalentAdapter()
    }


    private fun handleByeOneClick(mAuth:FirebaseAuth,detailProduct: ProductItem){
        if (mAuth.currentUser != null){
            val bundle = Bundle()
            bundle.putStringArrayList("images", arrayListOf(detailProduct.generalIconProductSting))
            bundle.putString("oldPrice", detailProduct.priceOlD)
            bundle.putString(
                "discount", ((detailProduct.priceOlD?.replace("$", "")?.trim())?.toFloat()
                    ?.minus(
                        (detailProduct.priceWithDiscount?.replace("$", "")?.trim())!!.toFloat()
                    )).toString()
            )
            bundle.putString("finalPrice", detailProduct.priceWithDiscount)
            navController.navigate(R.id.makingOrderFragment, bundle)
        }else {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Вы не зарегестрировались")
            builder.setMessage("Чтобы купить товар необходимо зарегистрироваться")
                .setCancelable(false)
                .setPositiveButton("Да") { dialog, id ->
                    navController.navigate(R.id.signInFragment)
                }
                .setNegativeButton("Нет") { dialog, id -> dialog.cancel()
                }

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }
    }


    private fun setupBannerAdapter(detailProduct: ProductItem){
        val adapterImages = BorderAdapter()

        detailProduct.images?.forEach {
            adapterImages.setItem(
                Banner(
                    onBoardingImageUrl = it,
                    transitionName = detailProduct.generalIconProductSting!!
                )
            )
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

    private fun setupSimpleAdapter(detailProduct: ProductItem){
        val simpleAdapter = SimpleAdapter()
        simpleAdapter.setOnClickCategoryListener = SimpleAdapter.OnClickCategoryListener { path ->
            val bundle = Bundle()
            bundle.putString("category", path)
            navController.navigate(R.id.productsListFragment, bundle)
        }

        simpleAdapter.setData(detailProduct.categoryPath?.toMutableList())
        similarCategory.layoutManager = LinearLayoutManager(context)
        similarCategory.adapter = simpleAdapter
    }

    private fun setupSimilarAdapter(){
        val adapterSimilar  = ProductAdapter()
        listProductsSimilar.adapter = adapterSimilar
        listProductsSimilar.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        adapterSimilar.setClickListenerProduct = ProductRowType.ProductClickListener { product, imageView ->
            val extras = FragmentNavigatorExtras(imageView to product.generalIconProductSting!!)
            val action = DetailsProductFragmentDirections.actionGlobalDetailsProductFragment(product = product)
            navController.navigate(action, extras)
        }

        adapterSimilar.setClickHeartProduct = ProductRowType.ClickListener { productsItem ->
            mainViewModel.insertOrDeleteFavoriteProduct(productsItem)
        }

        adapterSimilar.setClickBasketProduct = ProductRowType.ClickListener { productsItem ->
            mainViewModel.insertOrDeleteBasket(productsItem)
        }

        val animation: Animation = AnimationUtils.loadAnimation(
            context,
            R.anim.appearances_out
        )

        val controller = LayoutAnimationController(animation)
        listProductsSimilar.layoutAnimation = controller

        setupSimilarProducts(adapterSimilar)
    }

    private fun setupEquivalentAdapter(){
        val adapterEquivalent  = ProductAdapter()
        listProductsEquivalent.adapter = adapterEquivalent
        listProductsEquivalent.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        adapterEquivalent.setClickListenerProduct = ProductRowType.ProductClickListener { product, imageView ->
            val extras = FragmentNavigatorExtras(imageView to product.generalIconProductSting!!)
            val action = DetailsProductFragmentDirections.actionGlobalDetailsProductFragment(product = product)
            navController.navigate(action, extras)
        }

        adapterEquivalent.setClickHeartProduct = ProductRowType.ClickListener { productsItem ->
            mainViewModel.insertOrDeleteFavoriteProduct(productsItem)
        }

        adapterEquivalent.setClickBasketProduct = ProductRowType.ClickListener { productsItem ->
            mainViewModel.insertOrDeleteBasket(productsItem)
        }

        val animation: Animation = AnimationUtils.loadAnimation(
            context,
            R.anim.appearances_out
        )

        val controller = LayoutAnimationController(animation)
        listProductsEquivalent.layoutAnimation = controller

        setupEquivalentProducts(adapterEquivalent)
    }

    private fun setupEquivalentProducts(adapterEquivalent: ProductAdapter){
        viewModel.searchProductsResultList.observe(viewLifecycleOwner, { resource ->
            if (resource.data!!.list.isNotEmpty()) {
                adapterEquivalent.setData(resource.data.list)
                groupEquivalent.visibility = View.VISIBLE
            }
        })
    }

    private fun setupSimilarProducts(adapterSimilar: ProductAdapter){
        viewModel.productsResultList.observe(viewLifecycleOwner, { resource ->
            if (resource.data!!.list.isNotEmpty()) {
                adapterSimilar.setData(resource.data.list)
                groupSimilar.visibility = View.VISIBLE
            }
        })
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
