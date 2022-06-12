package com.slyvii.eyeam.ui.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.slyvii.eyeam.BuildConfig
import com.slyvii.eyeam.R
import com.slyvii.eyeam.api.ApiConfig
import com.slyvii.eyeam.data.ArticlesItem
import com.slyvii.eyeam.data.NewsResponse
import com.slyvii.eyeam.utils.SettingPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val pref : SettingPreferences) : ViewModel() {
    private val newsApi = BuildConfig.newsApiKey
    private val query = "endangered-animal"

    fun getNews(context: Context): LiveData<List<ArticlesItem>> {
        val resultNews = MutableLiveData<List<ArticlesItem>>()

        val client = ApiConfig.getNewsApiService().getNews(query, "publishedAt", newsApi)
        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val result = ArrayList<ArticlesItem>()
                        responseBody.articles?.forEach {
                            it?.let { data -> result.add(data) }
                        }
                        resultNews.value = result
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
            }
        })
        return resultNews
    }

    fun getToken(): LiveData<String> {
        return pref.getToken().asLiveData()
    }
    fun getName(): LiveData<String> {
        return pref.getDisplayName().asLiveData()
    }
}