package com.example.androidtesting


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtesting.databinding.NextActivityScreenBinding

class NextScreenActivity : AppCompatActivity() {
    private lateinit var binding: NextActivityScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NextActivityScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnOnPrev.setOnClickListener {
            onBackPressed()
        }
    }
}