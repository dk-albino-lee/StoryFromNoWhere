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
    init {
        TAG = this::class.java.simpleName
    }

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

    private fun isReadyToMoveOn(): Boolean {
        if (nickNameInput.value!!.length >= 2 && passwordInput.value!!.length >= 6)
            return true
        return false
    }

    fun initializeToast() {
        _toastMessage.value = ""
    }

    fun toSignInPage() {
        if (nickNameInput.value!!.length < 2) {
            _toastMessage.value = "별명은 2자 이상 입력해주세요."
            return
        }
        if (passwordInput.value!!.length < 6) {
            _toastMessage.value = "비밀번호는 6자 이상 입력해주세요."
            return
        }
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
