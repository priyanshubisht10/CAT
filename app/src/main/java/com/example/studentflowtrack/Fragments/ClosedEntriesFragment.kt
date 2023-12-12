package com.example.studentflowtrack.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentflowtrack.Adapters.ClosedEntriesAdapter
import com.example.studentflowtrack.Model.ClosedEntries
import com.example.studentflowtrack.databinding.FragmentClosedEntriesBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class ClosedEntriesFragment : Fragment() {

    private var _binding: FragmentClosedEntriesBinding? = null
    private val binding get() = _binding!!

    private lateinit var closedEntriesAdapter: ClosedEntriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClosedEntriesBinding.inflate(inflater, container, false)
        val view = binding.root

        setupRecyclerView()

        return view
    }

    private fun setupRecyclerView() {
        closedEntriesAdapter = ClosedEntriesAdapter(emptyList())
        binding.closedEntriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = closedEntriesAdapter
        }

        fetchDataAndUpdateAdapter()
    }

    private fun fetchDataAndUpdateAdapter() {
        val data = mutableListOf<ClosedEntries>()

        FirebaseFirestore.getInstance().collection("Closed").get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                if (task.isSuccessful) {
                    val result: QuerySnapshot? = task.result
                    if (result != null && !result.isEmpty) {
                        for (document in result) {
                            val name = document.getString("name") ?: ""
                            val inTime = "10:00" // Replace with the actual inTime logic
                            val outTime = document.getTimestamp("time").toString()

                            data.add(ClosedEntries(name, inTime, outTime))
                            Log.d("ClosedEntries", "Name: $name, In Time: $inTime, Out Time: $outTime")
                        }

                        closedEntriesAdapter.updateData(data)
                    }

                } else {
                    val exception: Exception? = task.exception
                    Log.e("FirestoreError", "Error fetching data", exception)
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
