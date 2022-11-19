package com.movingroot.storyfromnowhere.ui.base

import androidx.lifecycle.ViewModel
import com.movingroot.storyfromnowhere.data.network.KtorClient

open class BaseViewModel : ViewModel() {
    protected var TAG = ""
    protected val client = KtorClient(TAG)
}
