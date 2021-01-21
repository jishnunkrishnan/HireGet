package com.example.hireget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_customer_help_feedback.*

class CartActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        title = "Cart"

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