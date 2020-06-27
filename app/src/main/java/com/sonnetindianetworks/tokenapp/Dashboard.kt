package com.sonnetindianetworks.tokenapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_dashboard.*

class Dashboard : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    private var IssueTokenList: List<DashTokenIssueModal> = ArrayList()

    private val adapterIssueToken: AdapterIssueToken = AdapterIssueToken(IssueTokenList)
    var mobile: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        auth = FirebaseAuth.getInstance()
        val mobileNo = intent.getStringExtra("MOBILE")


        if (mobileNo == null) {
            Toast.makeText(this, "Null value for mobile", Toast.LENGTH_SHORT).show()
            signOut()

        } else {
            mobile = mobileNo
            Toast.makeText(this, mobile, Toast.LENGTH_SHORT).show()
        }





        fetchTokens()


        recyclerView_DashboardActivity.layoutManager = LinearLayoutManager(this)
        recyclerView_DashboardActivity.adapter = adapterIssueToken





        button_generate.setOnClickListener {
            startActivity(Intent(this, GenerateToken::class.java))
        }
        button_RequestToken_DashboardActivity.setOnClickListener {
            startActivity(Intent(this, RequestToken::class.java))

        }


    }

    private fun fetchTokens() {
        val dataMobile = mobile
        if (dataMobile.isEmpty()){
            return signOut()
        }
        
        val db = FirebaseFirestore.getInstance()
        val fireStoreTokens =  db.collection("UserDetails").document(dataMobile).collection("IssuedToken")



        fireStoreTokens.addSnapshotListener { snap, e ->
            if (e != null) {
                Log.w("token", "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snap != null) {
//                Log.d("token", "Current data: ${snap.documents}")

                for ( doc in snap) {
                    IssueTokenList = snap.toObjects(DashTokenIssueModal::class.java)

                    adapterIssueToken.IssuedTokens = IssueTokenList
                    adapterIssueToken.notifyDataSetChanged()
                    Log.d("token", "Current data: $IssueTokenList")

                }


            } else {
                Log.d("token", "Current data: null")
            }

        }


    }

    override fun onStart() {
        super.onStart()



        if (auth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_signOut -> {
                signOut()
            }

        }


        return super.onOptionsItemSelected(item)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    private fun signOut() {
        auth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show()
    }

}




