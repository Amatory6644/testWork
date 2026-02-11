package com.example.testwork.presentation.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.testwork.R
import com.example.testwork.databinding.AuthBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = AuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.toastFlow.collect { msg ->
                    Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
                }
            }
        }


        var name = binding.nameLayout.editText
        var email = binding.mailLayout.editText
        var password = binding.password.editText
        var passwordConfirm = binding.passwordConfirm.editText

        val button = binding.buttonSafe

        lifecycleScope.launch {
            viewModel.isFormValid.collect { valid ->
                if (valid) {

                    button.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.Yellow))
                    button.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.black))
                    button.isEnabled = true
                } else {
                    button.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.Focus_Grey))
                    button.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.Transparent))
                    button.isEnabled = false
                }
            }
        }

        binding.buttonSafe.setOnClickListener {

            var name = binding.nameLayout.editText?.text?.toString().orEmpty().trim()
            var email = binding.mailLayout.editText?.text?.toString().orEmpty().trim()
            var password = binding.password.editText?.text?.toString().orEmpty().trim()
            var passwordConfirm = binding.passwordConfirm.editText?.text?.toString().orEmpty().trim()
            viewModel.onSaveClicked(
                name.toString(), email.toString(),
                password.toString(), passwordConfirm.toString()
            )



        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onFieldsChanged(
                    name?.text?.toString().orEmpty(),
                    email?.text?.toString().orEmpty(),
                    password?.text?.toString().orEmpty(),
                    passwordConfirm?.text?.toString().orEmpty()
                )

            }

            override fun afterTextChanged(s: Editable?) {}
        }
        name?.addTextChangedListener(textWatcher)
        email?.addTextChangedListener(textWatcher)
        password?.addTextChangedListener(textWatcher)
        passwordConfirm?.addTextChangedListener(textWatcher)

    }
}

