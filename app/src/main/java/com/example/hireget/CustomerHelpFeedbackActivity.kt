package com.example.hireget

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.activity_customer_help_feedback.*
import kotlinx.android.synthetic.main.activity_customer_help_feedback.drawerLayout
import kotlinx.android.synthetic.main.activity_customer_help_feedback.navView
import kotlinx.android.synthetic.main.activity_customer_order.*
import kotlinx.android.synthetic.main.activity_customer_profile.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*
import java.util.ArrayList

class CustomerHelpFeedbackActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    fun editProfile (view: View) {

        startActivity(Intent(this, CustomerProfileActivity::class.java))
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    fun raiseIssue(view: View) {

        Snackbar.make(drawerLayout, "Issue raised, We'll notify when it's solved!", Snackbar.LENGTH_LONG).setAction("Dismiss") {

            drawerLayout.setBackgroundColor(Color.parseColor("#f2f2f2"))
        }.show()
        etDescribeIssue.text.clear()

        val inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(drawerLayout.windowToken, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_help_feedback)

        title = "Help & Feedback"
        var ordersArray = arrayOf("Carpentry - 21/ Nov/ 2020", "Electrical - 21/ Dec/ 2019", "Masonry - 2/ Jan/ 2020")
        var customerArrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ordersArray)
        customerSpinnerOrder!!.adapter  = customerArrayAdapter

        var issuesArray = arrayOf("Misbehaved", "Bad Work", "Charged More", "Others")
        customerArrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, issuesArray)
        customerSpinnerIssue!!.adapter  = customerArrayAdapter

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.orders -> {
                    startActivity(Intent(this, CustomerOrderActivity::class.java))
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.helpFeedback -> {
                    startActivity(Intent(this, CustomerHelpFeedbackActivity::class.java))
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.about -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.logout -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
            }
            true
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}