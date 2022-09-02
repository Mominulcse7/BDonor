package com.mominulcse7.bdonor.sessions

import android.content.Context
import android.content.SharedPreferences
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.mominulcse7.bdonor.utils.logPrint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppPreference @Inject constructor(@ApplicationContext context: Context) {

    private var prefName = "AppPreference"
    private var appLanguage = "APP_LANGUAGE"
    private var firebaseToken = "FIREBASE_TOKEN"

    private var pref: SharedPreferences =
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    fun clearAppPreferences() {
        val editor = pref.edit()
        editor.clear()
        editor.apply()
    }

    fun sLanguage(language: String) {
        val editor = pref.edit()
        editor.putString(appLanguage, language)
        editor.apply()
    }

    fun gLanguage(): String {
        return pref.getString(appLanguage, "en") ?: "en"
    }

    fun sDeviceToken(language: String) {
        val editor = pref.edit()
        editor.putString(firebaseToken, language)
        editor.apply()
    }

    fun gDeviceToken(): String {
        val token = pref.getString(firebaseToken, "") ?: ""
        return token.ifEmpty { loadToken() }
    }

    private fun loadToken(): String {
        var token = ""
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task: Task<String> ->
                if (!task.isSuccessful) {
                    return@addOnCompleteListener
                }
                token = task.result
                sDeviceToken(token)
            }
        logPrint(token)
        return token
    }

}