package com.movingroot.storyfromnowhere.util

import com.movingroot.storyfromnowhere.data.model.User

object Constants {

    object NetworkConst {
        const val BASE_URL = ""
        const val GET_POSTS = "posts"
        const val IS_SUCCESS = "succeeded"
        const val IS_FAILED = "failed"
    }

    object DataStoreConst {
        const val HAS_BEEN_LOADED = "hasBeenLoaded"
    }

    var signedInUser: User? = null
}
