package com.movingroot.storyfromnowhere

import android.app.Application
import com.movingroot.storyfromnowhere.util.DataStoreModule
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.BuildConfig
import com.orhanobut.logger.Logger

class BaseApplication : Application() {
    private val dataStore: DataStoreModule by lazy {
        DataStoreModule(this)
    }

    override fun onCreate() {
        super.onCreate()
        initApplication()
    }

    private fun initApplication() {
        baseApplication = this
        initLogger()
    }

    private fun initLogger() {
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    fun getDataStoreModule(): DataStoreModule = dataStore

    companion object {
        private lateinit var baseApplication: BaseApplication
        fun getInstance() = baseApplication
    }
}
