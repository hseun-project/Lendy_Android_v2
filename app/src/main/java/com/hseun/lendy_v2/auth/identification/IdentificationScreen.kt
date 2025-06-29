package com.hseun.lendy_v2.auth.identification

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.hseun.lendy_v2.auth.identification.viewmodel.IdentificationViewModel
import com.hseun.lendy_v2.ui.LoadingView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun IdentificationScreen(
    viewModel: IdentificationViewModel = hiltViewModel(),
    navigationGoBack: () -> Unit,
    navToMain: () -> Unit
) {
    val context = LocalContext.current
    var webView: WebView? by remember { mutableStateOf(null) }

    val currentUrl = viewModel.url
    val isNavToMain = viewModel.isNavToMain
    val isLoading = viewModel.isLoading
    val isGetUrl = viewModel.isGetUrl

    LaunchedEffect(Unit) {
        viewModel.getStartUrl()
    }

    LaunchedEffect(isGetUrl) {
        if (isGetUrl == false) {
            Toast.makeText(context, "본인인증 화면을 불러오는 데 실패했습니다", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(isNavToMain) {
        if (isNavToMain) {
            navToMain()
        }
    }

    val onPressBack = {
        if (webView?.canGoBack() == true) {
            webView?.goBack()
        } else {
            navigationGoBack()
        }
    }

    BackHandler {
        onPressBack()
    }

    when {
        isLoading -> {
            LoadingView(
                text = "본인인증 화면을 불러오는 중입니다"
            )
        }
        isGetUrl == true -> {
            AndroidView(
                factory = {
                    val myWebView = WebView(it)
                    myWebView.webViewClient = CustomWebViewClient { url ->
                        viewModel.onUrlLoaded(url)
                    }

                    myWebView.settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        mixedContentMode = WebSettings.MIXED_CONTENT_NEVER_ALLOW
                        loadsImagesAutomatically = true
                        cacheMode = WebSettings.LOAD_DEFAULT
                        textZoom = 100
                        mediaPlaybackRequiresUserGesture = false
                    }

                    myWebView.apply {
                        webView = this
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }
                },
                update = {
                    viewModel.onUrlLoaded(it.url.toString())
                    it.loadUrl(currentUrl)
                }
            )
        }
    }
}

class CustomWebViewClient(
    private val onRedirectToFinalUrl: (String) -> Unit
) : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url.toString()
        return try {
            if (url.startsWith("http://localhost:8080/open")) {
                onRedirectToFinalUrl(url)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e("CustomWebViewClient", e.message.toString())
            false
        }
    }
}