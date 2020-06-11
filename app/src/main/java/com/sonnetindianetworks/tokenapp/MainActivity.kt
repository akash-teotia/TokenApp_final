package com.sonnetindianetworks.tokenapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var profession: EditText
    lateinit var name: EditText
    lateinit var mobile: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
profession =findViewById(R.id.profession_activity_register)
        name =findViewById(R.id.name_activity_register)
        mobile =findViewById(R.id.mobile_text_activity_register)

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



        val email = email_activity_register.text.toString()
        val password = password_activity_register.text.toString()
        if (email.isEmpty()) {
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


                    // startActivity(Intent(this, LoginActivity::class.java))
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Register", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.", Toast.LENGTH_SHORT
                    ).show()
                    // updateUI(null)
                }

                // ...
            }

        ////saving data to Firebase /////
        val profession = profession.text.toString()
        val name = name.text.toString().trim()
/*if (name.isEmpty()){
    name.error = "Please enter the name "
return
}*/

   val mobile = mobile.text.toString().trim()



        val uid = FirebaseAuth.getInstance().uid?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
//        val user = User(uid, profession_activity_register.toString(), name_activity_register.toString() , mobile_text_activity_register.toString())
        val user = User(uid, profession, name , mobile)

        ref.setValue(user).addOnSuccessListener {
            Log.d("RegisterActivity", "Saved user to Database")
        }


    }

}
class User(val uid: String , val name: String ,val profession: String, val mobile: String)

