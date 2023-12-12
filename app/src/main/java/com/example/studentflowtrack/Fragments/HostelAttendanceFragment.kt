package com.example.studentflowtrack.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentflowtrack.Adapters.HostelAttendanceAdapter
import com.example.studentflowtrack.Model.AttendanceData
import com.example.studentflowtrack.R
import com.example.studentflowtrack.databinding.FragmentHostelAttendanceBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class HostelAttendanceFragment : Fragment() {

    private lateinit var binding: FragmentHostelAttendanceBinding
    private lateinit var hostelAttendanceAdapter: HostelAttendanceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHostelAttendanceBinding.inflate(inflater, container, false)
        val view = binding.root

        setupRecyclerView(view)

        binding.fabAdd.setOnClickListener {
            showAddDialog()
        }

        return view
    }

    private fun setupRecyclerView(view: View) {
        hostelAttendanceAdapter = HostelAttendanceAdapter(emptyList())

        val recyclerView: RecyclerView = view.findViewById(R.id.hostelAttendanceRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = hostelAttendanceAdapter

        fetchDataAndUpdateAdapter()
    }

    private fun fetchDataAndUpdateAdapter() {
        var data = mutableListOf<AttendanceData>()

        FirebaseFirestore.getInstance().collection("Hostel").get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                if (task.isSuccessful) {
                    val result: QuerySnapshot? = task.result
                    if (result != null && !result.isEmpty) {
                        for (document in result) {
                            val name = document.getString("name") ?: ""
                            val time = document.getTimestamp("time")?.toString() ?: ""
                            Log.d("HostelAttendance", name)
                            data.add(AttendanceData(name, time))
                        }

                        hostelAttendanceAdapter.updateData(data)
                        Log.d("FirestoreSuccess", "Data loaded successfully")
                    }
                } else {
                    val exception: Exception? = task.exception
                    Log.e("FirestoreError", "Error fetching data", exception)
                }
            })
    }

    private fun showAddDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Clear All?")
        alertDialog.setMessage("Click yes to reset the attendance page")
        alertDialog.setPositiveButton("Delete") { _, _ ->
            FirebaseFirestore.getInstance().collection("Hostel").get()
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            FirebaseFirestore.getInstance().collection("Hostel").document(document.id).delete()
                        }
                        Log.d("FirestoreSuccess", "All documents deleted from the collection.")
                    } else {
                        Log.e("FirestoreError", "Error deleting documents", task.exception)
                    }
                })
        }
        alertDialog.setNegativeButton("Cancel") { _, _ ->
            // Handle negative button click
        }
        alertDialog.show()
    }
}
