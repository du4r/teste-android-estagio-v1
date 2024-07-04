package com.du4r.mybus.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.du4r.mybus.R
import com.du4r.mybus.models.Stoppage

class StoppagesAdapter(private val stoppages: List<Stoppage>): RecyclerView.Adapter<StoppagesAdapter.StoppageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoppageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stoppage, parent, false)
        return StoppageViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoppageViewHolder, position: Int) {
        val stoppage = stoppages[position]
        holder.stoppageName.text = stoppage.np
        holder.stoppageCode.text = stoppage.cp.toString()
    }

    override fun getItemCount(): Int {
        return stoppages.size
    }

    inner class StoppageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stoppageName: TextView = itemView.findViewById(R.id.stoppage_name)
        val stoppageCode: TextView = itemView.findViewById(R.id.stoppage_code)
    }
}
