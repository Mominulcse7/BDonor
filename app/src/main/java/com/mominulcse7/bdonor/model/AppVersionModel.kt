package com.mominulcse7.bdonor.model

class AppVersionModel: ApiDataModel() {
    var appName: String? = "BDonor"
    var versionCode: Int? = 0
    var versionName: String? = ""
    var isUpdateMandatory: Int? = 0
    var isUnderMaintenance: Int? = 0
    var title: String? = "Update available."
    var maintenanceMsg: String? = "Please update your app."
    var createdAt: String? = ""
}