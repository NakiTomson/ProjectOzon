package com.appmarketplace.ozon.domain.exception



class NotFoundRealizationException(defValue: Any?) : Exception("not found realization for $defValue")