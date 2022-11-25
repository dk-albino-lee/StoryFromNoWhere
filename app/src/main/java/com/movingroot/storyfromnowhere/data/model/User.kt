package com.movingroot.storyfromnowhere.data.model

@kotlinx.serialization.Serializable
data class User(
    val uniqueId: String,
    val nickname: String,
    val password: String,
    val createdAt: String
)
