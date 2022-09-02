package com.mominulcse7.bdonor.utils

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.graphics.Paint
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.mominulcse7.bdonor.BuildConfig
import com.mominulcse7.bdonor.R
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun Activity.closeKeyboard() {
    try {
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    } catch (e: Exception) {
    }
}

fun Activity.showKeyboard() {
    try {
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(window.decorView, InputMethodManager.SHOW_IMPLICIT)
    } catch (e: Exception) {
    }
}

fun Activity.keyboardIsVisible(): Boolean {
    val isOpen = try {
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.isAcceptingText
    } catch (e: Exception) {
        false
    }
    return isOpen
}

fun Activity.haveNetwork(): Boolean {
    val connectivityManager = getSystemService(Activity.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    } else {
        @Suppress("DEPRECATION") val networkInfo =
            connectivityManager.activeNetworkInfo ?: return false
        @Suppress("DEPRECATION")
        return networkInfo.isConnected
    }
}

fun Activity.popupAlert(title: String, msg: String) {
    MaterialAlertDialogBuilder(this).setCancelable(false)
        .setIcon(R.drawable.ic_alert)
        .setTitle(title)
        .setMessage(msg)
        .setPositiveButton(resources.getString(R.string.ok)) { dialog, _ ->
            dialog.dismiss()
        }.show()
}

fun Activity.popupInfo(title: String, msg: String) {
    MaterialAlertDialogBuilder(this).setCancelable(false)
        .setIcon(R.drawable.ic_info)
        .setTitle(title)
        .setMessage(msg)
        .setPositiveButton(resources.getString(R.string.ok)) { dialog, _ ->
            dialog.dismiss()
        }.show()
}

fun Context.popupAlertContext(title: String, msg: String) {
    MaterialAlertDialogBuilder(this).setCancelable(false)
        .setIcon(R.drawable.ic_alert)
        .setTitle(title)
        .setMessage(msg)
        .setPositiveButton(resources.getString(R.string.ok)) { dialog, _ ->
            dialog.dismiss()
        }.show()
}

fun Activity.popupNotification(title: String, msg: String) {
    val alert = AlertDialog.Builder(this)
    alert.setMessage(msg)
    alert.setCancelable(false)
    alert.setIcon(R.drawable.ic_notification_color)
    alert.setTitle(title)
    alert.setNegativeButton(R.string.ok) { dialogInterface, i -> dialogInterface.cancel() }
    val alertDialog = alert.create()
    alertDialog.show()
}

fun Activity.popupSuccess(title: String, msg: String) {
    MaterialAlertDialogBuilder(this).setCancelable(false)
        .setIcon(R.drawable.ic_accept_ok_valid)
        .setTitle(title)
        .setMessage(msg)
        .setPositiveButton(resources.getString(R.string.ok)) { dialog, _ ->
            dialog.dismiss()
        }.show()
}

fun Activity.progressDialog(msg: String): AlertDialog {
    return MaterialAlertDialogBuilder(this).setCancelable(false)
        .setMessage(msg).create()
}

fun Context.toastShow(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Activity.popupNoInternet() {
    MaterialAlertDialogBuilder(this).setCancelable(false)
        .setIcon(R.drawable.ic_no_internet)
        .setTitle(resources.getString(R.string.no_internet))
        .setMessage(resources.getString(R.string.turn_on_internet))
        .setPositiveButton(resources.getString(R.string.open_settings)) { dialog, _ ->
            dialog.dismiss()
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }.setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }.show()
}

fun Activity.etStringToLong(stNumber: String): Long {
    stNumber.replace(" ", "")
    return try {
        stNumber.toLong()
    } catch (e: Exception) {
        0L
    }
}

fun Activity.etStringToDouble(stNumber: String): Double {
    stNumber.replace(" ", "")
    return try {
        stNumber.toDouble()
    } catch (e: Exception) {
        0.0
    }
}

fun isValidLongNumber(stNumber: String): Boolean {
    return !(stNumber.contains(".")
            || stNumber.contains(",")
            || stNumber.contains("+")
            || stNumber.contains("-")
            || stNumber.contains("*")
            || stNumber.contains("/"))
}

fun getCurrentSqlTime(): String {
    val calendar = Calendar.getInstance()
    val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    return currentDate.format(calendar.time)
}

fun getTwoDigitDecimal(stNumber: Any?): String {
    if (stNumber == null) return "0.0"
    val number = try {
        stNumber.toString().toDouble()
    } catch (e: Exception) {
        0.0
    }
    return String.format("%.2f", number)
}

fun Activity.updateLanguage(languageCode: String) {

//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//        // the method is used to set the language at runtime
//        // the method is used update the language of application by creating
//        // object of inbuilt Locale class and passing language argument to it
//        val locale = Locale(languageCode)
//        val configuration = resources.configuration
//
//        Locale.setDefault(locale)
//        configuration.setLocale(locale)
//        configuration.setLayoutDirection(locale)
//        createConfigurationActivity(configuration)
//    } else
//    {
//        // for devices having lower version of android os
    val locale = Locale(languageCode)
    val configuration = resources.configuration

    Locale.setDefault(locale)
    configuration.setLocale(locale)
    configuration.setLayoutDirection(locale)
    resources.updateConfiguration(configuration, resources.displayMetrics)
    //}
}


fun getDDMMYYTimeDay(inputTime: Any?): String {

    if (inputTime == null) return ""

    val filteredTime = inputTime.toString().replace("T", "-").substring(0, 16)


    return getDayHhMmAm(filteredTime) + "\n" + getDdMmYYYY(filteredTime)
    // return getTodayYesterday(filteredTime) + " " + getDayHhMmAm(filteredTime) + "\n" + getDdMmYYYY( filteredTime )
}

fun getDdMmYYYY(input: String?): String {

    if (input == null) return ""

    val filteredTime = input.toString().replace("T", "-").substring(0, 16)

    val dbFormat = SimpleDateFormat("yyyy-MM-dd-HH:mm", Locale.US)
    val newFormat = SimpleDateFormat("dd/MMM/yy", Locale.US)
    var strDateTime = filteredTime
    var date: Date? = null
    try {
        date = dbFormat.parse(filteredTime)
        strDateTime = newFormat.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return strDateTime
}

fun getDayHhMmAm(input: String?): String {

    if (input == null) return ""

    val filteredTime = input.toString().replace("T", "-").substring(0, 16)

    val dbFormat = SimpleDateFormat("yyyy-MM-dd-HH:mm", Locale.US)
    val newFormat = SimpleDateFormat("hh:mm a", Locale.US)
    var strDateTime = ""
    var date: Date? = null
    try {
        date = dbFormat.parse(filteredTime)
        strDateTime = newFormat.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return strDateTime
}

fun getSqlToDDMMYYHHSS(inputDate: String?): String? {
    val dbFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    val newFormat = SimpleDateFormat("dd/MMM/yy hh:mm a")
    var strDateTime = inputDate
    var date: Date? = null
    try {
        date = dbFormat.parse(inputDate)
        strDateTime = newFormat.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return strDateTime
}

fun getSqlToStatusTime(inputDate: String?): String? {
    val dbFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    val newFormatHour = SimpleDateFormat("hh:mm a", Locale.US)
    val newFormat = SimpleDateFormat("dd/MMM/yy", Locale.US)
    var strDateTime = inputDate
    var date: Date? = null
    try {
        date = dbFormat.parse(inputDate)
        strDateTime = newFormatHour.format(date) + "\n" + newFormat.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return strDateTime
}

fun getTodayYesterday(input: String?): String {

    if (input == null) return ""

    val filteredTime = input.toString().replace("T", "-").substring(0, 16)

    val inputDateFormat = SimpleDateFormat("yyyy-MM-dd-HH:mm", Locale.US)
    val newFormat = SimpleDateFormat("E", Locale.US)

    var strDateTime = ""
    var date: Date? = null
    try {
        date = inputDateFormat.parse(filteredTime)
        strDateTime = newFormat.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    if (date != null) {
        val currentTime: Calendar = Calendar.getInstance()
        val dbTime = Calendar.getInstance()
        dbTime.time = date

        if (currentTime.get(Calendar.MONTH) == dbTime.get(Calendar.MONTH)
            && currentTime.get(Calendar.DATE) == dbTime.get(Calendar.DATE)
        ) {
            strDateTime = "Today"
        } else if (currentTime.get(Calendar.MONTH) == dbTime.get(Calendar.MONTH)
            && currentTime.get(Calendar.DATE) - dbTime.get(Calendar.DATE) == 1
        ) {
            strDateTime = "Y.Day"
        }
    }

    return strDateTime
}

fun Activity.setToolbarTitle(title: String) {
    try {
        (this as AppCompatActivity).supportActionBar!!.title = title
    } catch (e: Exception) {
    }
}

fun Activity.showHideToolbar(isNeedHide: Boolean) {
    try {
        if (isNeedHide)
            (this as AppCompatActivity).supportActionBar!!.hide()
        else
            (this as AppCompatActivity).supportActionBar!!.show()
    } catch (e: Exception) {
    }
}

fun encodeData(input: String): String {
    try {
        val data = input.toByteArray(charset("UTF-8"))
        return Base64.encodeToString(data, Base64.DEFAULT)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return input
}

fun decodeData(input: String): String {
    try {
        val data: ByteArray = Base64.decode(input, Base64.DEFAULT)
        return String(data, charset("UTF-8"))
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return input
}

fun getGsonString(any: Any): String {
    return Gson().toJson(any)
}

fun getEncodedGsonString(any: Any): String {
    return encodeData(Gson().toJson(any))
}

fun logPrint(any: Any?) {
    if (BuildConfig.DEBUG)
        Log.d("010101", "\n\nLogData = " + Gson().toJson(any) + "\n\n\n")
}

fun isLoginSessionExpired(lastLoginTime: String?): Boolean {
    if (lastLoginTime == null || lastLoginTime == "")
        return false

    val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    val currentDate = Date()

    val differenceInTime = try {
        currentDate.time - df.parse(lastLoginTime).time
    } catch (e: java.lang.Exception) {
        0
    }
    val seconds = differenceInTime / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return minutes !in 0..30
}

fun Context.isLoginValid(apiToken: String?): Boolean {
    return !(apiToken == null || apiToken == "")
}

fun strikeThroughText(textView: TextView) {
    textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

fun gAppVersion(): String {
    return try {
        BuildConfig.VERSION_CODE.toString()
    } catch (e: Exception) {
        ""
    }
}
