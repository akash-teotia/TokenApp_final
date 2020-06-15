package com.sonnetindianetworks.tokenapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText

import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_generate_token.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_phone_auth.*
import java.util.concurrent.TimeUnit

class PhoneAuth : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    lateinit  var  mobile: EditText
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_auth)
        mobile = findViewById(R.id.verify_mobile_text_activity)

        auth = FirebaseAuth.getInstance()

        verify_button.setOnClickListener {


            verify()
        }
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
                  //  Log.d("Phone Authentication", "onVerificationCompleted:$credential")

                    signInWithPhoneAuthCredential(credential)
                }

            override fun onVerificationFailed(p0: FirebaseException) {

            }


            override fun onCodeSent(
                   verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    // The SMS verification code has been sent to the provided phone number, we
                    // now need to ask the user to enter the code and then construct a credential
                    // by combining the code with a verification ID.
                    Log.d("Phone Authentication", "onCodeSent:$verificationId")

                    // Save verification ID and resending token so we can use them later
                    storedVerificationId = verificationId
                    resendToken = token

                    // ...
                }


        }
    }

    /*override fun onStart() {
        super.onStart()
        if (auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            Toast.makeText(this, "Already SignedIn ", Toast.LENGTH_SHORT).show()
        }


    }*/
    val mobileTest = "+61452330164"

    private fun verify() {
        verificationCallbacks()
     //   val mobile = mobile.toString()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            mobileTest, // Phone number to verify
            60, // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            this, // Activity (for callback binding)
            callbacks
        ) // OnVerificationStateChangedCallback
    }




    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Phone Authentication", "signInWithCredential:success")

                   // val user = task.result?.user
                    Toast.makeText(this,"Login Successfully", Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this, Dashboard::class.java))
                    // ...
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("Phone Authentication", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(
                            this,
                            "The verification code entered was invalid",
                            Toast.LENGTH_SHORT
                        ).show()
// The verification code entered was invalid
                    }
                }
            }
    }
}
