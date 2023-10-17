package com.example.navian

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity()
{
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Create an instance of FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Variables
        val edtxEmail = findViewById<EditText>(R.id.login_username_input)
        val edtxPassword = findViewById<EditText>(R.id.login_password_input)

        // Login
        val navHome = findViewById<Button>(R.id.confirm_login_button)

        navHome.setOnClickListener()
        {
            val email = edtxEmail.text.toString()
            val password = edtxPassword.text.toString()

            handleLogin(email, password)
        }

        // Navigate to Register
        val navRegister = findViewById<Button>(R.id.nav_register_button)

        navRegister.setOnClickListener()
        {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun handleLogin(email: String, password: String)
    {
        // Debug Login
        if (email == "0" && password == "0")
        {
            // Navigate to Home
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        // --- Login Logic Here ---
        if(email.isEmpty())
        { Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show()}

        if(password.isEmpty())
        { Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show() }

        // Sign In
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this)
        { task ->
            if (task.isSuccessful)
            {
                // Sign in success, update UI with the signed-in user's information
                //Log.d(TAG, "signInWithEmail:success")
                //val user = auth.currentUser
                Toast.makeText(baseContext,"Authentication successful.", Toast.LENGTH_SHORT).show()
                // Navigate to Home
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
                //updateUI(user)
            }
            else
            {
                // If sign in fails, display a message to the user.
                //Log.w(TAG, "signInWithEmail:failure", task.exception)
                Toast.makeText(baseContext,"Authentication failed.", Toast.LENGTH_SHORT).show()
                //updateUI(null)
            }
        }
    }
}