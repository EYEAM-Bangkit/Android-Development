package com.slyvii.eyeam.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.slyvii.eyeam.BuildConfig
import com.slyvii.eyeam.api.ApiConfig
import com.slyvii.eyeam.data.ArticlesItem
import com.slyvii.eyeam.data.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val newsApi = BuildConfig.newsApiKey
    private val query = "endangered-animal"

    fun getNews(): LiveData<List<ArticlesItem>> {
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
                Log.e("RemoteDataSource", "getDetailTvShow onFailure : ${t.message}")
            }
        })
        return resultNews
    }

}