package com.example.hireget

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_customer_help_feedback.*
import kotlinx.android.synthetic.main.activity_worker_dashboard.*
import kotlinx.android.synthetic.main.activity_worker_help_feedback.*
import kotlinx.android.synthetic.main.activity_worker_help_feedback.customerSpinnerIssue
import kotlinx.android.synthetic.main.activity_worker_help_feedback.customerSpinnerOrder
import kotlinx.android.synthetic.main.activity_worker_help_feedback.workerDrawerLayout
import kotlinx.android.synthetic.main.activity_worker_help_feedback.workerNavView

class WorkerHelpFeedbackActivity : AppCompatActivity() {

    lateinit var workerToggle: ActionBarDrawerToggle

    fun raiseIssue(view: View) {

        Snackbar.make(workerDrawerLayout, "Issue raised, We'll notify when it's solved!", Snackbar.LENGTH_LONG).setAction("Dismiss") {

            workerDrawerLayout.setBackgroundColor(Color.parseColor("#f2f2f2"))
        }.show()
        etDescribeIssueWorker.text.clear()

        val inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(workerDrawerLayout.windowToken, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker_help_feedback)

        title = "Help & Feedback"

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.workerBlue)))
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

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
                    startActivity(Intent(this, WorkerHelpFeedbackActivity::class.java))
                    workerDrawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.logout -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    workerDrawerLayout.closeDrawer(GravityCompat.START)
                }
            }
            true
        }

        var ordersArray = arrayOf("Carpentry - 21/ Nov/ 2020", "Electrical - 21/ Dec/ 2019", "Masonry - 2/ Jan/ 2020")
        var customerArrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ordersArray)
        customerSpinnerOrder!!.adapter  = customerArrayAdapter

        var issuesArray = arrayOf("Misbehaved", "Customer didn't paid", "Charged Less", "Others")
        customerArrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, issuesArray)
        customerSpinnerIssue!!.adapter  = customerArrayAdapter

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (workerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}