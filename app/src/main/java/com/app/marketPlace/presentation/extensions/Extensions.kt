package com.app.marketPlace.presentation.extensions

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import java.util.*


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
