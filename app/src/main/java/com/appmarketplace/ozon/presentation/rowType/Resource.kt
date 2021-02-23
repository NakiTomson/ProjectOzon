package com.appmarketplace.ozon.presentation.rowType

data class Resource<T>(
    val status: Status = Status.LOADING,
    val data: T?,
    val exception: Exception? = null
) {
    enum class Status {
        LOADING,
        COMPLETED,
        ERROR
    }
}