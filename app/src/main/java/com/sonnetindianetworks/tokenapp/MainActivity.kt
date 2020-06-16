package com.sonnetindianetworks.tokenapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var profession: EditText
    lateinit var name: EditText
    lateinit var mobile: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    var db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        profession = findViewById(R.id.profession_activity_register)
        name = findViewById(R.id.name_activity_register)
        mobile = findViewById(R.id.mobile_text_activity_register)
        email = findViewById(R.id.email_activity_register)
        password = findViewById(R.id.password_activity_register)
        auth = FirebaseAuth.getInstance()


        already_have_account.setOnClickListener {
            Log.d("Login", " Tap already have account ")
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        button_register.setOnClickListener {
            signUpUser()
        }

    }


    private fun signUpUser() {
db  = FirebaseFirestore.getInstance()

        val email = email.text.toString()
        val password = password.text.toString()
        val profession = profession.text.toString()
        val name = name.text.toString().trim()
        val mobile = mobile.text.toString()

        if(email.isEmpty() || password.isEmpty() || profession.isEmpty()|| name.isEmpty()||mobile.isEmpty()){

            Toast.makeText(this,"Enter Valid Details", Toast.LENGTH_SHORT).show()
        }
else {


            val user = User(name, profession, mobile.toInt(), email, password)




            db.collection("User").document(mobile).set(user,SetOptions.merge())

            intent = Intent(this, PhoneAuth::class.java)
            intent.putExtra("mobileNo", mobile)
            startActivity(intent)

        }


    }

}

data class User(
    val name: String? = null,
    val profession: String? = null,
    val mobile: Int? = null,
    val email: String? = null,
    val password: String? = null
)

