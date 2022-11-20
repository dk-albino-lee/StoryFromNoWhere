package com.movingroot.storyfromnowhere.data.network

import com.movingroot.storyfromnowhere.data.model.Post

interface ApiService {
    suspend fun getPosts(uniqueId: String): List<Post>

}
