package com.movingroot.storyfromnowhere.data.model

@kotlinx.serialization.Serializable
data class User(
    val uniqueId: String,
    val nickName: String,
    val password: String,
    val createdAt: String
)
