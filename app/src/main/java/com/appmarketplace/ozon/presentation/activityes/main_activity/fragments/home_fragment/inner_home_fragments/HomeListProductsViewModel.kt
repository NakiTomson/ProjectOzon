package com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.home_fragment.inner_home_fragments

import androidx.lifecycle.ViewModel
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.pojo.OnBoardingItem
import com.appmarketplace.ozon.presentation.pojo.OnLiveItem
import com.appmarketplace.ozon.presentation.pojo.OnOfferProductsItem
import com.appmarketplace.ozon.presentation.pojo.OnProductItem

class HomeListProductsViewModel : ViewModel() {

    fun getListDataCategory() = listOf(
            OnBoardingItem(
                onBoardingImage = R.drawable.icon_app_ozon,
                title = "Каталог"
            ),
            OnBoardingItem(
                onBoardingImage = R.drawable.icon_app_ozon,
                title = "Товары в рассрочку"
            ),
            OnBoardingItem(
                onBoardingImage = R.drawable.icon_app_ozon,
                title = "Акции"
            ),

            OnBoardingItem(
                onBoardingImage = R.drawable.icon_app_ozon,
                title = "Для меня"
            ),
            OnBoardingItem(
                onBoardingImage = R.drawable.icon_app_ozon,
                title = "Top Fashion"
            ),
            OnBoardingItem(
                onBoardingImage = R.drawable.icon_app_ozon,
                title = "Электроника"
            ),
            OnBoardingItem(
                onBoardingImage = R.drawable.icon_app_ozon,
                title = "Одежда и обувь"
            ),
            OnBoardingItem(
                onBoardingImage = R.drawable.icon_app_ozon,
                title = "Детские товары"
            ),
            OnBoardingItem(
                onBoardingImage = R.drawable.icon_app_ozon,
                title = "Дом и сад"
            ),
            OnBoardingItem(
                onBoardingImage = R.drawable.icon_app_ozon,
                title = "Ozon Dисконт"
            )
        )

    fun allDataForCategoryList() = listOf(getListDataCategory(), getListDataCategory())

    fun getListDataAds() = listOf(
        OnBoardingItem(onBoardingImage = R.drawable.example_ads_banner),
        OnBoardingItem(onBoardingImage = R.drawable.example_ads_banner),
        OnBoardingItem(onBoardingImage = R.drawable.example_ads_banner)
    )

    fun getListItemStreamNow(): List<OnLiveItem> {

        return listOf<OnLiveItem>(
            OnLiveItem(
                onBoardingImage = R.drawable.ic_live_example,
                onIconCompany = R.drawable.icon_app_ozon,
                countUser = "11",
                description = "Natures Miracle впервые в прямом эфире Ozon Live!",
                nameOfCompany = "CityNature",
                statusLiveStream = "В Эфире"
            ),

            OnLiveItem(
                onBoardingImage = R.drawable.ic_live_example,
                onIconCompany = R.drawable.icon_app_ozon,
                countUser = "23",
                description = "Natures Miracle впервые в прямом эфире Ozon Live!",
                nameOfCompany = "CityNature",
                statusLiveStream = "В Эфире"
            ),
            OnLiveItem(
                onBoardingImage = R.drawable.ic_live_example,
                onIconCompany = R.drawable.icon_app_ozon,
                countUser = "45",
                description = "Natures Miracle впервые в прямом эфире Ozon Live!",
                nameOfCompany = "CityNature",
                statusLiveStream = "В Эфире"
            )
        )
    }

    fun getListProducts():List<OnProductItem>{
        return  listOf(
            OnProductItem(
                generalIconProduct = R.drawable.product_by_offer_exmplae,
                favoritelIconProduct = R.drawable.unlike_favorite_products_icon_heart,
                productDiscount = "- 44%",
                isBestseller = true,
                priceWithDiscount = "3 590 ₽",
                priceOlD = "8 749 ₽",
                goToBasket = true
            ),
            OnProductItem(
                generalIconProduct = R.drawable.product_by_offer_exmplae,
                favoritelIconProduct = R.drawable.unlike_favorite_products_icon_heart,
                productDiscount = "- 44%",
                isBestseller = true,
                priceWithDiscount = "3 590 ₽",
                priceOlD = "8 749 ₽",
                goToBasket = true
            ),
            OnProductItem(
                generalIconProduct = R.drawable.product_by_offer_exmplae,
                favoritelIconProduct = R.drawable.unlike_favorite_products_icon_heart,
                productDiscount = "- 44%",
                isBestseller = true,
                priceWithDiscount = "3 590 ₽",
                priceOlD = "8 749 ₽",
                goToBasket = true
            ),
            OnProductItem(
                generalIconProduct = R.drawable.product_by_offer_exmplae,
                favoritelIconProduct = R.drawable.unlike_favorite_products_icon_heart,
                productDiscount = "- 44%",
                isBestseller = true,
                priceWithDiscount = "3 590 ₽",
                priceOlD = "8 749 ₽",
                goToBasket = true
            ),
            OnProductItem(
                generalIconProduct = R.drawable.product_by_offer_exmplae,
                favoritelIconProduct = R.drawable.unlike_favorite_products_icon_heart,
                productDiscount = "- 44%",
                isBestseller = true,
                priceWithDiscount = "3 590 ₽",
                priceOlD = "8 749 ₽",
                goToBasket = true
            ),
            OnProductItem(
                generalIconProduct = R.drawable.product_by_offer_exmplae,
                favoritelIconProduct = R.drawable.unlike_favorite_products_icon_heart,
                productDiscount = "- 44%",
                isBestseller = true,
                priceWithDiscount = "3 590 ₽",
                priceOlD = "8 749 ₽",
                goToBasket = true
            )
        )
    }

    fun getListProductsTest():List<OnProductItem>{
        return  listOf(
            OnProductItem(
                generalIconProduct = R.drawable.product_by_offer_exmplae
            ),
            OnProductItem(
                generalIconProduct = R.drawable.product_by_offer_exmplae
            ),
            OnProductItem(
                generalIconProduct = R.drawable.product_by_offer_exmplae
            ),
            OnProductItem(
                generalIconProduct = R.drawable.product_by_offer_exmplae
            ),
            OnProductItem(
                generalIconProduct = R.drawable.product_by_offer_exmplae
            ),
            OnProductItem(
                generalIconProduct = R.drawable.product_by_offer_exmplae
            )
        )
    }

    fun getListProductsTestSecond():List<OnProductItem>{
        return  listOf(
            OnProductItem(
                generalIconProduct = R.drawable.product_by_offer_exmplae,
                favoritelIconProduct = R.drawable.unlike_favorite_products_icon_heart,
                productDiscount = "- 12%",
                priceWithDiscount = "3 590 ₽",
                priceOlD = "8 749 ₽",
                goToBasket = true
            ),
            OnProductItem(
                generalIconProduct = R.drawable.product_by_offer_exmplae,
                favoritelIconProduct = R.drawable.unlike_favorite_products_icon_heart,
                productDiscount = "- 17%",
                priceWithDiscount = "3 590 ₽",
                priceOlD = "8 749 ₽",
                goToBasket = true
            ),
            OnProductItem(
                generalIconProduct = R.drawable.product_by_offer_exmplae,
                favoritelIconProduct = R.drawable.unlike_favorite_products_icon_heart,
                productDiscount = "- 32%",
                priceWithDiscount = "3 590 ₽",
                priceOlD = "8 749 ₽",
                goToBasket = true
            )
        )
    }


    fun getListProductsTestThread():List<OnProductItem>{
        return  listOf(
            OnProductItem(
                generalIconProduct = R.drawable.hot_offer_one
            ),
            OnProductItem(
                generalIconProduct = R.drawable.hot_offer_two
            ),
            OnProductItem(
                generalIconProduct = R.drawable.hot_offer_three
            )
        )
    }

    fun getListProductsByBestOffer():List<OnOfferProductsItem>{

        return listOf(
            OnOfferProductsItem(
                topStringOffer = "Лучшие предложения!",
                getListProducts(),
                bottonStringOffer = "Скидки до  80 % здесь!"
            ),

            OnOfferProductsItem(
                topStringOffer = "Это выгодно! Успей купить!",
                getListProductsTest()
            ),

            OnOfferProductsItem(
                topStringOffer = "Зимняя распродажа здесь!",
                list = getListProductsTestSecond()
            )
        )
    }


    fun getListProductsByBestOfferTwo():List<OnOfferProductsItem>{

        return listOf(
            OnOfferProductsItem(
                list =  getListProductsTestThread()
            )
        )
    }
}