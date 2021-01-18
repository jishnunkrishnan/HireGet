package com.example.hireget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class AdminLoginActivity : AppCompatActivity() {

    fun adminLogin(view: View) {

        startActivity(Intent(this, AdminDashboardActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        supportActionBar?.hide()
    }
}