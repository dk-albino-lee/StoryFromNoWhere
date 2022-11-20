package com.movingroot.storyfromnowhere.data.model

@kotlinx.serialization.Serializable
data class Label(
    val uniqueId: String,
    val labelId: String,
    val postId: String,
    val label: String
)
