package com.example.androidtesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModelProvider
import com.example.androidtesting.databinding.ActivityMainBinding
import com.example.androidtesting.factory.MyViewModelFactory
import com.example.androidtesting.model.MyCal
import com.example.androidtesting.viewmodel.MyViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //    private val viewModel: MyViewModel by viewModels()
    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Creating Viewmodel Class this ths It.
        val viewModelFactory=MyViewModelFactory(MyCal())
        viewModel= ViewModelProvider(this,viewModelFactory)[MyViewModel::class.java]
        viewModel.area.observe(this) {
            it?.let { value ->
                binding.areaTxtField.append("\n\n $value\n")
            }
        }

        viewModel.circular.observe(this) {
            it?.let { value ->
                binding.cirTxtField.append("\n\n $value\n")
            }
        }

        binding.calBtnTxt.setOnClickListener {
            val value = binding.userEditTxt.text
            if (value.isNullOrBlank() || value.isNullOrEmpty() || !value.toString()
                    .isDigitsOnly()
            ) {
                Toast.makeText(this, "Enter the Correct value", Toast.LENGTH_SHORT).show()
            }
            val radius = value.toString().toDouble()
            viewModel.getCalculate(radius)
        }
    }

}