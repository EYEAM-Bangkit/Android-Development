package com.slyvii.eyeam.ui.profile

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.*
import com.slyvii.eyeam.utils.SettingPreferences
import kotlinx.coroutines.launch

class ProfileViewModel(private val pref: SettingPreferences) : ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    fun getName(): LiveData<String> {
        return pref.getDisplayName().asLiveData()
    }

    fun logout(){
        viewModelScope.launch {
            pref.saveLoginState(false)
        }
    }
}