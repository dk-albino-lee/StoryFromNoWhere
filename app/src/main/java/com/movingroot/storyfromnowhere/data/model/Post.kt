package com.movingroot.storyfromnowhere.data.model

import java.util.Date

data class Post(
    val title: String,
    val content: String,
    val postId: Int,
    val uniqueId: String,
    val createdAt: Date,
    val editedAt: Date?
)
