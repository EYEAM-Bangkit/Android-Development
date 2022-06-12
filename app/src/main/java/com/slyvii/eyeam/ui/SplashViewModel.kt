package com.slyvii.eyeam.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.slyvii.eyeam.utils.SettingPreferences

class SplashViewModel(private val pref : SettingPreferences): ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
    fun isLogin(): LiveData<Boolean>{
        return pref.getLoginState().asLiveData()
    }
}