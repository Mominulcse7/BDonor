package com.mominulcse7.bdonor.views.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.mominulcse7.bdonor.R
import com.mominulcse7.bdonor.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        bottomNavView = binding.bottomNavView
        bottomNavView.background = null
        bottomNavView.menu.getItem(2).isEnabled = false

        bottomNavView.setOnItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mProfile -> {
                findNavController(R.id.navHostFragment).navigate(R.id.loginF)
            }
            R.id.mHome -> {
                findNavController(R.id.navHostFragment).navigate(R.id.postsF)
            }
        }
        return true
    }
}