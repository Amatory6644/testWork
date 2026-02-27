package com.example.testwork.presentation.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
        binding.editName.setText(viewModel.name)
        binding.editMail.setText(viewModel.email)
        binding.editPassword.setText(viewModel.password)
        binding.editPasswordConfirm.setText(viewModel.passwordConfirm)
        collectUI(binding)
        setupForm(binding)
    }

    private fun collectUI(binding: AuthBinding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.toastFlow.collect { msg ->
                        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
                    }
                }
                launch {
                    viewModel.isFormValid.collect { valid ->
                        binding.buttonSafe.isSelected = valid

                    }
                }
            }
        }
    }

    private fun setupForm(binding: AuthBinding) = with(binding) {
        val name = nameLayout.editText
        val email = mailLayout.editText
        val password = password.editText
        val passwordConfirm = passwordConfirm.editText

        buttonSafe.setOnClickListener {
            viewModel.onSaveClicked(
                name?.text?.toString().orEmpty().trim(),
                email?.text?.toString().orEmpty().trim(),
                password?.text?.toString().orEmpty(),
                passwordConfirm?.text?.toString().orEmpty()
            )
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onFieldsChanged(
                    name?.text?.toString().orEmpty(),
                    email?.text?.toString().orEmpty(),
                    password?.text?.toString().orEmpty(),
                    passwordConfirm?.text?.toString().orEmpty()
                )

            }

        }

        name?.addTextChangedListener(textWatcher)
        email?.addTextChangedListener(textWatcher)
        password?.addTextChangedListener(textWatcher)
        passwordConfirm?.addTextChangedListener(textWatcher)

    }


}


