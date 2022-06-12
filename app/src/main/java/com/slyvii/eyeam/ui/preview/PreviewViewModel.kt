package com.slyvii.eyeam.ui.preview

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.slyvii.eyeam.R
import com.slyvii.eyeam.api.ApiConfig
import com.slyvii.eyeam.data.ClassifierResponse
import com.slyvii.eyeam.data.Data
import com.slyvii.eyeam.data.EyeamResponse
import com.slyvii.eyeam.utils.SettingPreferences
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreviewViewModel(private val pref : SettingPreferences) : ViewModel() {

    fun postImage(auth: String, imageMultipart: RequestBody, context: Context): LiveData<String> {
        val result = MutableLiveData<String>()
        val service = ApiConfig.getApiService().postImage(auth, imageMultipart)
        service.enqueue(object: Callback<ClassifierResponse>{
            override fun onResponse(
                call: Call<ClassifierResponse>,
                response: Response<ClassifierResponse>
            ) {
                if (response.isSuccessful){
                    if (response.body()?.animalName == "noanimalfound"){
                        Toast.makeText(context, R.string.no_animal_found, Toast.LENGTH_SHORT).show()
                        result.value = response.body()?.animalName ?: ""
                    } else {
                        result.value = response.body()?.animalName ?: ""
                        Toast.makeText(context, R.string.success, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ClassifierResponse>, t: Throwable) {
//                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
            }
        })
        return result
    }

    fun getToken(): LiveData<String> {
        return pref.getToken().asLiveData()
    }
}