package com.example.studentflowtrack.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentflowtrack.Adapters.HistoryAdapter
import com.example.studentflowtrack.Model.HistoryData
import com.example.studentflowtrack.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class HistoryFragment : Fragment() {

    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        setupRecyclerView(view)

        return view
    }

    private fun setupRecyclerView(view: View) {
        historyAdapter = HistoryAdapter(emptyList())

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewHistory)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = historyAdapter

        fetchDataAndUpdateAdapter()
    }

    private fun fetchDataAndUpdateAdapter() {
        val data = mutableListOf<HistoryData>()

        FirebaseFirestore.getInstance().collection("History").get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                if (task.isSuccessful) {
                    val result: QuerySnapshot? = task.result
                    if (result != null && !result.isEmpty) {
                        for (document in result) {

                            val rollNo = document.getString("rollNo") ?: ""
                            val outTime = document.getTimestamp("outTime")?.toString() ?: ""
                            val inTime = document.getTimestamp("inTime")?.toString() ?: "-"

                            Log.d("HistoryFragment", rollNo)

                            data.add(HistoryData(rollNo, outTime, inTime))
                        }

                        historyAdapter.updateData(data)
                        Log.d("FirestoreSuccess", "Data loaded successfully")
                    }
                } else {
                    val exception: Exception? = task.exception
                    Log.e("FirestoreError", "Error fetching data", exception)
                }
            })

        historyAdapter.updateData(data)
    }
}
