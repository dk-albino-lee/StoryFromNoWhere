package com.movingroot.storyfromnowhere.ui.sign.sighin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.movingroot.storyfromnowhere.databinding.FragmentSignInBinding
import com.movingroot.storyfromnowhere.ui.base.BaseFragment

class SignInFragment : BaseFragment() {
    private val binding: FragmentSignInBinding get() = _binding!! as FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModels()

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
    }

    private fun bindViewModel() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
    }
}
