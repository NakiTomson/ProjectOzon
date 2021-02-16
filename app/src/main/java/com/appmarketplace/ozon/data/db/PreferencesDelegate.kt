package com.appmarketplace.ozon.data.db

import android.content.SharedPreferences
import com.appmarketplace.ozon.domain.exception.NotFoundRealizationException
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PreferencesDelegate<Value>(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defValue: Value
):ReadWriteProperty<Any?,Value> {

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Value) {
        with(preferences.edit()){

            when(value){
                is Boolean -> putBoolean(name, value)
                is Int -> putInt(name, value)
                is Float -> putFloat(name, value)
                is Long -> putLong(name, value)
                is String -> putString(name, value)
                else -> throw NotFoundRealizationException(value)
            }
            apply()
        }

    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): Value {
        with(preferences){
            return when(defValue){
                is Boolean -> (getBoolean(name, defValue) as? Value) ?: defValue
                is Int -> (getInt(name, defValue) as Value) ?: defValue
                is Float -> (getFloat(name, defValue) as Value) ?: defValue
                is Long -> (getLong(name, defValue) as Value) ?: defValue
                is String -> (getString(name, defValue) as Value) ?: defValue
                else -> throw NotFoundRealizationException(defValue)
            }
        }
    }
}
class ExampleUsege(preferences: SharedPreferences){




    //TODO Переделать
    var userName: String by PreferencesDelegate(preferences, USER_NAME, "")
    var userPhone: String by PreferencesDelegate(preferences, USER_PHONE, "")
    var isShowLicence: Boolean by PreferencesDelegate(preferences, USER_LICENCE, false)


    companion object {
        private const val USER_NAME = "user_name"
        private const val USER_PHONE = "user_phone"
        private const val USER_LICENCE = "user_licence"
    }
}

