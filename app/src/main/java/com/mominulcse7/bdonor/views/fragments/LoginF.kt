package com.mominulcse7.bdonor.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mominulcse7.bdonor.databinding.FragmentLoginBinding
import com.mominulcse7.bdonor.viewModel.HomeViewModel

class LoginF : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(lif: LayoutInflater, vg: ViewGroup?, sis: Bundle?): View {
        binding = FragmentLoginBinding.inflate(lif, vg, false)
        initiateView()
        observeViewModel()
        return binding.root
    }

    private fun initiateView() {

    }

    private fun observeViewModel() {

    }

}