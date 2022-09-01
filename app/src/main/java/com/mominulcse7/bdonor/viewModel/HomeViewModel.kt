package com.mominulcse7.bdonor.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mominulcse7.bdonor.apiResponse.RpPostModelList
import com.mominulcse7.bdonor.model.ApiDataModel
import com.mominulcse7.bdonor.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val ctx: Application,
    private val apiService: ApiService
) : BaseViewModel() {
    val liveRpPostModelList = MutableLiveData<RpPostModelList>()

    fun getPostList(apiCall: ApiDataModel) {
        getResponse(apiService::getPostList, apiCall) {
            liveRpPostModelList.value = it
        }
    }
}