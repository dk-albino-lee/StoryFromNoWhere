package com.movingroot.storyfromnowhere.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.movingroot.storyfromnowhere.data.model.ModelResponse
import com.movingroot.storyfromnowhere.data.network.KtorClient
import io.ktor.client.call.*
import io.ktor.client.network.sockets.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.network.sockets.SocketTimeoutException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class BaseViewModel : ViewModel() {
    protected var TAG = ""
    private val client = KtorClient(TAG).instance()
    private val _toShowNetworkErrorDialog = MutableLiveData(false)
    val toShowNetworkErrorDialog: LiveData<Boolean> get() = _toShowNetworkErrorDialog

    suspend fun <T> requestPost(_url: String, data: Any): ModelResponse<T> {
        val request = try {
            client.post(_url) {
                contentType(ContentType.Application.Json)
                setBody(data)
            }
        } catch (e: ClientRequestException) {
            withContext(Dispatchers.Main) {
                _toShowNetworkErrorDialog.value = true
            }
            throw e
        } catch (e: ServerResponseException) {
            withContext(Dispatchers.Main) {
                _toShowNetworkErrorDialog.value = true
            }
            throw e
        } catch (e: HttpRequestTimeoutException) {
            withContext(Dispatchers.Main) {
                _toShowNetworkErrorDialog.value = true
            }
            throw e
        }
         catch (e: ConnectTimeoutException) {
             withContext(Dispatchers.Main) {
                 _toShowNetworkErrorDialog.value = true
             }
             throw e
         } catch (e: SocketTimeoutException) {
            withContext(Dispatchers.Main) {
                _toShowNetworkErrorDialog.value = true
            }
            throw e
         }
        return request.body()
    }

    suspend fun requestGet(url: String): HttpResponse {
        val request =  try {
            client.get(url) {
                contentType(ContentType.Application.Json)
            }
        } catch(e: ClientRequestException) {
            withContext(Dispatchers.Main) {
                _toShowNetworkErrorDialog.value = true
            }
            throw e
        } catch (e: ServerResponseException) {
            withContext(Dispatchers.Main) {
                _toShowNetworkErrorDialog.value = true
            }
            throw e
        } catch (e: HttpRequestTimeoutException) {
            withContext(Dispatchers.Main) {
                _toShowNetworkErrorDialog.value = true
            }
            throw e
        }
        catch (e: ConnectTimeoutException) {
            withContext(Dispatchers.Main) {
                _toShowNetworkErrorDialog.value = true
            }
            throw e
        } catch (e: SocketTimeoutException) {
            withContext(Dispatchers.Main) {
                _toShowNetworkErrorDialog.value = true
            }
            throw e
        }
        return request.body()
    }


    suspend fun requestPut(url: String, data: Any): HttpResponse {
        val request = try {
            client.put(url) {
                contentType(ContentType.Application.Json)
                setBody(data)
            }
        } catch(e: ClientRequestException) {
            withContext(Dispatchers.Main) {
                _toShowNetworkErrorDialog.value = true
            }
            throw e
        } catch (e: ServerResponseException) {
            withContext(Dispatchers.Main) {
                _toShowNetworkErrorDialog.value = true
            }
            throw e
        } catch (e: HttpRequestTimeoutException) {
            withContext(Dispatchers.Main) {
                _toShowNetworkErrorDialog.value = true
            }
            throw e
        }
        catch (e: ConnectTimeoutException) {
            withContext(Dispatchers.Main) {
                _toShowNetworkErrorDialog.value = true
            }
            throw e
        } catch (e: SocketTimeoutException) {
            withContext(Dispatchers.Main) {
                _toShowNetworkErrorDialog.value = true
            }
            throw e
        }
        return request.body()
    }

    suspend fun requestDelete(url: String, data: Any): HttpResponse {
        val request = try {
            client.delete(url) {
                contentType(ContentType.Application.Json)
                setBody(data)
            }
        }  catch(e: ClientRequestException) {
            withContext(Dispatchers.Main) {
                _toShowNetworkErrorDialog.value = true
            }
            throw e
        } catch (e: ServerResponseException) {
            withContext(Dispatchers.Main) {
                _toShowNetworkErrorDialog.value = true
            }
            throw e
        } catch (e: HttpRequestTimeoutException) {
            withContext(Dispatchers.Main) {
                _toShowNetworkErrorDialog.value = true
            }
            throw e
        }
        catch (e: ConnectTimeoutException) {
            withContext(Dispatchers.Main) {
                _toShowNetworkErrorDialog.value = true
            }
            throw e
        } catch (e: SocketTimeoutException) {
            withContext(Dispatchers.Main) {
                _toShowNetworkErrorDialog.value = true
            }
            throw e
        }
        return request.body()
    }
}
