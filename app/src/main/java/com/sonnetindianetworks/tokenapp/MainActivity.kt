package com.sonnetindianetworks.tokenapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var profession: EditText
    lateinit var name: EditText
    lateinit var mobile: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    private lateinit var storedVerificationId: String


    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

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
auth = FirebaseAuth.getInstance()
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


sendVerificationCodeToUser()
            intent = Intent(this, PhoneAuth::class.java)

            startActivity(intent)

        }


    }

    private fun sendVerificationCodeToUser() {
        verificationCallbacks()
        val mobile = mobile.text.toString()



        val phoneNo = mobile


//        Toast.makeText(this, phoneNo, Toast.LENGTH_LONG).show()

        PhoneAuthProvider.getInstance().verifyPhoneNumber(

            "+61$phoneNo", // Phone number to verify
            60, // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            TaskExecutors.MAIN_THREAD, // Activity (for callback binding)
            callbacks
        ) // OnVerificationStateChangedCallbacks

    }

    private fun verificationCallbacks() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                //Log.d(TAG, "onVerificationCompleted:$credential")

                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                //   Log.w(TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

                // Show a message and update the UI
                // ...
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
//            Log.d(TAG, "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token

                // ...
            }
        }

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    // Log.d(TAG, "signInWithCredential:success")

                    Toast.makeText(this, "Logged in Successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, Dashboard::class.java))
                    //  val user = task.result?.user
                    // ...
                } else {
                    // Sign in failed, display a message and update the UI
                    //Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(
                            this,
                            "verification code entered was invalid",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
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

