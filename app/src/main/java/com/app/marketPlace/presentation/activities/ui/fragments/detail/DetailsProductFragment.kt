package com.app.marketPlace.presentation.activities.ui.fragments.detail

import android.graphics.Color
import android.os.Bundle
import android.transition.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.app.marketPlace.R
import com.app.marketPlace.domain.modelsUI.OnBoardingItem
import com.app.marketPlace.domain.modelsUI.OnProductItem
import com.app.marketPlace.domain.repositories.DataBaseRepository
import com.app.marketPlace.presentation.MarketPlaceApp
import com.app.marketPlace.presentation.activities.MainViewModel
import com.app.marketPlace.presentation.activities.MainViewModelFactory
import com.app.marketPlace.presentation.activities.errorHandling
import com.app.marketPlace.presentation.activities.gettingErrors
import com.app.marketPlace.presentation.activities.ui.fragments.description.DescriptionFragment
import com.app.marketPlace.presentation.adapters.BannerAdapter
import com.app.marketPlace.presentation.adapters.ProductItemAdapter
import com.app.marketPlace.presentation.adapters.SimpleDataAdapter
import com.app.marketPlace.presentation.rowType.BannerRowType
import com.app.marketPlace.presentation.rowType.ProductsRowType
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


class DetailsProductFragment : Fragment() {

    init {
        MarketPlaceApp.appComponent.inject(detailsProductFragment = this)
    }


    private lateinit var viewModel:DetailsProductViewModel

    @Inject
    lateinit var repository: DataBaseRepository

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository)
    }

    private val args: DetailsProductFragmentArgs by navArgs()

    private var transaction:Boolean = false

    private var indexTab = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = TransitionSet().apply {
            addTransition(ChangeImageTransform())
            addTransition(ChangeBounds())
            addTransition(ChangeTransform())
        }
        return inflater.inflate(R.layout.fragment_details_product, container, false)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel = ViewModelProvider(this).get(DetailsProductViewModel::class.java)
        val detailProduct = args.product

        val nameProduct:String =
            detailProduct?.nameOfProduct?.replace("-", "")!!.replace("  ", " ")


        val searchWord = nameProduct
            .substring(0, nameProduct.indexOf(' ', nameProduct.indexOf(' ') + 7))
            .trim()

        viewModel.getListEquivalentProducts(searchWord)
        viewModel.getListSimilarCategory(detailProduct.categoryPath)

        initView(detailProduct)

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


    private fun initView(detailProduct: OnProductItem){
        val navController =findNavController()
        val mAuth = FirebaseAuth.getInstance()

        val basket = activity?.findViewById<MaterialButton>(R.id.inBasketButton)
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
            activity?.inBasketButton?.setBackgroundResource(R.drawable.button_added)
        }else{
            activity?.inBasketButton?.setBackgroundResource(R.drawable.button_next)
        }

        activity?.inBasketButton?.setOnClickListener {
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

            if (mAuth.currentUser != null){
                val bundle = Bundle()
                bundle.putString("imageUrl0", detailProduct.generalIconProductSting)
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



        val adapterImages = BannerAdapter()

        detailProduct.images?.forEach {
            adapterImages.setItem(
                OnBoardingItem(
                    onBoardingImageUrl = it,
                    transitionName = detailProduct.generalIconProductSting!!
                )
            )
        }

        postponeEnterTransition()

        imageDetailViewPager.adapter = adapterImages

        adapterImages.setCompleteListener = object : BannerRowType.CompleteListener{
            override fun onComplete() {
                startPostponedEnterTransition()
            }
        }

        setupIndicator(adapterImages.itemCount)
        setCurrentIndicator(0)

        imageDetailViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        val simpleAdapter = SimpleDataAdapter()
        simpleAdapter.setOnClickCategoryListener = object : SimpleDataAdapter.OnClickCategoryListener{
            override fun onClickCategory(path: String) {
                val bundle = Bundle()
                bundle.putString("category", path)
                navController.navigate(R.id.productsListFragment, bundle)
            }
        }
        simpleAdapter.setData(detailProduct.categoryPath?.toMutableList())
        similarCategory.layoutManager = LinearLayoutManager(context)
        similarCategory.adapter = simpleAdapter


        val adapterEquivalent  = ProductItemAdapter()
        listProductsEquivalent.adapter = adapterEquivalent
        listProductsEquivalent.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        adapterEquivalent.setClickListenerProduct = object : ProductsRowType.OnProductClickListener{
            override fun clickProduct(product: OnProductItem, imageView: ImageView) {

                val extras = FragmentNavigatorExtras(
                    imageView to product.generalIconProductSting!!
                )

                val action = DetailsProductFragmentDirections.actionGlobalDetailsProductFragment(
                    product = product
                )
                navController.navigate(action,extras)
            }
        }

        adapterEquivalent.setClickHeartProduct = object :ProductsRowType.OnClickListener{
            override fun onClick(productsItem: OnProductItem) {
                mainViewModel.insertOrDeleteFavoriteProduct(productsItem)
            }
        }


        val adapterSimilar  = ProductItemAdapter()
        listProductsSimilar.adapter = adapterSimilar
        listProductsSimilar.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        adapterSimilar.setClickListenerProduct = object : ProductsRowType.OnProductClickListener{
            override fun clickProduct(product: OnProductItem, imageView: ImageView) {

                val extras = FragmentNavigatorExtras(
                    imageView to product.generalIconProductSting!!
                )

                val action = DetailsProductFragmentDirections.actionGlobalDetailsProductFragment(
                    product = product
                )
                navController.navigate(action,extras)
            }
        }

        adapterSimilar.setClickHeartProduct = object :ProductsRowType.OnClickListener{
            override fun onClick(productsItem: OnProductItem) {
                mainViewModel.insertOrDeleteFavoriteProduct(productsItem)
            }
        }

        setEquivalent(adapterEquivalent)
        setSimilar(adapterSimilar)
    }



    private fun setEquivalent(adapterEquivalent: ProductItemAdapter){
        viewModel.searchProductsResultList.observe(viewLifecycleOwner, { resource ->
            if (gettingErrors(resource)) {
                resource.data?.list?.let { list ->
                    if (list.isEmpty()) errorHandling("Empty List Equivalent Product", resource)
                    adapterEquivalent.setData(list)
                }
            } else {
                errorHandling("ERROR PRODUCT 1", resource)
            }
        })
    }

    private fun setSimilar(adapterSimilar: ProductItemAdapter){
        viewModel.productsResultList.observe(viewLifecycleOwner, { resource ->
            if (gettingErrors(resource)) {
                resource.data?.list?.let { list ->
                    if (list.isEmpty()) errorHandling("Empty List Similar Product", resource)
                    adapterSimilar.setData(list)
                }
            } else {
                errorHandling("ERROR PRODUCT 1", resource)
            }
        })
    }

    private fun setCurrentIndicator(position: Int) {
        val childCount  = indicatorsContainer.childCount
        for (i in 0 until  childCount){
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
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
                indicatorsContainer.addView(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.findViewById<MaterialButton>(R.id.inBasketButton)?.visibility = View.GONE
    }

}
