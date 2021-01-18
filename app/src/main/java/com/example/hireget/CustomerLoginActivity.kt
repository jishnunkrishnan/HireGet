package com.example.hireget

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_customer_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CustomerLoginActivity : AppCompatActivity() {

    private var RC_SIGN_IN: Int = 1
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private fun signIn() {

        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
        Log.i("hey", "heyyd")
    }

     private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        Log.i("helllo", completedTask.toString())
        try {
            val account = completedTask.getResult(ApiException::class.java)!!

            // Signed in successfully, show authenticated UI.
//            updateUI(account)
            Log.i("hello", account.toString())
            startActivity(Intent(this, MainActivity::class.java))
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Tholvi", "signInResult:failed code=" + e.statusCode)
//            updateUI(null)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
                Log.i("hey", "heyy")
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            GlobalScope.launch {
                handleSignInResult(task)
                Log.i("hey", "heyy")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_login)

        signInButton.setOnClickListener {

            when (it.id) {
                R.id.signInButton -> signIn()
            }
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        //signInButton.setSize(SignInButton.SIZE_STANDARD)
    }

    override fun onStart() {
        super.onStart()

        val account = GoogleSignIn.getLastSignedInAccount(this)
            //updateUI(account)
        if (account != null) {
            signIn()
        }
        Log.i("Onstart", account.toString())
    }
}