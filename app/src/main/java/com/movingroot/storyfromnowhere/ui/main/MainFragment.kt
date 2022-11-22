package com.movingroot.storyfromnowhere.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.movingroot.storyfromnowhere.databinding.FragmentMainBinding
import com.movingroot.storyfromnowhere.ui.base.BaseFragment

class MainFragment : BaseFragment() {
    private val binding: FragmentMainBinding get() = _binding!! as FragmentMainBinding
    private val viewModel: MainFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun bindViewModel() {
        super.bindViewModel()
        binding.lifecycleOwner = viewLifecycleOwner
    }
}
