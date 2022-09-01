package com.mominulcse7.bdonor.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mominulcse7.bdonor.BuildConfig
import com.mominulcse7.bdonor.network.NetworkError
import com.mominulcse7.bdonor.utils.logPrint
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    private val networkError = MutableLiveData<String>()
    private lateinit var application: Application

    //multipleParameter
    fun <T : Any, U : Any> getResponse(
        requesterMethod: suspend (T) -> U,
        body: T,
        onResponseMethod: (response: U) -> Unit
    ) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                onResponseMethod(requesterMethod(body))
            } catch (errorMsg: Throwable) {
                val errorMessage = NetworkError.getServerResponseMessage(errorMsg, application)
                networkError.value = errorMessage
                Toast.makeText(application, errorMessage, Toast.LENGTH_LONG).show()
                printApiResponse(errorMsg.localizedMessage)
            }
            isLoading.value = false
        }
    }

    //singleParameter
    fun <U : Any> getResponse(
        requesterMethod: suspend () -> U,
        onResponseMethod: (response: U) -> Unit
    ) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                onResponseMethod(requesterMethod())
            } catch (errorMsg: Throwable) {
                networkError.value = NetworkError.getServerResponseMessage(errorMsg, application)
                printApiResponse(errorMsg.localizedMessage)
            }
            isLoading.value = false
        }
    }

    private fun printApiResponse(errorMsg: String?) {
        if (BuildConfig.DEBUG)
            logPrint(errorMsg)
    }

}