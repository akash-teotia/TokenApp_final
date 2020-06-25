package com.sonnetindianetworks.tokenapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashboard.*

class Dashboard : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        auth = FirebaseAuth.getInstance()
        val uid = auth.uid



        button_generate.setOnClickListener {
            startActivity(Intent(this, GenerateToken::class.java))
        }
        button_RequestToken_DashboardActivity.setOnClickListener {
            startActivity(Intent(this, RequestToken::class.java))

        }


    }

    override fun onStart() {
        super.onStart()



        if (auth.currentUser == null) {
            val intent  =Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when(item.itemId){
        R.id.menu_signOut->{
            signOut()
        }




    }


        return super.onOptionsItemSelected(item)


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(R.menu.nav_menu , menu)
        return super.onCreateOptionsMenu(menu)

    }

    private fun signOut() {
        auth.signOut()
        val intent  =Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show()
    }

}
