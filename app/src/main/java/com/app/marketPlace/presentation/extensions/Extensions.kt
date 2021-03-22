package com.app.marketPlace.presentation.extensions

import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import com.app.marketPlace.R
import com.app.marketPlace.presentation.rowType.BannerRowType
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import java.util.*



public fun String.uploadPicture(context: Context, imageOnBoarding: RoundedImageView) {


    if (this.contains("drop".toUpperCase(Locale.ROOT))){
        Picasso.with(context)
            .load(this)
            .placeholder(R.drawable.example_ads_banner)
            .noFade()
            .into(imageOnBoarding)
    }else{
        Picasso.with(context).load(this)
            .centerInside()
            .resize(500,500)
            .into(imageOnBoarding,object : Callback {
                override fun onSuccess() {
//                    setCompleteListener?.onComplete()
                }
                override fun onError() {
//                    setCompleteListener?.onComplete()
                }
            })
    }
}

fun <T> Flow<T>.launchWhenCreated(lifecycleScope: LifecycleCoroutineScope){
    lifecycleScope.launchWhenCreated {
        this@launchWhenCreated.collect()
    }
}

fun <T> Flow<T>.launchWhenStarted(lifecycleScope: LifecycleCoroutineScope){
    lifecycleScope.launchWhenStarted {
        this@launchWhenStarted.collect()
    }
}
