package com.example.studentflowtrack.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentflowtrack.Model.ClosedEntries
import com.example.studentflowtrack.R

class ClosedEntriesAdapter(private var closedEntriesList: List<ClosedEntries>) :
    RecyclerView.Adapter<ClosedEntriesAdapter.ClosedEntriesViewHolder>() {

    class ClosedEntriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewRollNo)
        val textViewInTime: TextView = itemView.findViewById(R.id.textViewInTime)
        val textViewOutTime: TextView = itemView.findViewById(R.id.textViewOutTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClosedEntriesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_closed_entry, parent, false)
        return ClosedEntriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClosedEntriesViewHolder, position: Int) {

        val entry = closedEntriesList[position]

        holder.textViewName.text = "Name: ${entry.rollNo}"
        holder.textViewInTime.text = "In Time: ${entry.inTime}"
        holder.textViewOutTime.text = "Out Time: ${entry.outTime}"
    }

    override fun getItemCount(): Int {
        return closedEntriesList.size
    }

    fun updateData(newData: List<ClosedEntries>) {
        closedEntriesList = newData
        notifyDataSetChanged()
    }
}
