package com.mominulcse7.bdonor.views.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.mominulcse7.bdonor.R
import com.mominulcse7.bdonor.databinding.FragmentLoginBinding
import com.mominulcse7.bdonor.model.UserModel
import com.mominulcse7.bdonor.sessions.AppPreference
import com.mominulcse7.bdonor.sessions.LoginPreference
import com.mominulcse7.bdonor.utils.ConstantKeys.FAILED
import com.mominulcse7.bdonor.utils.ConstantKeys.LOGIN_S1
import com.mominulcse7.bdonor.utils.ConstantKeys.LOGIN_S2
import com.mominulcse7.bdonor.utils.ConstantKeys.LOGIN_S3
import com.mominulcse7.bdonor.utils.DotsProgressBar.DDProgressBarDialog
import com.mominulcse7.bdonor.utils.closeKeyboard
import com.mominulcse7.bdonor.utils.toastShow
import com.mominulcse7.bdonor.viewModel.HomeViewModel
import com.mominulcse7.bdonor.views.activities.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginF : Fragment(), View.OnClickListener {

    @Inject
    lateinit var loginPreference: LoginPreference

    @Inject
    lateinit var appPreference: AppPreference

    private lateinit var binding: FragmentLoginBinding
    private lateinit var imageSlider: ImageSlider
    private val imageList = ArrayList<SlideModel>()
    private lateinit var pDialog: DDProgressBarDialog
    private lateinit var activity: Activity

    private var fcmToken = ""

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var gso: GoogleSignInOptions

    private val GMAIL_LOGIN_REQUESTS = 202
    private val REQ_PHONE_AUTH = 205
    private var CLICKED_REQUESTS = 0

    private lateinit var prLauncher: ActivityResultLauncher<String>
    private lateinit var irLauncher: ActivityResultLauncher<Intent>

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = requireActivity()
        pDialog = DDProgressBarDialog(activity)
        fcmToken = appPreference.gDeviceToken()

        gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    override fun onCreateView(lif: LayoutInflater, vg: ViewGroup?, sis: Bundle?): View {
        binding = FragmentLoginBinding.inflate(lif, vg, false)
        initiateView()
        setImageSlider()
        intentResult()
        observeViewModel()
        return binding.root
    }

    private fun initiateView() {
        binding.llLoginWithGmail.setOnClickListener(this)

//        imageSlider.setItemChangeListener(this)
    }

    override fun onClick(v: View) {
        activity.closeKeyboard()
        when (v.id) {
            R.id.llLoginWithGmail -> {
                CLICKED_REQUESTS = GMAIL_LOGIN_REQUESTS
                irLauncher.launch(googleSignInClient.signInIntent)
            }
        }
    }

    private fun observeViewModel() {

    }

    private fun intentResult() {
        irLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (CLICKED_REQUESTS == GMAIL_LOGIN_REQUESTS && it.data != null) {
                try {
                    val intentData = it.data
                    val task = GoogleSignIn.getSignedInAccountFromIntent(intentData)
                    val account = task.getResult(ApiException::class.java)
                    saveGmailDataToDB(account)
                } catch (e: Exception) {
                    requireActivity().toastShow("Failed to login with gmail.")
                }
            } else
                requireActivity().toastShow("Failed to login. Try again after sometime")
        }
    }

    private fun saveGmailDataToDB(account: GoogleSignInAccount) {
        val name = account.displayName
        val email = account.email!!.lowercase().trim()
        val image = try {
            account.photoUrl.toString()
        } catch (e: Exception) {
            ""
        }

        val sdModel = UserModel()
        sdModel.androidToken = appPreference.gDeviceToken()
        sdModel.name = name
        sdModel.email = email
        sdModel.image = image

        pDialog.show()
        viewModel.userLoginWithGmail(sdModel)
    }

    private fun gotoHomePage(rModel: UserModel) {
        loginPreference.clearLoginPreference()
        loginPreference.sUserModel(rModel)

//        (requireActivity() as MainActivity).reloadUserLogin()

        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun setImageSlider() {
        imageSlider = binding.imageSlider

        imageList.add(SlideModel(LOGIN_S1, resources.getString(R.string.find_blood)))
        imageList.add(SlideModel(LOGIN_S2, resources.getString(R.string.donate_blood)))
        imageList.add(SlideModel(LOGIN_S3, resources.getString(R.string.donate_blood_save_life)))

        imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)
    }

}