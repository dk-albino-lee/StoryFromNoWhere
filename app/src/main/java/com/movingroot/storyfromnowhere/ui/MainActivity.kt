package com.movingroot.storyfromnowhere.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.movingroot.storyfromnowhere.R
import com.movingroot.storyfromnowhere.databinding.ActivityMainBinding
import com.movingroot.storyfromnowhere.ui.sign.sighin.SignInFragmentDirections
import com.movingroot.storyfromnowhere.ui.sign.signup.SignUpFragmentDirections

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActivity()
    }

    private fun initActivity() {
        bindViewModel()
        toSignInFragment()
    }

    private fun bindViewModel() {
        binding.viewModel = viewModel
    }

    private fun toSignInFragment() {
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val inflater = navHost.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)
            .apply {
                setStartDestination(R.id.signInFragment)
            }

        navHost.navController.setGraph(graph, Bundle())
    }

    fun moveFromSignInToMain() {
        val action = SignInFragmentDirections.actionSignInFragmentToMainFragment()
        binding.navHostFragment.findNavController().navigate(action)
    }

    fun moveFromSignInToSignUp() {
        val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
        binding.navHostFragment.findNavController().navigate(action)
    }

    fun moveFromSignUpToSignIn() {
        val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
        binding.navHostFragment.findNavController().navigate(action)
    }
}
