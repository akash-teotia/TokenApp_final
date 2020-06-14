package com.sonnetindianetworks.tokenapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
            startActivity(Intent(this, PhoneAuth::class.java))
        }

    }


    private fun signUpUser() {
db  = FirebaseFirestore.getInstance()

        val email = email.text.toString()
        val password = password.text.toString()
        val profession = profession.text.toString()
        val name = name.text.toString().trim()
        val mobile = mobile.text.toString()

        val  user = User(name,profession,mobile.toInt(),email, password)
        db.collection("User").document("Org").set(user)

        /* if (email.isEmpty()) {
            email_activity_register.error = "Please enter valid email"
            email_activity_register.requestFocus()

        }
        if (password.isEmpty()) {
            password_activity_register.error = "Please enter valid password"
            password_activity_register.requestFocus()
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Register", "createUserWithEmail:success")


                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Register", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.", Toast.LENGTH_SHORT
                    ).show()
                    // updateUI(null)
                }*/

                // ...
            //}

        ////saving data to Firebase /////


        //val uid = FirebaseAuth.getInstance().uid ?: ""
       /* val ref = FirebaseDatabase.getInstance().getReference("users")
      //val user = User(, profession, name, mobile, email, password)

       // ref.setValue(user).addOnSuccessListener {
            Log.d("RegisterActivity", "Saved user to Database")

        }*/

       /* val intent = Intent(this, PhoneAuth::class.java)
intent.putExtra("Mobile", mobile)
        startActivity(intent)*/

    }

}

data class User(
    val name: String? = null,
    val profession: String? = null,
    val mobile: Int? = null,
    val email: String? = null,
    val password: String? = null
)

