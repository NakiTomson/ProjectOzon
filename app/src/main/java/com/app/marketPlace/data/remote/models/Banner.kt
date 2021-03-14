package com.app.marketPlace.data.remote.models

import com.app.marketPlace.R

data class Banner(
  val onBoardingImage:Int = R.drawable.icon_market_place_app,
  val onBoardingImageUrl:String = "https://www.dropbox.com/s/3z9sqdui2zztnws/one.png?dl=0",
  val title:String? =null,
  val description:String? =null,
  val category:String? =null,
  val transitionName:String = ""

)