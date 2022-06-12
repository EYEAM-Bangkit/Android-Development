package com.slyvii.eyeam.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.slyvii.eyeam.BuildConfig
import com.slyvii.eyeam.R
import com.slyvii.eyeam.api.ApiConfig
import com.slyvii.eyeam.data.LoginRequest
import com.slyvii.eyeam.data.LoginResponse
import com.slyvii.eyeam.databinding.ActivityLoginBinding
import com.slyvii.eyeam.ui.MainActivity
import com.slyvii.eyeam.ui.ViewModelFactory
import com.slyvii.eyeam.utils.SettingPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory(SettingPreferences.getInstance(dataStore))
    }
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        state(false)

        auth = Firebase.auth

        supportActionBar?.hide()

        binding.txtLoginSignup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.signIn.setOnClickListener {
            state(true)
            if (requirementCheck()) {
                login()
            }
        }
    }

    private fun login() {
        binding.apply {
            val key = BuildConfig.loginKey
            val raw = LoginRequest(email.text.toString(), myPassword.text.toString())
            val service = ApiConfig.getJwtToken().postToGetJwt(key, raw)
            service.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.idToken?.let { viewModel.saveToken(it) }
                        response.body()?.displayName?.let { viewModel.saveName(it) }
                        Toast.makeText(this@LoginActivity, R.string.success, Toast.LENGTH_SHORT)
                            .show()
                        viewModel.saveState(true)
                        state(false)
                        mainIntent()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, R.string.error, Toast.LENGTH_SHORT).show()
                }

            })

        }
    }

    private fun mainIntent() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun requirementCheck(): Boolean {
        var state = false
        binding.apply {
            if (email.text.isNullOrEmpty() || myPassword.text.isNullOrEmpty()) {
                Toast.makeText(
                    baseContext,
                    resources.getText(R.string.fill_form),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                state = true
            }
        }
        return state
    }

    private fun state(b: Boolean){
        if (b){
            binding.progressCircular.visibility = View.VISIBLE
            binding.signIn.isEnabled = false
        } else {
            binding.progressCircular.visibility = View.GONE
            binding.signIn.isEnabled = true
        }
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            finishAffinity()
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, R.string.double_back, Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}