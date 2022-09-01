package com.mominulcse7.bdonor.sessions

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LoginPreference @Inject constructor(@ApplicationContext context: Context) {

    private var prefName = "LoginPreference"
    private var apiToken = "API_TOKEN"
    private var userModel = "USER_MODEL"

    private var pref: SharedPreferences =
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    fun clearLoginPreference() {
        val editor = pref.edit()
        editor.clear()
        editor.apply()
    }

    fun sApiToken(input: String) {
        val editor = pref.edit()
        editor.putString(apiToken, input)
        editor.apply()
    }

    fun gApiToken(): String {
        return pref.getString(apiToken, "") ?: ""
    }

}