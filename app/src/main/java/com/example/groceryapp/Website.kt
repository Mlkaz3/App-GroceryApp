package com.example.groceryapp

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity


class Website : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_website)

        val webView: WebView = findViewById(R.id.website)
        val webSetting: WebSettings = webView.settings
        webSetting.javaScriptEnabled = true
        webView.loadUrl("http://mrfarmergrocer.com/")
        webView.webViewClient = WebViewClient()

    }
}