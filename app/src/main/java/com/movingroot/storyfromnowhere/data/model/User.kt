package com.movingroot.storyfromnowhere.data.model

import java.util.Date

data class User(
    val uniqueId: String,
    val nickName: String,
    val password: String,
    val createdAt: Date
)
