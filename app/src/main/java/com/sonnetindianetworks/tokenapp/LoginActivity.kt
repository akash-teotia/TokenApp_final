package com.sonnetindianetworks.tokenapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.TaskExecutors
import com.google.common.collect.MapMaker
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit

lateinit var email: EditText
lateinit var password: EditText
lateinit var auth: FirebaseAuth

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        email = findViewById(R.id.email_LoginActivity)
        password = findViewById(R.id.password_LoginActivity)
        auth = FirebaseAuth.getInstance()

        register_login.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        login_button.setOnClickListener {
            signIn()
        }
    }
    private fun signIn(){
        val email = email.text.toString()
        val password = password.text.toString()
        Toast.makeText(baseContext, password,Toast.LENGTH_SHORT).show()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(baseContext, "Authentication Successfully",Toast.LENGTH_SHORT).show()
startActivity(Intent(this, Dashboard::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                   /* Toast.makeText(baseContext, " Email Authentication failed ",
                        Toast.LENGTH_SHORT).show()*/
                }

                // ...
            }
        /*auth.signInWithEmailAndPassword(email, password.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this, "Authentication successfully",
                        Toast.LENGTH_SHORT).show()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    // ...
                }

                // ...
            }*/
    }
}
