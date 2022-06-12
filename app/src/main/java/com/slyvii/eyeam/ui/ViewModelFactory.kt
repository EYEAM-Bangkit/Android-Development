package com.slyvii.eyeam.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.slyvii.eyeam.ui.auth.LoginViewModel
import com.slyvii.eyeam.ui.detail.DetailViewModel
import com.slyvii.eyeam.ui.preview.PreviewViewModel
import com.slyvii.eyeam.ui.history.HistoryViewModel
import com.slyvii.eyeam.ui.home.HomeViewModel
import com.slyvii.eyeam.ui.profile.ProfileViewModel
import com.slyvii.eyeam.utils.SettingPreferences

class ViewModelFactory(private val pref: SettingPreferences) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(pref) as T
        } else if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(pref) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(pref) as T
        } else if (modelClass.isAssignableFrom(PreviewViewModel::class.java)) {
            return PreviewViewModel(pref) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(pref) as T
        } else if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(pref) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}