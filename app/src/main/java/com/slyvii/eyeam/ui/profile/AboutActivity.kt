package com.slyvii.eyeam.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.slyvii.eyeam.R
import com.slyvii.eyeam.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.about_us)
    }
}