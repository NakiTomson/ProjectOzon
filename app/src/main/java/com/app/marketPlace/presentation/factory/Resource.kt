package com.app.marketPlace.presentation.factory

data class Resource<T>(
    var status: Status = Status.LOADING,
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
        object BANNER: Type()
        object CATEGORIES: Type()
        object STORIES: Type()
        object STREAMS: Type()
        object PRODUCT: Type()
        object HORIZONTAL_PRODUCT: Type()
        object REGISTRATION: Type()
        object UNDEFINED: Type()
    }

    companion object{
        fun<N> getDefSateResource(): Resource<N> {
            return Resource(data = null)
        }

    }

}