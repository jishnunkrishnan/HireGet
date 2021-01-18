package com.example.hireget

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_customer_help_feedback.*
import kotlinx.android.synthetic.main.activity_hire_worker.*
import kotlinx.android.synthetic.main.activity_hire_worker.drawerLayout
import kotlinx.android.synthetic.main.activity_hire_worker.navView
import java.util.*

class HireWorkerActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    fun hireWorker(view: View) {

        Snackbar.make(drawerLayout, "Worker will be arranged soon, you can track details on orders page!", Snackbar.LENGTH_LONG).setAction("Dismiss") {

            drawerLayout.setBackgroundColor(Color.parseColor("#f2f2f2"))
        }.show()

        startActivity(Intent(this, MapsActivity::class.java))
    }

    fun getDate (view: View) {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            lateinit var month: String
            when (monthOfYear) {
                0,1,2,3,4,5,6,7,8,9 -> month = "0" + (monthOfYear +1).toString()
                10 -> month = (monthOfYear + 1).toString()
                11 -> month = (monthOfYear + 1).toString()
            }

            tvDate.setText("Date: $dayOfMonth/ $month/ $year")

        }, year, month, day)

        dpd.show()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hire_worker)

        title = "Hire Worker"

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

        var intent = intent
        var imageResource = intent.getIntExtra("image",0)
        var text = intent.getStringExtra("text")

        if (imageResource != null) {
            ivHireWorker.setImageResource(imageResource)
        }
        tvWorkCategory.text = text

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}