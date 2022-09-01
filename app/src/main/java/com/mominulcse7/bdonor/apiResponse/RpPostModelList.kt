package com.mominulcse7.bdonor.apiResponse

import com.mominulcse7.bdonor.model.PostModel
import com.mominulcse7.bdonor.utils.ConstantKeys.FAILED
import com.mominulcse7.bdonor.utils.ConstantKeys.FAILED_MSG

class RpPostModelList {
    var status: String = FAILED
    var message: String = FAILED_MSG
    var totalItem: Long = 0L
    var list: List<PostModel> = ArrayList()
}