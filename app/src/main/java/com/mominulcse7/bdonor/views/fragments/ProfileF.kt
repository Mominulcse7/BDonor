package com.mominulcse7.bdonor.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mominulcse7.bdonor.R
import com.mominulcse7.bdonor.databinding.FragmentNotificationBinding
import com.mominulcse7.bdonor.databinding.FragmentProfileBinding

class ProfileF : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(lif: LayoutInflater, vg: ViewGroup?, sis: Bundle?): View {
        binding = FragmentProfileBinding.inflate(lif, vg, false)
        return binding.root
    }
}