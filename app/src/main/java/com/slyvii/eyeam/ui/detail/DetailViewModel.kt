package com.slyvii.eyeam.ui.detail

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.slyvii.eyeam.R
import com.slyvii.eyeam.api.ApiConfig
import com.slyvii.eyeam.data.Data
import com.slyvii.eyeam.data.EyeamResponse
import com.slyvii.eyeam.utils.SettingPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val pref: SettingPreferences) : ViewModel() {

    fun getAnimal(auth: String, name: String, context: Context): LiveData<ArrayList<Data>> {
        val animalData = MutableLiveData<ArrayList<Data>>()
        val service = ApiConfig.getApiService().getAnimalName(auth, name)
        service.enqueue(object : Callback<EyeamResponse> {
            override fun onResponse(call: Call<EyeamResponse>, response: Response<EyeamResponse>) {

                val listAnimal = ArrayList<Data>()
                response.body()?.data?.forEach {
                    listAnimal.add(it)
                }
                animalData.value = listAnimal
                Log.e("animalData", "onResponse: $listAnimal")

            }

            override fun onFailure(call: Call<EyeamResponse>, t: Throwable) {
                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
            }
        })
        return animalData
    }

    fun getToken(): LiveData<String> {
        return pref.getToken().asLiveData()
    }
}