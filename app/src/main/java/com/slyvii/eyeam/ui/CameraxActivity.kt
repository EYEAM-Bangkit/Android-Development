package com.slyvii.eyeam.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.slyvii.eyeam.databinding.ActivityCameraxBinding

class CameraxActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraxBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraxBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}