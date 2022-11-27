package com.movingroot.storyfromnowhere.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.movingroot.storyfromnowhere.data.model.ModelResponse
import com.movingroot.storyfromnowhere.data.network.KtorClient
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.network.sockets.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.network.sockets.SocketTimeoutException
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel() {
    protected var TAG = ""
        set(value) {
            if (value.isNotEmpty())
                client = KtorClient(value).instance()
            field = value
        }
    private lateinit var client: HttpClient
    protected var job: Job? = null

    private val _toShowNetworkErrorDialog = MutableLiveData(false)
    val toShowNetworkErrorDialog: LiveData<Boolean> get() = _toShowNetworkErrorDialog

    suspend fun <T> requestPost(_url: String, data: Any): ModelResponse<T> {
        val request = requestApi {
            client.post(_url) {
                contentType(ContentType.Application.Json)
                setBody(data)
            }
        }
        return request.body()
    }

    suspend fun <T> requestGet(url: String): ModelResponse<T> {
        val request = requestApi {
            client.get(url) {
                contentType(ContentType.Application.Json)
            }
        }
        return request.body()
    }

    suspend fun <T> requestPut(url: String, data: Any): ModelResponse<T> {
        val request = requestApi {
            client.put(url) {
                contentType(ContentType.Application.Json)
                setBody(data)
            }
        }
        return request.body()
    }

    suspend fun <T> requestDelete(url: String, data: Any): ModelResponse<T> {
        val request = requestApi {
            client.delete(url) {
                contentType(ContentType.Application.Json)
                setBody(data)
            }
        }

        return request.body()
    }

    private suspend fun requestApi(call: suspend () -> HttpResponse): HttpResponse {
        return try {
            call.invoke()
        } catch (e: Exception) {
            when (e) {
                is ClientRequestException, is ServerResponseException, is HttpRequestTimeoutException,
                is ConnectTimeoutException, is SocketTimeoutException -> respondToNetworkError(e)
                else -> throw e
            }
        }
    }

    private suspend fun respondToNetworkError(e: Throwable): Nothing =
        withContext(Dispatchers.Main) {
            job?.cancel()
            _toShowNetworkErrorDialog.value = true
            throw e
        }

    override fun onCleared() {
        job = null
        if (this::client.isInitialized)
            client.close()
        super.onCleared()
    }
}
