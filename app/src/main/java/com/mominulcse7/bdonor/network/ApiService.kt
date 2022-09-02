package com.mominulcse7.bdonor.network

import com.mominulcse7.bdonor.apiResponse.RpHomeDataModel
import com.mominulcse7.bdonor.apiResponse.RpPostModelList
import com.mominulcse7.bdonor.apiResponse.RpUserModel
import com.mominulcse7.bdonor.model.ApiDataModel
import com.mominulcse7.bdonor.model.UserModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("user/get_home_data")
    suspend fun getHomeData(): RpHomeDataModel

    @GET("/user/loginWithGmail")
    suspend fun userLoginWithGmail(data: UserModel): RpUserModel

    @GET("/user/logout")
    suspend fun userLogout(): RpUserModel

    @GET("user/post_list_public")
    suspend fun getPostList(@Query("page") page: Int): RpPostModelList
}