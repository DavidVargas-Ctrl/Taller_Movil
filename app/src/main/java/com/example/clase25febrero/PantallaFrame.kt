package com.example.clase25febrero

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clase25febrero.databinding.ActivityPantallaFrameBinding

class PantallaFrame : AppCompatActivity() {
    lateinit var binding: ActivityPantallaFrameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_frame)
        binding = ActivityPantallaFrameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}