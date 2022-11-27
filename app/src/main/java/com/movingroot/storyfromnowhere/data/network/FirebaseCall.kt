@file:Suppress("UNCHECKED_CAST")

package com.movingroot.storyfromnowhere.data.network

import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.HttpsCallableReference
import com.google.firebase.functions.HttpsCallableResult
import com.movingroot.storyfromnowhere.data.model.ModelResponse
import com.movingroot.storyfromnowhere.data.model.Post
import com.movingroot.storyfromnowhere.data.model.User
import com.movingroot.storyfromnowhere.util.Constants
import java.util.concurrent.TimeUnit

/**
 * FirebaseObject 에서 호출할 함수들의 기본 설정을 저장한다.
 * Exception 등 unexpected results 에 대한 handling 은
 * FirebaseObject 에서 동일한 이름의 함수를 통해 실행한다.
 */
class FirebaseCall(private val functions: FirebaseFunctions) {

    fun signIn(nickname: String, password: String): Task<ModelResponse<User>> {
        val data = hashMapOf(
            "nickname" to nickname,
            "password" to password
        )

        return makeCall("signIn", data)
            .continueWith { task ->
                task.result?.data as ModelResponse<User>
            }
    }

    fun signUp(nickname: String, password: String): Task<ModelResponse<User>> {
        val data = hashMapOf(
            "nickname" to nickname,
            "password" to password
        )

        return makeCall("signUp", data)
            .continueWith { task ->
                task.result?.data as ModelResponse<User>
            }
    }

    fun getPosts(): Task<ModelResponse<List<Post>>> {
        val data = hashMapOf(
            "uniqueId" to Constants.signedInUser?.uniqueId
        )

        return makeCall("posts", data)
            .continueWith { task ->
                task.result?.data as ModelResponse<List<Post>>
            }
    }

    fun deletePost(postId: String): Task<ModelResponse<Boolean>> {
        val data = hashMapOf(
            "uniqueId" to Constants.signedInUser?.uniqueId
        )

        return makeCall("delete/post", data)
            .continueWith { task ->
                task.result?.data as ModelResponse<Boolean>
            }
    }

    private fun <T> makeCall(name: String, data: HashMap<String, T>): Task<HttpsCallableResult> {
        return makeCallable(name)
            .call(data)
    }

    private fun makeCallable(name: String): HttpsCallableReference {
        return functions
            .getHttpsCallable(name)
            .apply {
                setTimeout(Constants.NetworkConst.TIME_OUT, TimeUnit.SECONDS)
            }
    }

    companion object {
        private lateinit var call: FirebaseCall
        fun instance(functions: FirebaseFunctions): FirebaseCall {
            if (!this::call.isInitialized)
                call = FirebaseCall(functions)
            return call
        }
    }
}
