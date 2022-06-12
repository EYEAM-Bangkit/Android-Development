package com.slyvii.eyeam.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slyvii.eyeam.utils.SettingPreferences
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: SettingPreferences): ViewModel() {

    fun saveToken(token: String) {
        viewModelScope.launch {
            pref.setToken(token)
        }
    }
    fun saveName(name: String) {
        viewModelScope.launch {
            pref.setDisplayName(name)
        }
    }
    fun saveState(isLogin: Boolean) {
        viewModelScope.launch {
            pref.saveLoginState(isLogin)
        }
    }

}