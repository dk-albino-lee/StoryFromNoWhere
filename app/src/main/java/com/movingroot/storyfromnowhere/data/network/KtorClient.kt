package com.movingroot.storyfromnowhere.data.network

import android.util.Log
import com.orhanobut.logger.BuildConfig
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class KtorClient(private val tag: String) {
    private val client = HttpClient(OkHttp) {
        expectSuccess = true
        engine {
            this.setOkHttpConfig()
        }
    }

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
        fun instance(tag: String) = KtorClient(tag)
        private const val TIMEOUT = 15L
    }
}
