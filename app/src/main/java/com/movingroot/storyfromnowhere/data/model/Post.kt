package com.movingroot.storyfromnowhere.data.model

@kotlinx.serialization.Serializable
data class Post(
    val title: String,
    val content: String,
    val postId: Int,
    val labels: List<Label> = listOf(),
    val uniqueId: String,
    val createdAt: String,
    val editedAt: String?
)
