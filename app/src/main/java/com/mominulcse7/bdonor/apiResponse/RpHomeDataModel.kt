package com.mominulcse7.bdonor.apiResponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mominulcse7.bdonor.model.HomeDataModel
import com.mominulcse7.bdonor.utils.ConstantKeys.FAILED
import com.mominulcse7.bdonor.utils.ConstantKeys.MESSAGE

class RpHomeDataModel {

    @SerializedName("status")
    @Expose
    var status: String = FAILED

    @SerializedName("message")
    @Expose
    var message: String = MESSAGE

    @SerializedName("data")
    @Expose
    var data: HomeDataModel? = null

}