package com.movingroot.storyfromnowhere.ui.sign.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.movingroot.storyfromnowhere.databinding.FragmentSignUpBinding
import com.movingroot.storyfromnowhere.ui.base.BaseFragment

class SignUpFragment : BaseFragment() {
    private val binding: FragmentSignUpBinding get() = _binding!! as FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

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
