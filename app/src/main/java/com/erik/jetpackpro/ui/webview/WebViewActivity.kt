package com.erik.jetpackpro.ui.webview

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.erik.jetpackpro.R
import com.erik.jetpackpro.databinding.ActivityWebViewBinding
import com.example.awesomedialog.*
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {
    companion object {
        var current_url = ""
    }

    @SuppressLint("SetJavaScriptEnabled", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityWebViewBinding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(activityWebViewBinding.root)

        var url = intent.getStringExtra("url")
        if (url.isNullOrEmpty()){
            AwesomeDialog.build(this)
                    .title(
                            resources.getString(R.string.url_not_found),
                            titleColor = ContextCompat.getColor(this, android.R.color.black)
                    )
                    .icon(R.drawable.empty)
                    .background(R.drawable.layout_rounded_white)
                    .onPositive(
                            resources.getString(R.string.OK),
                            buttonBackgroundColor = R.drawable.layout_rounded_dark_black,
                            textColor = ContextCompat.getColor(this, android.R.color.white)
                    )
                    .position(AwesomeDialog.POSITIONS.CENTER)
        } else {
            url = url!!.replace("https://", "")
            val fullDomain = url.split(".com")
            val domain = fullDomain[0].split(".")

            tvLink.text = domain[1] + ".com"
            wvPage.settings.javaScriptEnabled = true
            wvPage.settings.builtInZoomControls = true
            wvPage.settings.displayZoomControls = false
            wvPage.isHorizontalScrollBarEnabled = false
            wvPage.isVerticalFadingEdgeEnabled = false
            wvPage.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    var urls = url
                    current_url = url!!
                    urls = urls!!.replace("https://", "")
                    val fullDomain = urls.split(".com")
                    val domain = fullDomain[0].split(".")
                    tvLink.text = domain[1] + ".com"
                    shWebView.visibility = View.VISIBLE
                    shWebView.startShimmer()
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    shWebView.stopShimmer()
                    shWebView.visibility = View.GONE
                }
            }

            wvPage.loadUrl(intent.getStringExtra("url").toString())
        }


        ivBack.setOnClickListener {
            onBack()
        }

        tvButton.setOnClickListener {
            onBrowser()
        }
    }

    override fun onBackPressed() {
        if (wvPage.canGoBack()) {
            wvPage.goBack()
        } else {
            super.onBackPressed()
        }
    }

    private fun onBack() {
        if (wvPage.canGoBack()) {
            wvPage.goBack()
        } else {
            finish()
        }
    }

    private fun onBrowser() {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(current_url)
        startActivity(i)
    }
}