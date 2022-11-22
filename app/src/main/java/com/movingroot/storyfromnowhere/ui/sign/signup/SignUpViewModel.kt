package com.movingroot.storyfromnowhere.ui.sign.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.movingroot.storyfromnowhere.ui.base.BaseViewModel

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

    fun requestSigningUp() {
        // TODO : 회원가입 호출

        toSignIn()
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
