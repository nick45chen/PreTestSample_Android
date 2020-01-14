package com.example.fuglepretestsample.views.web

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.example.fuglepretestsample.R
import com.example.fuglepretestsample.databinding.ActivityWebViewBinding
import com.example.fuglepretestsample.views.base.BaseActivity

class WebViewActivity : BaseActivity() {

    private lateinit var viewBinding: ActivityWebViewBinding

    companion object {
        private const val KEY_SYMBOLS = "symbols"
        private const val URL = "https://www.fugle.tw/ai/%s"
        fun intentToSymbolsDetailView(context: Context, symbols: String): Intent {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(KEY_SYMBOLS, symbols)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_web_view)
        //
        setUpToolbarView(view = viewBinding.toolbar, title = "")
        setUpWebView(view = viewBinding.webView, progressBar = viewBinding.progressBar)
        //
        loadInComingIntent(intent = intent, view = viewBinding.webView)
    }

    private fun loadInComingIntent(intent: Intent, view: WebView) {
        intent.extras?.let {
            if (it.containsKey(KEY_SYMBOLS)) {
                val symbols = it.getString(KEY_SYMBOLS, "")
                view.loadUrl(String.format(URL, symbols))
            }
        }
    }

    private fun setUpWebView(view: WebView, progressBar: ProgressBar) {
        setUpWebSetting(view.settings)
        view.webViewClient = MyWebViewClient()
        view.webChromeClient = MyWebChromeClient(progressBar)
    }

    private fun setUpToolbarView(view: Toolbar, @Suppress("SameParameterValue") title: String) {
        super.setUpToolbar(view = view, title = title)
        super.setHomeButtonEnable(enable = true)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebSetting(settings: WebSettings) {
        //5.0以上開啟混合模式加載
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        // setting
        settings.javaScriptEnabled = true
        //
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        //
        settings.setSupportZoom(false)
        settings.builtInZoomControls = false
        settings.displayZoomControls = false
        //
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.allowFileAccess = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.loadsImagesAutomatically = true
        settings.defaultTextEncodingName = "utf-8"
    }

    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return true
        }
    }

    private inner class MyWebChromeClient(val progressBar: ProgressBar) : WebChromeClient() {
        override fun onProgressChanged(view: WebView, progress: Int) {
            super.onProgressChanged(view, progress)
            progressBar.visibility = if (progress != 100) View.VISIBLE else View.GONE
            progressBar.progress = progress
        }

        override fun onCloseWindow(window: WebView) {
            super.onCloseWindow(window)
            setResult(RESULT_OK)
            finish()
        }
    }
}
