package com.movingroot.storyfromnowhere.ui.sign.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.movingroot.storyfromnowhere.data.network.FirebaseObject
import com.movingroot.storyfromnowhere.ui.base.BaseViewModel
import com.movingroot.storyfromnowhere.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel : BaseViewModel() {
    init {
        TAG = this::class.java.simpleName
    }

    private val _toSignIn = MutableLiveData(false)
    val toSignIn: LiveData<Boolean> get() = _toSignIn
    val _inputNickname = MutableLiveData("")
    val inputNickname: LiveData<String> get() = _inputNickname
    val _inputPassword = MutableLiveData("")
    val inputPassword: LiveData<String> get() = _inputPassword
    val isSignUpEnabled = MediatorLiveData<Boolean>()
        .apply {
            addSource(inputNickname) {
                this.value = isReadyToMoveOn()
            }
            addSource(inputPassword) {
                this.value = isReadyToMoveOn()
            }
        }

    fun startSignUp() {
        job = viewModelScope.launch(Dispatchers.IO) {
            processSignUp()
        }
    }

    private suspend fun processSignUp() {
        val ret = FirebaseObject.signUp(inputNickname.value!!, inputPassword.value!!)
        if (ret.code == Constants.NetworkConst.IS_FAILED) {
            // TODO : Failed Dialog
            return
        }
        withContext(Dispatchers.Main) {
            toSignIn()
        }
    }

    fun toSignIn() {
        _toSignIn.value = true
    }

    private fun isReadyToMoveOn(): Boolean {
        if (inputNickname.value!!.length >= 2 && inputPassword.value!!.length >= 6)
            return true
        return false
    }

}
