package com.slyvii.eyeam.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.core.view.isVisible
import com.slyvii.eyeam.R
import com.slyvii.eyeam.databinding.ActivityNewsDetailBinding

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailBinding
    private var url: String? = null

    companion object {
        const val URL = "url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.news)

        binding.progressBar.isVisible = true

        url = intent.getStringExtra(URL)

        val myWebView: WebView = binding.webView
        myWebView.loadUrl(url!!)

        myWebView.webViewClient = WebViewClient()

    }

    inner class WebViewClient : android.webkit.WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            view?.loadUrl(url!!)
            return false
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.progressBar.isVisible = false
        }
    }
}