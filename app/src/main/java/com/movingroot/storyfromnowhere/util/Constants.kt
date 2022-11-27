package com.movingroot.storyfromnowhere.util

import com.movingroot.storyfromnowhere.data.model.User

object Constants {

    object NetworkConst {
        const val BASE_URL = ""
        const val GET_POSTS = "posts"
        const val IS_SUCCESS = "succeeded"
        const val IS_FAILED = "failed"
        const val TIME_OUT = 10L
    }

    object DataStoreConst {
        const val HAS_BEEN_LOADED = "hasBeenLoaded"
    }

    object BundleConst {
        const val DATA_KEY = "data_key"
    }

    var signedInUser: User? = null
}
