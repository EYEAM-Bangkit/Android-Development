package com.slyvii.eyeam.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.slyvii.eyeam.R
import com.slyvii.eyeam.databinding.ActivitySplashBinding
import com.slyvii.eyeam.ui.auth.LoginActivity
import com.slyvii.eyeam.utils.SettingPreferences


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val pref = SettingPreferences.getInstance(dataStore)
        val viewModel = ViewModelProvider(this, ViewModelFactory(pref))[SplashViewModel::class.java]

        viewModel.getThemeSettings().observe(this) {
            if (it) {
                setDefaultNightMode(MODE_NIGHT_YES)
            } else {
                setDefaultNightMode(MODE_NIGHT_NO)
            }
        }

        viewModel.isLogin().observe(this){
            if (it){
                mainIntent()
            } else {
                loginIntent()
            }
        }
        Glide.with(this).load(R.drawable.giphy).into(binding.viewGif)
    }

    private fun mainIntent() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, 2000L)
    }
    private fun loginIntent() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }, 2000L)
    }
}