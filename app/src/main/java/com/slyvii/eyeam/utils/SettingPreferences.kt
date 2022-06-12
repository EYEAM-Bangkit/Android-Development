package com.slyvii.eyeam.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val THEME_KEY = booleanPreferencesKey("theme_setting")
    private val TOKEN_KEY = stringPreferencesKey("token_key")
    private val IS_LOGIN = booleanPreferencesKey("is_login")
    private val DISPLAY_NAME = stringPreferencesKey("display_name")

    fun getToken(): Flow<String>{
        return dataStore.data.map {
            it[TOKEN_KEY] ?: ""
        }
    }

    suspend fun setToken(token: String){
        dataStore.edit {
            it[TOKEN_KEY] = token
        }
    }

    fun getDisplayName(): Flow<String>{
        return dataStore.data.map {
            it[DISPLAY_NAME] ?: ""
        }
    }

    suspend fun setDisplayName(name: String){
        dataStore.edit {
            it[DISPLAY_NAME] = name
        }
    }

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveLoginState(isLogin: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LOGIN] = isLogin
        }
    }

    fun getLoginState(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[IS_LOGIN] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}