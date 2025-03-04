package com.example.clase25febrero

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clase25febrero.databinding.ActivityPantallaWebBinding

class PantallaWeb : AppCompatActivity() {
    lateinit var binding: ActivityPantallaWebBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPantallaWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webview.webViewClient = WebViewClient()
        binding.webview.loadUrl("https://github.com/")
    }
}