package com.example.hireget

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_customer_help_feedback.*
import kotlinx.android.synthetic.main.activity_worker_dashboard.*
import kotlinx.android.synthetic.main.activity_worker_profile.*
import kotlinx.android.synthetic.main.activity_worker_profile.workerDrawerLayout
import kotlinx.android.synthetic.main.activity_worker_profile.workerNavView
import kotlinx.android.synthetic.main.activity_worker_requests.*

class WorkerProfileActivity : AppCompatActivity() {

    lateinit var workerToggle: ActionBarDrawerToggle
    var loginWindow: Boolean = false

    fun login(view: View) {
        startActivity(Intent(this, WorkerDashboardActivity::class.java))
    }

    fun loginHere(view: View) {
        loginWindow = true
        llLogin.visibility = View.VISIBLE
        rlRegister.visibility = View.GONE
    }

    fun registerHere(view: View) {

        loginWindow = false
        llLogin.visibility = View.GONE
        rlRegister.visibility = View.VISIBLE
    }

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