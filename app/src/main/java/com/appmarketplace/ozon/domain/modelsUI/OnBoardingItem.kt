package com.appmarketplace.ozon.domain.modelsUI

import com.appmarketplace.ozon.R

data class OnBoardingItem(
  val onBoardingImage:Int = R.drawable.icon_app_ozon,
  val onBoardingImageUrl:String = "https://www.dropbox.com/s/3z9sqdui2zztnws/one.png?dl=0",
  val title:String? =null,
  val description:String? =null,
  val category:String? =null
)