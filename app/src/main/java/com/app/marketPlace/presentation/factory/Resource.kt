package com.app.marketPlace.presentation.factory


data class Resource<T>(
    var status: Status = Status.LOADING,
    val data: T?,
    val exception: Exception? = null
) {
    enum class Status {
        LOADING,
        COMPLETED,
        EMPTY,
        ERROR
    }

    companion object {
        fun <N> getDefSateResource(): Resource<N> {
            return Resource(data = null)
        }
    }
}