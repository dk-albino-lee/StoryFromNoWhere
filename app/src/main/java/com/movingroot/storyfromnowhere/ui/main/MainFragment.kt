package com.movingroot.storyfromnowhere.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.movingroot.storyfromnowhere.R
import com.movingroot.storyfromnowhere.databinding.FragmentMainBinding
import com.movingroot.storyfromnowhere.ui.base.BaseFragment

class MainFragment : BaseFragment() {
    private val binding: FragmentMainBinding get() = _binding!! as FragmentMainBinding
    private val viewModel: MainFragmentViewModel by viewModels()
//    private val navItemSelectListener = NavigationBarView.OnItemSelectedListener {
//        setSelection(it.itemId)
//        true
//    }

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
        initFragment()
//        binding.bottomMenu.setOnItemSelectedListener(navItemSelectListener)
//        binding.bottomMenu.setOnLongClickListener(null)
//        binding.bottomMenu.selectedItemId = R.id.page_feed
    }

    private fun initFragment() {
        bindViewModel()
        setUpNavigation()
    }

    override fun bindViewModel() {
        super.bindViewModel()
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setUpNavigation() {
        val navHost = childFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val controller = navHost.navController
        binding.bottomMenu.setupWithNavController(controller)
    }

//    private fun setChildView(fragmentClass: Class<out Fragment?>, itemId: Int) {
//        childFragmentManager.fragments.forEach { fragment ->
//            Logger.d("fragment: ${fragment.javaClass.simpleName}")
//            childFragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss()
//        }
//
//        val tag = itemId.toString()
//        val fragment = childFragmentManager.findFragmentByTag(tag)
//        fragment?.let { fragment ->
//            childFragmentManager.beginTransaction()
//                .show(fragment)
//                .commitAllowingStateLoss()
//            fragment.onResume()
//        } ?: run {
//            childFragmentManager.beginTransaction()
//                .add(binding.container.id, fragmentClass, null, tag)
//                .commitAllowingStateLoss()
//        }
//    }
//
//    private fun setSelection(itemId: Int): Boolean {
//        when (itemId) {
//            R.id.page_feed -> {
//                setChildView(FeedFragment::class.java, itemId)
//                return true
//            }
//            R.id.page_photo -> {
//                setChildView(CameraFragment::class.java, itemId)
//                return true
//            }
//            R.id.page_more -> {
//                setChildView(SettingsFragment::class.java, itemId)
//                return true
//            }
//        }
//
//        return false
//    }
}
