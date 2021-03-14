package com.app.marketPlace.domain.exception



class NotFoundRealizationException(defValue: Any?) : Exception("not found realization for $defValue")