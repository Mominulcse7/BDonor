package com.mominulcse7.bdonor.views.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.mominulcse7.bdonor.BuildConfig
import com.mominulcse7.bdonor.R
import com.mominulcse7.bdonor.databinding.ActivityMainBinding
import com.mominulcse7.bdonor.model.AppVersionModel
import com.mominulcse7.bdonor.model.NotificationModel
import com.mominulcse7.bdonor.model.UserModel
import com.mominulcse7.bdonor.sessions.LoginPreference
import com.mominulcse7.bdonor.sessions.TempPreference
import com.mominulcse7.bdonor.utils.ConstantKeys.APP_FCM_TOPIC
import com.mominulcse7.bdonor.utils.ConstantKeys.FRAGMENT
import com.mominulcse7.bdonor.utils.ConstantKeys.INTENT_DATA
import com.mominulcse7.bdonor.utils.ConstantKeys.NOTIFY_FRAGMENT
import com.mominulcse7.bdonor.utils.ConstantKeys.PLAY_STORE_LINK
import com.mominulcse7.bdonor.utils.haveNetwork
import com.mominulcse7.bdonor.utils.popupNotification
import com.mominulcse7.bdonor.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private var isUpdateMandatory = 0
    private var dbAppVersion = 0

    private var appVersionModel: AppVersionModel? = null
    private var notificationModel: NotificationModel? = null
    private var intentFragment = ""

    private var uModel = UserModel()
    private lateinit var activity: Activity

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var gso: GoogleSignInOptions

    private val viewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var loginPreference: LoginPreference

    @Inject
    lateinit var tempPreference: TempPreference

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var bottomAppBar: BottomAppBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initiateView()
        initiateBottomNavigation()
        observeViewModel()
        getNotificationData()
    }

    private fun observeViewModel() {
        viewModel.application = activity.application
        viewModel.getHomeData()

        viewModel.liveRpHomeDataModel.observe(this) {
            if (it != null) {
                appVersionModel = it.data?.appVersion

                if (appVersionModel?.isUpdateMandatory != null)
                    isUpdateMandatory = appVersionModel?.isUpdateMandatory!!

                if (appVersionModel?.versionCode != null)
                    dbAppVersion = appVersionModel?.versionCode!!

                checkAppUpdate()
            }
        }
    }

    private fun initiateView() {
        activity = this
        uModel = loginPreference.gUserModel()

        gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(activity, gso)

        if (intent.extras != null)
            intentFragment = try {
                intent.getStringExtra(FRAGMENT)!!
            } catch (e: Exception) {
                ""
            }

        if (intentFragment == NOTIFY_FRAGMENT)
            findNavController(R.id.navHostFragment).navigate(R.id.notificationF)
    }

    private fun initiateBottomNavigation() {
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

    private fun setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window = window
            val decorView = window.decorView
            val wic = WindowInsetsControllerCompat(window, decorView)
            wic.isAppearanceLightStatusBars = true // true or false as desired.
            // And then you can set any background color to the status bar.
            window.statusBarColor = ContextCompat.getColor(this, R.color.cWhite)
        }
    }

    private fun getNotificationData() {
        notificationModel = null
        if (intent.extras != null) {
            try {
                notificationModel = Gson().fromJson(
                    intent.getStringExtra(INTENT_DATA),
                    NotificationModel::class.java
                )
            } catch (e: Exception) {
            }
        }
        if (notificationModel != null)
            popupNotification(
                notificationModel?.title!!,
                notificationModel?.message!!
            )
    }

    fun logoutAlert() {
        MaterialAlertDialogBuilder(this).setCancelable(false)
            .setIcon(R.drawable.ic_logout)
            .setTitle(resources.getString(R.string.hello))
            .setMessage(resources.getString(R.string.want_to_sign_out))
            .setNegativeButton(resources.getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.yes)) { dialog, _ ->
                logout()
                dialog.dismiss()
            }
            .show()
    }

    fun logout() {
        if (haveNetwork()) {
            try {
                FirebaseMessaging.getInstance().deleteToken()
            } catch (e: Exception) {
            }

            try {
                FirebaseMessaging.getInstance().unsubscribeFromTopic(APP_FCM_TOPIC)
            } catch (e: Exception) {
            }

            try {
                googleSignInClient.signOut()
            } catch (e: Exception) {
            }

            if (uModel.id != null && uModel.id != 0L) {
                viewModel.userLogout()
            }
        }

        loginPreference.clearLoginPreference()
        tempPreference.clearTempPreference()

        val intent = Intent(activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mHome -> {
                findNavController(R.id.navHostFragment).navigate(R.id.postsF)
            }
            R.id.mNotification -> {
                findNavController(R.id.navHostFragment).navigate(R.id.notificationF)
            }
            R.id.mProfile -> {
                findNavController(R.id.navHostFragment).navigate(R.id.profileF)
            }
            R.id.mSettings -> {
                findNavController(R.id.navHostFragment).navigate(R.id.settingsF)
            }
        }
        return true
    }


    override fun onResume() {
        super.onResume()
        if (isUpdateMandatory == 1)
            checkAppUpdate()
    }

    private fun checkAppUpdate() {
        val currentAppVersion = try {
            BuildConfig.VERSION_CODE.toLong()
        } catch (e: Exception) {
            0L
        }

        if (currentAppVersion < dbAppVersion) {

            var nButton = resources.getString(R.string.later)
            if (isUpdateMandatory == 1)
                nButton = resources.getString(R.string.exit)

            MaterialAlertDialogBuilder(this).setCancelable(false)
                .setIcon(R.drawable.ic_logo)
                .setTitle(appVersionModel?.title)
                .setMessage(appVersionModel?.message)
                .setCancelable(false)
                .setPositiveButton(resources.getString(R.string.update)) { dialog, _ ->
                    dialog.dismiss()
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(PLAY_STORE_LINK)
                        )
                    )
                }.setNegativeButton(nButton) { dialog, _ ->
                    if (isUpdateMandatory == 1) {
                        dialog.dismiss()
                        finishAffinity()
                    } else
                        dialog.dismiss()
                }.show()
        }
    }

}