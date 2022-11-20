package com.movingroot.storyfromnowhere.data.model

@kotlinx.serialization.Serializable
data class ModelResponse<T>(
    val code: String,
    val data: T
)
