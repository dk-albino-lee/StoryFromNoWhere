package com.movingroot.storyfromnowhere.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.movingroot.storyfromnowhere.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        initActivity()
    }

    private fun initActivity() {
        bindViewModel()
    }

    private fun bindViewModel() {
        binding.viewModel = viewModel
    }
}
