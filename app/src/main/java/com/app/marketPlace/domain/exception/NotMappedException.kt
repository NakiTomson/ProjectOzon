package com.app.marketPlace.domain.exception



class NotMappedException(defValue: Any?) : Exception("not mapped for $defValue")