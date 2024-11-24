package com.example.wxcamping.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wxcamping.R

class WelcomeActivity : AppCompatActivity() {
    lateinit var welcome_register: Button
    lateinit var welcome_login: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        welcome_register = findViewById(R.id.welcome_register)
        welcome_login = findViewById(R.id.welcome_login)
        welcome_register.setOnClickListener {
            //跳转到注册页面
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        welcome_login.setOnClickListener {
            //跳转到登录页面
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}