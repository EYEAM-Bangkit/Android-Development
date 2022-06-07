package com.slyvii.eyeam.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import com.slyvii.eyeam.MainActivity
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

        binding.progressBar.isVisible = true

        url = intent.getStringExtra(URL)

        val myWebView: WebView = binding.webView
        myWebView.loadUrl(url!!)

        myWebView.webViewClient = WebViewClient()

//        binding.btnBack.setOnClickListener {
//            startActivity(Intent(this@NewsDetailActivity, MainActivity::class.java))
//        }
//    }
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