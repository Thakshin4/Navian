package com.example.navian

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Create an instance of FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Variables
        val edtxEmail = findViewById<EditText>(R.id.register_username_input)
        val edtxPassword = findViewById<EditText>(R.id.register_password_input)

        // Register
        val navHome = findViewById<Button>(R.id.register_confirm_button)

        navHome.setOnClickListener()
        {
            val email = edtxEmail.text.toString()
            val password = edtxPassword.text.toString()

            if (email.isEmpty() || password.isEmpty())
            {
                Toast.makeText(applicationContext,
                    "Username and Password cannot be Empty.",
                    Toast.LENGTH_SHORT).show()
            }
            else
            {
                handleRegister(email, password)
            }
        }

        // Navigate to Login
        val navLogin = findViewById<Button>(R.id.nav_login_button)

        navLogin.setOnClickListener()
        {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Handle User Registration
    private fun handleRegister(email: String, password: String)
    {
        // --- Register Logic Here ---
        // Validation
        if(email.isEmpty())
        { Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show()}

        if(password.isEmpty())
        { Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show() }

        if(!validatePassword(password))
        { Toast.makeText(this,
            "\"Invalid password. Please enter a password with at least 8 characters, containing at least one number and one special character.\"",
            Toast.LENGTH_SHORT).show() }

        // Create User
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this)
        { task ->
            if (task.isSuccessful)
            {
                // Sign in success, update UI with the signed-in user's information
                //Log.d(TAG, "createUserWithEmail:success")
                //val user = auth.currentUser
                Toast.makeText(baseContext,"Authentication successful.", Toast.LENGTH_SHORT).show()
                //updateUI(user)
            }
            else
            {
                // If sign in fails, display a message to the user.
                //Log.w(TAG, "createUserWithEmail:failure", task.exception)
                Toast.makeText(baseContext,"Authentication failed.", Toast.LENGTH_SHORT).show()
                //updateUI(null)
            }
        }
    }

    // Functions
    private fun validatePassword(password: String): Boolean
    {
        var valid = true

        if( password.length < 9 ||
            password.none { it.isDigit() } ||
            password.none { it.isLetterOrDigit().not() })
        { valid = false; }

        return valid
    }
}