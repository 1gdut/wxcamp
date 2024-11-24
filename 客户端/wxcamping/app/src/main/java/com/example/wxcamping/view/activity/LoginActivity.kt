package com.example.wxcamping.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.wxcamping.R
import com.example.wxcamping.databinding.ActivityLoginBinding
import com.example.wxcamping.databinding.ActivityRegisterBinding
import com.example.wxcamping.model.database.UserDatabase
import com.example.wxcamping.viewmodel.LoginViewModel
import com.example.wxcamping.viewmodel.RegisterViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    lateinit var userDatabase: UserDatabase

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.lifecycleOwner = this
        binding.loginViewModel = viewModel
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login_view)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        userDatabase = UserDatabase.getDatabase(this)
        binding.loginFinish.setOnClickListener {
            viewModel.login()
        }
        viewModel.result.observe(this) {
            if (it) {
                val intent = Intent(this, MainViewActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}