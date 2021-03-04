package com.appmarketplace.ozon.presentation.activityes.ui.fragments.detail

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.domain.modelsUI.OnBoardingItem
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem
import com.appmarketplace.ozon.domain.repositories.DataBaseRepository
import com.appmarketplace.ozon.presentation.OzonApp
import com.appmarketplace.ozon.presentation.activityes.MainViewModel
import com.appmarketplace.ozon.presentation.activityes.MainViewModelFactory
import com.appmarketplace.ozon.presentation.activityes.errorhandling
import com.appmarketplace.ozon.presentation.activityes.gettingErrors
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.description.DescriptionFragment
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.description.SpecificationsFragment
import com.appmarketplace.ozon.presentation.adapters.BannerAdapter
import com.appmarketplace.ozon.presentation.adapters.ProductItemAdapter
import com.appmarketplace.ozon.presentation.adapters.SimpleDataAdapter
import com.appmarketplace.ozon.presentation.rowType.ProductsRowType
import com.appmarketplace.ozon.presentation.rowType.Resource
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
        OzonApp.appComponent.inject(detailsProductFragment = this)
    }


    lateinit var viewModel:DetailsProductViewModel

    @Inject
    lateinit var repository: DataBaseRepository

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository)
    }

    val args: DetailsProductFragmentArgs by navArgs()

    var transaction:Boolean = false

    val description = DescriptionFragment(this)
    val specifications = SpecificationsFragment(this)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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


        var activeFragment:Fragment = description
        if (!transaction){
            childFragmentManager.beginTransaction().apply {
                add(R.id.frameDescriptionContainer, description, "description").show(description)
                add(R.id.frameDescriptionContainer, specifications, "specifications").hide(
                    specifications
                )
            }.commit()
        }
        transaction = true

        tabsLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        childFragmentManager.beginTransaction().hide(activeFragment)
                            .show(description).commit()
                        activeFragment = description

                    }
                    1 -> {
                        childFragmentManager.beginTransaction().hide(activeFragment)
                            .show(specifications).commit()
                        activeFragment = specifications
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        viewModel.descriptions.value = detailProduct?.longDescription
        viewModel.specifications.value = detailProduct?.shortDescription
    }


    fun initView(detailProduct: OnProductItem){
        val navController =findNavController()
        val mAuth = FirebaseAuth.getInstance()

        val baket = activity?.findViewById<MaterialButton>(R.id.inBasketButton)
        baket?.visibility = View.VISIBLE
        baket?.text = "В корзину                 ${detailProduct.priceWithDiscount}"

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
            val action = DetailsProductFragmentDirections.actionDetailsProductFragementToMockFragment(
                arrayHistory = null
            )
            navController.navigate(action)
        }
        imageNext.setOnClickListener {
            val action = DetailsProductFragmentDirections.actionDetailsProductFragementToMockFragment(
                arrayHistory = null
            )
            navController.navigate(action)
        }

        notinfyMeAboutChange.setOnClickListener {
            Toast.makeText(context, "Уведомление Включено", Toast.LENGTH_SHORT).show()
        }

        addToGift.setOnClickListener {
            val action = DetailsProductFragmentDirections.actionDetailsProductFragementToMockFragment(
                mockGiftImge = detailProduct.images?.get(0) ?: "",
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
                    .setNegativeButton("Нет") {
                            dialog, id -> dialog.cancel()
                    }

                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()
            }
        }


        val adapterImages = BannerAdapter()
        detailProduct.images?.forEach {
            adapterImages.setItem(OnBoardingItem(onBoardingImageUrl = it))
        }

        imageDetailViewPager.adapter  = adapterImages
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
                val bundel:Bundle = Bundle()
                bundel.putString("category", path)
                navController.navigate(R.id.productsListFragment, bundel)
            }
        }
        simpleAdapter.setData(detailProduct?.categoryPath?.toMutableList())
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
                val action = DetailsProductFragmentDirections.actionGlobalDetailsProductFragement(
                    product = product
                )
                navController.navigate(action)
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
                val action = DetailsProductFragmentDirections.actionGlobalDetailsProductFragement(
                    product = product
                )
                navController.navigate(action)
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
        viewModel.searchProductsResultList.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.list?.let { list ->
                    if (list.isEmpty()) errorhandling("Empty List Equivalent Product", resource)
                    adapterEquivalent.setData(list)
                }
            } else {
                errorhandling("ERROR PRODUCT 1", resource)
            }
        })
    }

    private fun setSimilar(adapterSimilar: ProductItemAdapter){
        viewModel.productsResultList.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.list?.let { list ->
                    if (list.isEmpty()) errorhandling("Empty List Similar Product", resource)
                    adapterSimilar.setData(list)
                }
            } else {
                errorhandling("ERROR PRODUCT 1", resource)
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
