package com.example.hireget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LoginActivity : AppCompatActivity() {

    fun customerLogin(view: View) {

        startActivity(Intent(this, CustomerLoginActivity::class.java))
    }

    fun seekerLogin(view: View) {

        startActivity(Intent(this, WorkerProfileActivity::class.java))
    }

    fun adminLogin(view: View) {

        startActivity(Intent(this, AdminLoginActivity::class.java))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
    }
}