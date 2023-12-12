package com.example.studentflowtrack.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentflowtrack.Adapters.OpenEntriesAdapter
import com.example.studentflowtrack.Model.OpenEntries
import com.example.studentflowtrack.databinding.FragmentOpenEntriesBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class OpenEntriesFragment : Fragment() {

    private var _binding: FragmentOpenEntriesBinding? = null
    private val binding get() = _binding!!

    private lateinit var openEntriesAdapter: OpenEntriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOpenEntriesBinding.inflate(inflater, container, false)
        val view = binding.root

        setupRecyclerView()

        return view
    }

    private fun setupRecyclerView() {
        openEntriesAdapter = OpenEntriesAdapter(emptyList())
        binding.openEntriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = openEntriesAdapter
        }

        fetchDataAndUpdateAdapter()
    }

    private fun fetchDataAndUpdateAdapter() {
        val data = mutableListOf<OpenEntries>()

        FirebaseFirestore.getInstance().collection("Open").get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                if (task.isSuccessful) {
                    val result: QuerySnapshot? = task.result
                    if (result != null && !result.isEmpty) {
                        for (document in result) {
                            val name = document.getString("name") ?: ""
                            val time = document.getTimestamp("time")?.toString() ?: ""

                            data.add(OpenEntries(name, time))
                        }

                        openEntriesAdapter.updateData(data)
                        Log.d("FirestoreSuccess", "Data loaded successfully")
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
