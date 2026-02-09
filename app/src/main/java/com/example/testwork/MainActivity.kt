package com.example.testwork

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.testwork.databinding.AuthBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = AuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonSafe.setBackgroundColor(ContextCompat.getColor(this, R.color.black))

    }
}
