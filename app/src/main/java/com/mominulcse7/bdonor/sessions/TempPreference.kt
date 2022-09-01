package com.mominulcse7.bdonor.sessions

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TempPreference @Inject constructor(@ApplicationContext context: Context) {
    private var prefName = "TempPreference"

    private var pref: SharedPreferences =
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    fun clearTempPreference() {
        val editor = pref.edit()
        editor.clear()
        editor.apply()
    }

}