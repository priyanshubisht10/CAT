package com.example.studentflowtrack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.studentflowtrack.Fragments.AddNewStudentFragment
import com.example.studentflowtrack.Fragments.ClosedEntriesFragment
import com.example.studentflowtrack.Fragments.HistoryFragment
import com.example.studentflowtrack.Fragments.HostelAttendanceFragment
import com.example.studentflowtrack.Fragments.OpenEntriesFragment
import com.example.studentflowtrack.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val openEntriesFragment = OpenEntriesFragment()
    private val closedEntriesFragment = ClosedEntriesFragment()
    private val historyFragment = HistoryFragment()
    private val hostelAttendanceFragment = HostelAttendanceFragment()
    private val addNewStudentFragment = AddNewStudentFragment()

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.open_entries -> {
                    replaceFragment(openEntriesFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.closed_entries -> {
                    replaceFragment(closedEntriesFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.history -> {
                    replaceFragment(historyFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.hostel_attendance -> {
                    replaceFragment(hostelAttendanceFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.add_new_student -> {
                    replaceFragment(addNewStudentFragment)
                    return@OnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        replaceFragment(openEntriesFragment)
    }
}
