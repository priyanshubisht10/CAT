package com.example.studentflowtrack.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.studentflowtrack.databinding.FragmentAddNewStudentBinding
import com.google.firebase.firestore.FirebaseFirestore

class AddNewStudentFragment : Fragment() {

    private lateinit var binding: FragmentAddNewStudentBinding
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNewStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set click listener for the Save button
        binding.btnSave.setOnClickListener {
            saveDataToFirestore()
        }
    }

    private fun saveDataToFirestore() {
        // Get data from EditText fields
        val name = binding.editTextName.text.toString()
        val rollNo = binding.editTextRollNo.text.toString()
        val department = binding.editTextDepartment.text.toString()
        val hostelName = binding.editTextHostelName.text.toString()
        val roomNo = binding.editTextRoomNo.text.toString()

        // Create a data map
        val studentData = hashMapOf(
            "name" to name,
            "rollNo" to rollNo,
            "department" to department,
            "hostelName" to hostelName,
            "roomNo" to roomNo
        )

        // Add data to Firestore collection
        firestore.collection("Students")
            .document(rollNo) // Replace with your actual collection name
            .set(studentData)
            .addOnSuccessListener {
                binding.editTextDepartment.text.clear()
                binding.editTextName.text.clear()
                binding.editTextHostelName.text.clear()
                binding.editTextRoomNo.text.clear()
                binding.editTextRollNo.text.clear()
                Toast.makeText(requireContext(), "Data added successfully", Toast.LENGTH_SHORT)
                    .show()
            }
            .addOnFailureListener {
                // Handle failure
            }
    }
}
