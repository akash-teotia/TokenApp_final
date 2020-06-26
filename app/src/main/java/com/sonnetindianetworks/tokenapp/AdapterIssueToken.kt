package com.sonnetindianetworks.tokenapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycleview_dashboard.view.*

class AdapterIssueToken(var IssuedTokens: List<DashTokenIssueModal>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class DeskView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(issueTokens: DashTokenIssueModal) {
            itemView.recycleView_DashBoard_tokenNo.text = DashTokenIssueModal().tokenNo
itemView.recycleView_DashBoard_issuedBy!!.text = DashTokenIssueModal().issuedBy
            itemView.recycleView_DashBoard_date.text = DashTokenIssueModal().date
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycleview_dashboard, parent, false)
        return DeskView(view)
    }

    override fun getItemCount(): Int {
        return IssuedTokens.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DeskView).bind(IssuedTokens[position])
    }
}