package com.mominulcse7.bdonor.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.mominulcse7.bdonor.apiResponse.RpPostModelList
import com.mominulcse7.bdonor.network.ApiService
import com.mominulcse7.bdonor.pagination.PostSource
import com.mominulcse7.bdonor.utils.ConstantKeys.INITIAL_LOAD_SIZE
import com.mominulcse7.bdonor.utils.ConstantKeys.NETWORK_PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val ctx: Application,
    private val apiService: ApiService,
    private val postSource: PostSource
) : BaseViewModel() {
    val liveRpPostModelList = MutableLiveData<RpPostModelList>()

    val postList = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = INITIAL_LOAD_SIZE
        ),
        pagingSourceFactory = { postSource },
        initialKey = 1
    ).liveData.cachedIn(viewModelScope)
}