package com.sonnetindianetworks.tokenapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashboard.*

class Dashboard : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
 auth = FirebaseAuth.getInstance()
 textView_signOut.setOnClickListener {
    signOut()
 }

    }
    override  fun onStart() {
        super.onStart()
        if (auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
    private  fun signOut()
    {
        auth.signOut()
        startActivity(Intent(this , LoginActivity::class.java))
        Toast.makeText(this, "Logout Successfully" , Toast.LENGTH_SHORT).show()
    }
}
