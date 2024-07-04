package com.du4r.mybus.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.du4r.mybus.R
import com.du4r.mybus.models.Line

class LinesAdapter(private val lines: List<Line>): RecyclerView.Adapter<LinesAdapter.LineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_line_list,parent,false)
        return LineViewHolder(view)
    }

    override fun onBindViewHolder(holder: LineViewHolder, position: Int) {
        val line = lines[position]
        holder.linhaNome.text = line.tp
        holder.linhaCodigo.text = line.cl.toString() }

    override fun getItemCount(): Int {
        return lines.size
    }

    inner class LineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val linhaNome: TextView = itemView.findViewById(R.id.line_name)
        val linhaCodigo: TextView = itemView.findViewById(R.id.line_code)
    }
}
