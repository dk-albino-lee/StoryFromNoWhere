package com.movingroot.storyfromnowhere.ui.sign.sighin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.movingroot.storyfromnowhere.data.model.Dummies
import com.movingroot.storyfromnowhere.data.model.ModelResponse
import com.movingroot.storyfromnowhere.data.model.User
import com.movingroot.storyfromnowhere.ui.base.BaseViewModel
import com.movingroot.storyfromnowhere.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInViewModel : BaseViewModel() {
    val _nickNameInput = MutableLiveData("")
    val nickNameInput: LiveData<String> get() = _nickNameInput
    val _passwordInput = MutableLiveData("")
    val passwordInput: LiveData<String> get() = _passwordInput
    val isSignInEnabled = MediatorLiveData<Boolean>()
        .apply {
            addSource(nickNameInput) {
                this.value = isReadyToMoveOn()
            }
            addSource(passwordInput) {
                this.value = isReadyToMoveOn()
            }
        }
    private val _signInFlag = MutableLiveData(false)
    val signInFlag: LiveData<Boolean> get() = _signInFlag
    private val _signUpFlag = MutableLiveData(false)
    val signUpFlag: LiveData<Boolean> get() = _signUpFlag
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage
    val _autoSignIn = MutableLiveData<Boolean>()
    val autoSignIn: LiveData<Boolean> get() = _autoSignIn
    val _rememberNickname = MutableLiveData<Boolean>()
    val rememberNickname: LiveData<Boolean> get() = _rememberNickname

    init {
        TAG = this::class.java.simpleName
    }

    private fun isReadyToMoveOn(): Boolean {
        if (nickNameInput.value!!.length < 2)
            return false
        if (passwordInput.value!!.length < 6)
            return false
        return true
    }

    fun retrieveSavedCheckBoxValue(isAutoSignIn: Boolean, isRememberPassword: Boolean) {
        _autoSignIn.value = isAutoSignIn
        _rememberNickname.value = isRememberPassword
    }

    fun retrieveNicknameIfRemembered(savedNickname: String) {
        if (rememberNickname.value!!)
            _nickNameInput.value = savedNickname
    }

    fun initializeToast() {
        _toastMessage.value = ""
    }

    fun startSignIn() {
        job = viewModelScope.launch(Dispatchers.IO) {
            val process = async { requestSignIn() }
            val result = process.await()
            if (result.code == Constants.NetworkConst.IS_FAILED) {

                return@launch
            }
            if (result.data == null) {

                return@launch
            }
            withContext(Dispatchers.Main) {
                Constants.signedInUser = result.data
                _signInFlag.value = true
            }
        }
    }

    private suspend fun requestSignIn(): ModelResponse<User?> {

        return ModelResponse(Constants.NetworkConst.IS_SUCCESS, Dummies.makeDummyUser())
    }

    fun toSignUpPage() {
        _signUpFlag.value = true
    }
}
