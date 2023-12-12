package com.example.studentflowtrack.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentflowtrack.Model.HistoryData
import com.example.studentflowtrack.R

class HistoryAdapter(private var historyList: List<HistoryData>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewRollNo: TextView = itemView.findViewById(R.id.textViewRollNo)
        val textViewOutTime: TextView = itemView.findViewById(R.id.textViewOutTime)
        val textViewInTime: TextView = itemView.findViewById(R.id.textViewInTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val historyItem = historyList[position]

        holder.textViewRollNo.text = "Roll No: ${historyItem.rollNo}"
        holder.textViewOutTime.text = "Out Time: ${historyItem.outTime}"
        holder.textViewInTime.text = "In Time: ${historyItem.inTime}"
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    fun updateData(newData: List<HistoryData>) {
        historyList = newData
        notifyDataSetChanged()
    }

}
