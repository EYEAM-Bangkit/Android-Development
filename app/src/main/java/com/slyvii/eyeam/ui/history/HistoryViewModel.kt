package com.slyvii.eyeam.ui.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.slyvii.eyeam.api.ApiConfig
import com.slyvii.eyeam.data.ArticlesItem
import com.slyvii.eyeam.data.DataItems
import com.slyvii.eyeam.data.HistoryResponse
import com.slyvii.eyeam.utils.SettingPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel(private val pref: SettingPreferences) : ViewModel() {
    fun getToken(): LiveData<String> {
        return pref.getToken().asLiveData()
    }

    fun getItems(token: String): LiveData<ArrayList<DataItems>> {
        val listHistory = MutableLiveData<ArrayList<DataItems>>()
        val service = ApiConfig.getApiService().getLogs(token)
        service.enqueue(object : Callback<HistoryResponse> {
            override fun onResponse(
                call: Call<HistoryResponse>,
                response: Response<HistoryResponse>
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val result = ArrayList<DataItems>()
                    responseBody.data?.forEach {
                        it.let { data -> result.add(data) }
                    }
                    Log.e("result", "onResponse: $result", )
                    listHistory.value = result
                }
            }

            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                Log.e("TAGs", "onFailure: ${t.message}", )
            }

        })
        return listHistory
    }
}