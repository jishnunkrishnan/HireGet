package com.example.hireget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_admin_add_customer.*
import kotlinx.android.synthetic.main.activity_admin_issues.*
import kotlinx.android.synthetic.main.activity_admin_issues.adminDrawerLayout
import kotlinx.android.synthetic.main.activity_admin_issues.adminNavView

class AdminIssuesActivity : AppCompatActivity() {

    lateinit var adminToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_issues)

        title = "Issues"

        adminToggle = ActionBarDrawerToggle(this, adminDrawerLayout, R.string.open, R.string.close)
        adminDrawerLayout.addDrawerListener(adminToggle)
        adminToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adminNavView.setNavigationItemSelectedListener {

            when (it.itemId) {

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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
          if(adminToggle.onOptionsItemSelected(item)) {
             return true
          }
        return super.onOptionsItemSelected(item)
    }
}