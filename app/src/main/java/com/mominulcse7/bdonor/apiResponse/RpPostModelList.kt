package com.mominulcse7.bdonor.apiResponse

import com.mominulcse7.bdonor.model.PostModel
import com.mominulcse7.bdonor.utils.ConstantKeys.FAILED
import com.mominulcse7.bdonor.utils.ConstantKeys.MESSAGE

class RpPostModelList {
    var status: String = FAILED
    var message: String = MESSAGE
    var totalItem: Long = 0L
    var list: List<PostModel> = ArrayList()
}