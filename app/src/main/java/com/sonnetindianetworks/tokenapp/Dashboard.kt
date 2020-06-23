package com.sonnetindianetworks.tokenapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashboard.*

class Dashboard : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
 auth = FirebaseAuth.getInstance()
        val credential = EmailAuthProvider.getCredential(email.toString(), password.toString())
        auth.currentUser!!.linkWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Linking Successful",
                        Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(baseContext, "Linking failed.",
                        Toast.LENGTH_SHORT).show()
                }

                // ...
            }


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
