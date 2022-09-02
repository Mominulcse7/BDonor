package com.mominulcse7.bdonor.utils

import com.mominulcse7.bdonor.BuildConfig

object ConstantKeys {

    const val REQUEST_TIMEOUT = 60L
    const val INITIAL_LOAD_SIZE = 2
    const val NETWORK_PAGE_SIZE = 30

    const val FRAGMENT = "FRAGMENT"
    const val NOTIFY_FRAGMENT = "NOTIFY_FRAGMENT"
    const val POST_FRAGMENT = "POST_FRAGMENT"

    const val CLEAR_ERROR_TIME: Long = 5000L

    const val LOGIN_S1 = "https://media.istockphoto.com/vectors/donate-blood-concept-with-blood-bag-and-heart-blood-donation-vector-vector-id1033906526?k=20&m=1033906526&s=612x612&w=0&h=I-uDrvlPJBqRquTKDAc5x1JCUhXXuduoRMHi92RmuVI="
    const val LOGIN_S2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLTjduBCa-HHLKx0oDAhgQPmTsl8RsFAwLy6Z3gBTj&s"
    const val LOGIN_S3 = "https://image.shutterstock.com/image-vector/blood-donation-vector-illustration-red-260nw-737116375.jpg"


    const val BASE_LINK = BuildConfig.BASE_URL
    const val API_VERSION = BuildConfig.API_VERSION

    const val IMAGE_URL = BuildConfig.IMAGE_URL
    const val URL_PRODUCT = "$IMAGE_URL/product/"
    const val URL_USER = "$IMAGE_URL/customer/"
    const val URL_NOTIFY = "$IMAGE_URL/notification/"
    const val URL_PAYMENT = "$IMAGE_URL/payment_gateway/"

    const val APP_SHARE_LINK =
        "I'm using this BDonor app. https://play.google.com/store/apps/details?id=com.mominulcse7.bdonor&hl=en"

    const val PLAY_STORE_LINK = "https://play.google.com/store/apps/details?id=com.mominulcse7.bdonor&hl=en"
    const val PRIVACY_POLICY = "$BASE_LINK/bdonor/privacy_policy.html"

    const val APP_FCM_TOPIC = "BDonorUser"
    const val INTENT_DATA = "INTENT_DATA"

    const val MESSAGE = "Failed to get data."
    const val FAILED = "Failed"
    const val SUCCESS = "Success"
    const val SESSION_EXPIRED = "SessionExpired"
    const val USER_EXIST = "Exists"
    const val PHONE_EXIST = "PhoneExists"
    const val USER_NOT_EXIST = "NotExists"
    const val VERIFICATION_PENDING = "VerificationPending"
    const val NO_RESPONSE = "No response!"

}