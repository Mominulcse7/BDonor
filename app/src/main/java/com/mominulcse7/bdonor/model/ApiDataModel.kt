package com.mominulcse7.bdonor.model

open class ApiDataModel {
    var userId: Long? = 0L
    var itemId: Long? = 0L
    var status: String? = ""
    var message: String? = ""
    var filterKey: String? = ""
    var currentPage: Long? = 0
    var itemPerPage: Long? = 50
    var androidToken: String? = ""
    var cAppVersion: String? = ""
    var data: Any? = null
}