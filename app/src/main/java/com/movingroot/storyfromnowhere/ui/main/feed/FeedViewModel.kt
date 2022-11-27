package com.movingroot.storyfromnowhere.ui.main.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.movingroot.storyfromnowhere.data.model.ModelResponse
import com.movingroot.storyfromnowhere.data.model.Post
import com.movingroot.storyfromnowhere.data.network.FirebaseObject
import com.movingroot.storyfromnowhere.ui.base.BaseViewModel
import com.movingroot.storyfromnowhere.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedViewModel : BaseViewModel() {
    init {
        TAG = this::class.java.simpleName
    }

    private val _posts = MutableLiveData(listOf<Post>())
    val posts: LiveData<List<Post>> get() = _posts

    fun getPosts() {
       job = viewModelScope.launch(Dispatchers.IO) {
           processGettingPosts()
       }
    }

    private suspend fun processGettingPosts() {
        val ret = FirebaseObject.getPosts()
        if (ret.code == Constants.NetworkConst.IS_FAILED) {
            // TODO : Failed Dialog
            return
        }
        withContext(Dispatchers.Main) {
            _posts.value = ret.data
        }
    }

    fun deletePost() {

    }
}
