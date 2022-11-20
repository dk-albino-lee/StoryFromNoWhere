package com.movingroot.storyfromnowhere.data.network

import android.util.Log
import com.movingroot.storyfromnowhere.util.Constants
import com.orhanobut.logger.BuildConfig
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class KtorClient(private val tag: String) {
    private val client = HttpClient(OkHttp) {
        expectSuccess = true
        defaultRequest {
            url(Constants.BASE_URL)
        }
        engine {
            this.setOkHttpConfig()
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    fun instance(): HttpClient = client

    private fun OkHttpConfig.setOkHttpConfig() {
        config {
            TIMEOUT.let {
                connectTimeout(it, TimeUnit.SECONDS)
                readTimeout(it, TimeUnit.SECONDS)
                writeTimeout(it, TimeUnit.SECONDS)
            }
        }

        if (BuildConfig.DEBUG)
            addInterceptor(makeInterceptor())
    }

    private fun makeInterceptor(): Interceptor {
        return HttpLoggingInterceptor(HttpLogger(tag))
            .setLevel(HttpLoggingInterceptor.Level.BASIC)
    }

    class HttpLogger(val tag: String): HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.d("Call from $tag", message)
        }
    }

    companion object {
        private const val TIMEOUT = 15000L
    }
}
