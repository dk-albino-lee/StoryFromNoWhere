@file:Suppress("UNCHECKED_CAST")

package com.movingroot.storyfromnowhere.data.network

import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.movingroot.storyfromnowhere.data.model.ModelResponse
import com.movingroot.storyfromnowhere.data.model.Post
import com.movingroot.storyfromnowhere.data.model.User

/**
 * FirebaseObject 에서 호출할 함수들의 기본 설정을 저장한다.
 * Exception 등 unexpected results 에 대한 handling 은
 * FirebaseObject 에서 동일한 이름의 함수를 통해 실행한다.
 */
class FirebaseCall(private val functions: FirebaseFunctions) {

    // Sample function from https://firebase.google.com/docs/functions/callable
    fun addMessage(message: String): Task<String> {
        // Create the arguments to the callable function.
        val data = hashMapOf(
            "message" to message,
            "push" to true
        )

        return functions
            .getHttpsCallable("addMessage")
            .call(data)
            .continueWith { task ->
                // This continuation runs on either success or failure,
                // but if the task has failed, then result will throw an Exception
                task.result?.data as String
            }
    }

    fun signIn(nickname: String, password: String): Task<ModelResponse<User>> {
        val data = hashMapOf(
            "nickname" to nickname,
            "password" to password
        )

        return functions
            .getHttpsCallable("signIn")
            .call(data)
            .continueWith { task ->
                task.result?.data as ModelResponse<User>
            }
    }

    fun signUp(nickname: String, password: String): Task<ModelResponse<User>> {
        val data = hashMapOf(
            "nickname" to nickname,
            "password" to password
        )

        return functions
            .getHttpsCallable("signUp")
            .call(data)
            .continueWith { task ->
                task.result?.data as ModelResponse<User>
            }
    }

    fun getPosts(uniqueId: String): Task<ModelResponse<List<Post>>> {
        val data = hashMapOf(
            "uniqueId" to uniqueId
        )

        return functions
            .getHttpsCallable("posts")
            .call(data)
            .continueWith { task ->
                task.result?.data as ModelResponse<List<Post>>
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
