package com.app.marketPlace.domain.exception



class NotFoundMappedException(defValue: Any?) : Exception("not mapped for $defValue")