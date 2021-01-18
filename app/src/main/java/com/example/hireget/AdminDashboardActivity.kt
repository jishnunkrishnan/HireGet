package com.example.hireget

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_admin_dashboard.*

class AdminDashboardActivity : AppCompatActivity() {

    lateinit var adminToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        title = "Dashboard"

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.adminGray)))
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.adminGrayStatusBar)
        }

        adminToggle = ActionBarDrawerToggle(this, adminDrawerLayout, R.string.open, R.string.close)
        adminDrawerLayout.addDrawerListener(adminToggle)
        adminToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adminNavView.setNavigationItemSelectedListener {
            when(it.itemId) {

                R.id.dashboard -> {
                    startActivity(Intent(this, AdminDashboardActivity::class.java))
                    adminDrawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.addNewCustomer -> {
                    startActivity(Intent(this, AdminAddCustomerActivity::class.java))
                    adminDrawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.addNewWorker -> {
                    startActivity(Intent(this, AdminAddWorkerActivity::class.java))
                    adminDrawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.onGoingWorks -> {
                    startActivity(Intent(this, AdminOnGoingActivity::class.java))
                    adminDrawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.pastWorks -> {
                    startActivity(Intent(this, AdminPastWorksActivity::class.java))
                    adminDrawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.issues -> {
                    startActivity(Intent(this, AdminIssuesActivity::class.java))
                    adminDrawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.logout -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    adminDrawerLayout.closeDrawer(GravityCompat.START)
                }
            }
            true
        }

        val arrayListWorkers = arrayOf("John Doe", "Jane Doe", "Rakesh Rao", "Atlee Mishra")
        var arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayListWorkers)
        listViewWorkers.adapter = arrayAdapter

        val arrayListUsers = arrayOf("Adam John", "Riya Mary", "Karlose Antony", "Ravi Rathnan")
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayListUsers)
        listViewUsers.adapter = arrayAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(adminToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}