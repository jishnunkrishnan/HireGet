package com.example.hireget

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header.view.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), ExampleAdapter.OnItemCLickListener {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var exampleList: List<ExampleItem>
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    fun editProfile(view: View) {

        startActivity(Intent(this, CustomerProfileActivity::class.java))
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    fun joinHgPartner(view: View) {

        startActivity(Intent(this, WorkerProfileActivity::class.java))
//        rlRegister.visibility = View.VISIBLE
//        llLogin.visibility = View.GONE
    }

    private fun generateDummyList(size: Int): List<ExampleItem> {

        val list = ArrayList<ExampleItem>()
        for (i in 0 until size) {

            var drawable by Delegates.notNull<Int>()
            lateinit var work: String
            when (i) {
                0 -> {
                    drawable = R.drawable.carpenter
                    work = "Carpenter"
                }
                1 -> {
                    drawable = R.drawable.cctv
                    work = "CCTV Technician"
                }
                2 -> {
                    drawable = R.drawable.electrician
                    work = "Electrician"
                }
                3 -> {
                    drawable = R.drawable.masonary
                    work = "Masonry"
                }
                4 -> {
                    drawable = R.drawable.mechanic
                    work = "Mechanic"
                }
                5 -> {
                    drawable = R.drawable.painter
                    work = "Painter"
                }
                6 -> {
                    drawable = R.drawable.plumber
                    work = "Plumber"
                }
            }
            val item = ExampleItem(drawable, work)
            list += item
        }
        return list
    }


    private fun signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, OnCompleteListener<Void?> {
                    // ...
                })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            val personName = acct.displayName
            val personGivenName = acct.givenName
            val personFamilyName = acct.familyName
            val personEmail = acct.email
            val personId = acct.id
            val personPhoto: Uri? = acct.photoUrl

            //tvMenuName?.text = personName.toString()
            //tvMenuEmail?.text = personEmail.toString()

            val headerView = navView.getHeaderView(0)

            headerView.tvMenuName.text = personName
            headerView.tvMenuEmail.text = personEmail

            Log.i("personName", personName.toString())
            Log.i("personGivenName", personGivenName.toString())
            Log.i("personFamilyName", personFamilyName.toString())
            Log.i("personEmail", personEmail.toString())
            Log.i("personId", personId.toString())
            Log.i("personPhoto", personPhoto.toString())
        }

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
                    signOut()
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
            }
            true
        }

        exampleList = generateDummyList(7)
        recyclerView.adapter = ExampleAdapter(exampleList, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(position: Int) {

        var example = exampleList[position]
        var image = example.imageResource
        var text = example.text1

        val intent = Intent(this, HireWorkerActivity::class.java)
        intent.putExtra("image", image)
        intent.putExtra("text", text)
        startActivity(intent)
    }
}