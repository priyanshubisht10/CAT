package com.example.studentflowtrack.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentflowtrack.Model.OpenEntries
import com.example.studentflowtrack.R

class OpenEntriesAdapter(private var openEntriesList: List<OpenEntries>) :
    RecyclerView.Adapter<OpenEntriesAdapter.OpenEntriesViewHolder>() {

    class OpenEntriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewRollNo: TextView = itemView.findViewById(R.id.textViewRollNo)
        val textViewOutTime: TextView = itemView.findViewById(R.id.textViewOutTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpenEntriesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_open_entry, parent, false)
        return OpenEntriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: OpenEntriesViewHolder, position: Int) {

        val entry = openEntriesList[position]

        holder.textViewRollNo.text = "Roll No: ${entry.rollNo}"
        holder.textViewOutTime.text = "Out Time: ${entry.outTime}"
    }

    override fun getItemCount(): Int {
        return openEntriesList.size
    }

    fun updateData(newData: List<OpenEntries>) {
        openEntriesList = newData
        notifyDataSetChanged()
    }
}
