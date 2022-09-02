package com.mominulcse7.bdonor.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemChangeListener
import com.denzcoskun.imageslider.models.SlideModel
import com.mominulcse7.bdonor.R
import com.mominulcse7.bdonor.databinding.FragmentLoginBinding
import com.mominulcse7.bdonor.viewModel.HomeViewModel

class LoginF : Fragment(), ItemChangeListener {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var imageSlider: ImageSlider
    private val imageList = ArrayList<SlideModel>()

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
        imageSlider = binding.imageSlider

        imageList.add(SlideModel("jksc", resources.getString(R.string.find_blood)))
        imageList.add(SlideModel("casc", resources.getString(R.string.donate_blood)))
        imageList.add(SlideModel("ascasc", resources.getString(R.string.donate_blood_save_life)))

        imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)

        imageSlider.setItemChangeListener(this)
    }

    private fun observeViewModel() {

    }

    override fun onItemChanged(position: Int) {

    }

}