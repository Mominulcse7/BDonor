package com.mominulcse7.bdonor.network

import com.mominulcse7.bdonor.apiResponse.RpPostModelList
import com.mominulcse7.bdonor.apiResponse.RpUserModel
import com.mominulcse7.bdonor.model.ApiDataModel
import com.mominulcse7.bdonor.model.UserModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/user/register")
    suspend fun userRegister(data: UserModel): RpUserModel

    @GET("user/post_list")
    suspend fun getPostList(@Query("page") page: Int): RpPostModelList
}