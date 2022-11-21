package com.movingroot.storyfromnowhere.ui.main.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movingroot.storyfromnowhere.data.model.ModelResponse
import com.movingroot.storyfromnowhere.data.model.Post
import com.movingroot.storyfromnowhere.ui.base.BaseViewModel
import com.movingroot.storyfromnowhere.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedViewModel : BaseViewModel() {
    init {
        TAG = this::class.java.simpleName
    }

    private val _posts = MutableLiveData(listOf<Post>())
    val posts: LiveData<List<Post>> get() = _posts

    fun getPosts() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val result: ModelResponse<List<Post>> = requestGet(Constants.NetworkConst.GET_POSTS)
            _posts.postValue(result.data)
        }
    }

    fun deletePost() {

    }
}
