package com.example.studentflowtrack.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentflowtrack.Model.AttendanceData
import com.example.studentflowtrack.R

class HostelAttendanceAdapter(private var attendanceList: List<AttendanceData>) :
    RecyclerView.Adapter<HostelAttendanceAdapter.AttendanceViewHolder>() {

    class AttendanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewRollNo: TextView = itemView.findViewById(R.id.textViewRollNo)
        val textViewTime: TextView = itemView.findViewById(R.id.textViewTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hostel_attendance, parent, false)
        return AttendanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: AttendanceViewHolder, position: Int) {
        val entry = attendanceList[position]

        holder.textViewRollNo.text = "Name: ${entry.name}"
        holder.textViewTime.text = "Time: ${entry.time}"
    }

    override fun getItemCount(): Int {
        return attendanceList.size
    }

    fun updateData(newData: List<AttendanceData>) {
        attendanceList = newData
        notifyDataSetChanged()
    }

}
