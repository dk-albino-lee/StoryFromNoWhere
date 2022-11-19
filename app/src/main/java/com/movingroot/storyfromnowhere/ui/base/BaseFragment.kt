package com.movingroot.storyfromnowhere.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.launch

open class BaseFragment : Fragment() {
    protected var _binding: ViewBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBaseFragment()
    }

    private fun initBaseFragment() {
        viewLifecycleOwner.lifecycleScope.launch {
            setResumeAction()
            setDestroyAction()
        }
    }

    protected suspend fun setResumeAction() {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {

        }
    }

    protected suspend fun setDestroyAction() {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.DESTROYED) {
            _binding = null
        }
    }
}
