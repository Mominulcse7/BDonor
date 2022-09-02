package com.mominulcse7.bdonor.views.activities

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView
import com.mominulcse7.bdonor.R
import com.mominulcse7.bdonor.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var bottomAppBar: BottomAppBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window = window
            val decorView = window.decorView
            val wic = WindowInsetsControllerCompat(window, decorView)
            wic.isAppearanceLightStatusBars = true // true or false as desired.
            // And then you can set any background color to the status bar.
            window.statusBarColor = ContextCompat.getColor(this, R.color.cWhite)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        floatingActionButton = binding.floatingActionButton
        bottomAppBar = binding.bottomAppBar
        bottomNavView = binding.bottomNavView

        bottomNavView.background = null
        bottomNavView.menu.getItem(2).isEnabled = false

        bottomNavView.setOnItemSelectedListener(this)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.postsF -> showBottomNav()
                else -> hideBottomNav()
            }
        }
    }

    private fun showBottomNav() {
        floatingActionButton.visibility = View.VISIBLE
        bottomAppBar.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        floatingActionButton.visibility = View.GONE
        bottomAppBar.visibility = View.GONE
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