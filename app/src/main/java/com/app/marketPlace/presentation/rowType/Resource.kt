package com.app.marketPlace.presentation.rowType

data class Resource<T>(
    val status: Status = Status.LOADING,
    val data: T?,
    val exception: Exception? = null,
    val type: Type = Type.UNDEFINED
) {
    enum class Status {
        LOADING,
        COMPLETED,
        EMPTY,
        ERROR
    }
    sealed class Type {
        object BANNER:Type()
        object CATEGORIES:Type()
        object STORIES:Type()
        object STREAMS:Type()
        object PRODUCT:Type()
        object HORIZONTAL_PRODUCT:Type()
        object UNDEFINED:Type()
    }
}