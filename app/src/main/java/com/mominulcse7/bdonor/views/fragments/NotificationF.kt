package com.mominulcse7.bdonor.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mominulcse7.bdonor.R
import com.mominulcse7.bdonor.databinding.FragmentLoginBinding
import com.mominulcse7.bdonor.databinding.FragmentNotificationBinding

class NotificationF : Fragment() {

    private lateinit var binding: FragmentNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(lif: LayoutInflater, vg: ViewGroup?, sis: Bundle?): View {
        binding = FragmentNotificationBinding.inflate(lif, vg, false)
        return binding.root
    }
}