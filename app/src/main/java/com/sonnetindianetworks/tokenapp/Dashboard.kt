package com.sonnetindianetworks.tokenapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
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

    //private var IssueTokenList: List<DashTokenIssueModal> = ArrayList()
    var mobile: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        auth = FirebaseAuth.getInstance()
        val mobileNo = intent.getStringExtra("MOBILE")
        if (mobileNo == null) {
            Toast.makeText(this, "Null value for mobile", Toast.LENGTH_SHORT).show()

        } else {
            mobile = mobileNo
            Toast.makeText(this, "+61$mobile", Toast.LENGTH_SHORT).show()
        }

        val adapter = GroupAdapter<GroupieViewHolder>()
        recyclerView_DashboardActivity.adapter = adapter


        fetchTokens()








        button_generate.setOnClickListener {
            startActivity(Intent(this, GenerateToken::class.java))
        }
        button_RequestToken_DashboardActivity.setOnClickListener {
            startActivity(Intent(this, RequestToken::class.java))

        }


    }

    private fun fetchTokens() {
        /*val db = FirebaseFirestore.getInstance()
        val fireStoreTokens =
            db.collection("UserDetails").document("+61$mobile").collection("IssuedToken")
        fireStoreTokens.get()
            .addOnSuccessListener { result ->
                val adapter = GroupAdapter<GroupieViewHolder>()
                recyclerView_DashboardActivity.adapter = adapter

                for (document in result) {
                    Log.d("token", "${document.id} => ${document.data}")
                    val tokens = result!!.toObjects(DashTokenIssueModal::class.java)
                    if (tokens != null) {
                        adapter.add(IssuedTokens())
                    }
                    Log.d("re", tokens.toString())

                }
            }
            .addOnFailureListener { exception ->
                Log.d("token", "Error getting documents: ", exception)
            }


    }*/
        val db = FirebaseFirestore.getInstance()
        val fireStoreTokens =
            db.collection("UserDetails").document("+61$mobile").collection("IssuedToken")
        fireStoreTokens.addSnapshotListener{ snap ,e ->
            if (e != null) {
                Log.w("token", "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snap != null && snap.exists) {

            }


                else {
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

class IssuedTokens() : Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.recycleview_dashboard
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

    }

}


data class DashTokenIssueModal(
    val tokenNo: String,
    val issuedBy: String,
    val date: String
) {
constructor(): this("", "" , "")

}
