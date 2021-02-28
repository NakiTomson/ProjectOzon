package com.appmarketplace.ozon.presentation.activityes.ui.fragments.detail

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.domain.modelsUI.OnBoardingItem
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem
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
import kotlinx.android.synthetic.main.fragment_personal_account.*
import kotlinx.android.synthetic.main.product_details_bottom.*
import kotlinx.android.synthetic.main.product_details_center.*
import kotlinx.android.synthetic.main.product_details_top.*


class DetailsProductFragement : Fragment() {


    private lateinit var viewModel: DetailsProductViewModel

    val args: DetailsProductFragementArgs by navArgs()


    var transaction:Boolean = false

    val description = DescriptionFragment(this)
    val specifications = SpecificationsFragment(this)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details_product, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsProductViewModel::class.java)
        val detailProduct = args.product


        val nameProduct:String =
            detailProduct?.nameOfProduct!!.replace("-", "").replace("  ", " ")

        val searchWord = nameProduct
            .substring(0, nameProduct.indexOf(' ', nameProduct.indexOf(' ') + 7))
            .trim()

        viewModel.getListEquivalentProducts(searchWord)

        detailProduct.categoryPath?.let { viewModel.getListSimilarCategory(it) }

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

        viewModel.descriptions.value = detailProduct.longDescription
        viewModel.specifications.value = detailProduct.shortDescription
    }


    fun initView(detailProduct: OnProductItem){
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

            viewModel.insertOrDeleteBasketProduct(detailProduct)
        }

        val navController =findNavController()

        offerOne.setOnClickListener {
            val action = DetailsProductFragementDirections.actionDetailsProductFragementToMockFragment(
                arrayHistory = null
            )
            navController.navigate(action)
        }
        imageNext.setOnClickListener {
            val action = DetailsProductFragementDirections.actionDetailsProductFragementToMockFragment(
                arrayHistory = null
            )
            navController.navigate(action)
        }

        notinfyMeAboutChange.setOnClickListener {
            Toast.makeText(context, "Уведомление Включено", Toast.LENGTH_SHORT).show()
        }

        addToGift.setOnClickListener {
            val action = DetailsProductFragementDirections.actionDetailsProductFragementToMockFragment(
                mockGiftImge = detailProduct.images?.get(0) ?: "",
                arrayHistory = null
            )
            navController.navigate(action)
        }

        val mAuth = FirebaseAuth.getInstance()
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
        detailProduct?.images?.forEach {
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


        adapterEquivalent.setClickListenerProduct = object : ProductsRowType.OnClickProduct{
            override fun clickProduct(product: OnProductItem, imageView: ImageView) {
                val action = DetailsProductFragementDirections.actionGlobalDetailsProductFragement(
                    product = product
                )
                navController.navigate(action)
            }

        }

        val adapterSimilar  = ProductItemAdapter()
        listProductsSimilar.adapter = adapterSimilar
        listProductsSimilar.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )



        adapterSimilar.setClickListenerProduct = object : ProductsRowType.OnClickProduct{
            override fun clickProduct(product: OnProductItem, imageView: ImageView) {
                val action = DetailsProductFragementDirections.actionGlobalDetailsProductFragement(
                    product = product
                )
                navController.navigate(action)
            }
        }

        adapterEquivalent.setClickHeartProduct = object :ProductsRowType.OnClickHeart{
            override fun onClickHeart(productsItem: OnProductItem) {
                viewModel.insertOrDeleteFavoriteProduct(productsItem)
            }
        }

        adapterSimilar.setClickHeartProduct = object :ProductsRowType.OnClickHeart{
            override fun onClickHeart(productsItem: OnProductItem) {
                viewModel.insertOrDeleteFavoriteProduct(productsItem)
            }
        }


        setEquivalent(adapterEquivalent)
        setSimilar(adapterSimilar)
    }


    fun <T> gettingErrors(resource: Resource<T>): Boolean {
        return !(resource.status == Resource.Status.ERROR || resource.status == Resource.Status.LOADING || resource.data == null || resource.exception != null)
    }

    fun <T> errorhandling(name: String, resource: Resource<T>) {
        Log.v(name, "${resource.exception?.message}")
        Log.v(name, "${resource.exception}")
        Log.v(name, "${resource.status}")
    }



    fun setEquivalent(adapterEquivalent: ProductItemAdapter){
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

    fun setSimilar(adapterSimilar: ProductItemAdapter){
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
