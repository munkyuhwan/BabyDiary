package com.anji.babydiary.common.MyShare

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun getLong(key: String, defValue: Long): Long {
        return prefs.getLong(key, defValue).toLong()
    }


    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }

    fun setLong(key: String, num: Long) {
        prefs.edit().putLong(key, num).apply()

    }
}

