package com.movingroot.storyfromnowhere.data.network

import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.movingroot.storyfromnowhere.data.model.ModelResponse
import com.movingroot.storyfromnowhere.data.model.Post
import com.movingroot.storyfromnowhere.data.model.User

object FirebaseObject {
    private val functions: FirebaseFunctions by lazy {
        Firebase.functions
    }
    private val callInstance: FirebaseCall by lazy {
        FirebaseCall.instance(functions)
    }

    fun signIn(nickname: String, password: String): ModelResponse<User> {
        return callInstance
            .signIn(nickname, password)
            .addOnCompleteListener { task ->
                handleFirebaseException(task)
            }
            .result
    }

    fun signUp(nickname: String, password: String): ModelResponse<User> {
        return callInstance
            .signUp(nickname, password)
            .addOnCompleteListener { task ->
                handleFirebaseException(task)
            }
            .result
    }

    fun getPosts(): ModelResponse<List<Post>> {
        return callInstance
            .getPosts()
            .addOnCompleteListener { task ->
                handleFirebaseException(task)
            }
            .result
    }

    private fun <T> handleFirebaseException(task: Task<ModelResponse<T>>) {
        if (!task.isSuccessful) {
            task.exception
                .run {
                    if (this is FirebaseFunctionsException) {
                        val code = this.code
                        val details = this.details
                    }
                }
        }
    }
}
