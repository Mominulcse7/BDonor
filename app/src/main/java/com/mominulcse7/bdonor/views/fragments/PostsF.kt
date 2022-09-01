package com.mominulcse7.bdonor.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mominulcse7.bdonor.R
import com.mominulcse7.bdonor.databinding.FragmentPostsBinding
import com.mominulcse7.bdonor.viewModel.HomeViewModel

class PostsF : Fragment() {

    private lateinit var binding: FragmentPostsBinding

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(lif: LayoutInflater, vg: ViewGroup?, sis: Bundle?): View {
        binding = FragmentPostsBinding.inflate(lif, vg, false)
        initiateView()
        observeViewModel()
        setList()
        return binding.root
    }

    private fun initiateView() {

    }

    private fun observeViewModel() {

    }

    private fun setList() {

    }

}