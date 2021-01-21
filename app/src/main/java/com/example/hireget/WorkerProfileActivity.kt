package com.example.hireget

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_worker_profile.*
import kotlinx.android.synthetic.main.activity_worker_profile.workerDrawerLayout
import kotlinx.android.synthetic.main.activity_worker_profile.workerNavView

class WorkerProfileActivity : AppCompatActivity() {

    lateinit var workerToggle: ActionBarDrawerToggle
    var loginWindow: Boolean = false

    var mapBoolean = false

    fun login(view: View) {
        startActivity(Intent(this, WorkerDashboardActivity::class.java))
    }

    fun regOrLog(view: View) {

        if (loginWindow) {

            llLogin.visibility = View.VISIBLE
            rlRegister.visibility = View.GONE
            loginWindow = false
        } else {

            llLogin.visibility = View.GONE
            rlRegister.visibility = View.VISIBLE
            loginWindow = true
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker_profile)

        title = "Login/ Register"
        loginWindow = true

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.workerBlue)))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.workerBlueStatusBar)
        }

        workerToggle = ActionBarDrawerToggle(this, workerDrawerLayout, R.string.open, R.string.close)
        workerDrawerLayout.addDrawerListener(workerToggle)
        workerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        buttonWorkerLocation.setOnClickListener {

            startActivity(Intent(this, WorkerMapActivity::class.java))
        }

        val intent = getIntent()
        val workerLocSubCity = intent.getStringExtra("locSubCity")
        val workerLocCity = intent.getStringExtra("locCity")
        val workerLocCountry = intent.getStringExtra("locCountry")

        Log.i("HelloW", workerLocSubCity.toString())
        Log.i("HelloL", workerLocCity.toString())
        Log.i("HelloLC", workerLocCountry.toString())
        val regWindow = intent.getBooleanExtra("regWindow", false)
        Log.i("Hello", regWindow.toString())
        if(regWindow) {

            //tvWorkerLocation.text = workerLocation
            loginWindow = false
            llLogin.visibility = View.GONE
            rlRegister.visibility = View.VISIBLE
            tvWorkerLocation.text = "$workerLocSubCity, $workerLocCity, $workerLocCountry"
        }

        workerNavView.setNavigationItemSelectedListener {

            when(it.itemId) {
                R.id.dashboard -> {
                    startActivity(Intent(this, WorkerDashboardActivity::class.java))
                    workerDrawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.requests -> {
                    startActivity(Intent(this, WorkerRequestsActivity::class.java))
                    workerDrawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.pastRequests -> {
                    startActivity(Intent(this, WorkerPastRequestActivity::class.java))
                    workerDrawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.helpFeedback -> {
                    startActivity(Intent(this, CustomerHelpFeedbackActivity::class.java))
                    workerDrawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.logout -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    workerDrawerLayout.closeDrawer(GravityCompat.START)
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (workerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}