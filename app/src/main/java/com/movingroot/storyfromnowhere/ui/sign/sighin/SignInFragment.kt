package com.movingroot.storyfromnowhere.ui.sign.sighin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.movingroot.storyfromnowhere.BaseApplication
import com.movingroot.storyfromnowhere.databinding.FragmentSignInBinding
import com.movingroot.storyfromnowhere.ui.MainActivity
import com.movingroot.storyfromnowhere.ui.base.BaseFragment
import com.movingroot.storyfromnowhere.util.DataStoreModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SignInFragment : BaseFragment() {
    private val binding: FragmentSignInBinding get() = _binding!! as FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModels()
    private val dataStoreModule: DataStoreModule by lazy {
        BaseApplication.getInstance().getDataStoreModule()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        setObservers()
        retrieveValues()
    }

    override fun bindViewModel() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
    }

    override fun setObservers() {
        viewModel.toShowNetworkErrorDialog.observe(viewLifecycleOwner) { toShow ->
            if (toShow) {

            }
        }
        viewModel.toastMessage.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.initializeToast()
            }
        }
        viewModel.signUpFlag.observe(viewLifecycleOwner) { goodToGo ->
            if (goodToGo)
                (requireActivity() as MainActivity).moveFromSignInToSignUp()
        }
        viewModel.signInFlag.observe(viewLifecycleOwner) { goodToGo ->
            if (goodToGo) {
                saveSettings()
                (requireActivity() as MainActivity).moveFromSignInToMain()
            }
        }
    }

    private fun retrieveValues() {
        CoroutineScope(Dispatchers.Main).launch {
            retrieveCheckBox()
            retrieveNickname()
        }
    }

    private suspend fun retrieveCheckBox() {
        viewModel.retrieveSavedCheckBoxValue(
            dataStoreModule.retrieveBoolean("auto_sign_in").first(),
            dataStoreModule.retrieveBoolean("remember_nickname").first()
        )
    }

    private suspend fun retrieveNickname() {
        viewModel.retrieveNicknameIfRemembered(dataStoreModule.retrieveString("nickname").first())
    }

    private fun saveSettings() {
        CoroutineScope(Dispatchers.Main).launch {
            if (viewModel.rememberNickname.value!!)
                saveNickname()
            if (viewModel.autoSignIn.value!!)
                saveAutoSignInValue()
        }
    }

    private suspend fun saveNickname() {
        dataStoreModule.storeValue("nickname", viewModel.nickNameInput.value!!)
    }

    private suspend fun saveAutoSignInValue() {
        dataStoreModule.storeValue("auto_sign_in", viewModel.autoSignIn.value!!)
    }
}
