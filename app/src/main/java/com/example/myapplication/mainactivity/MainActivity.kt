package com.example.myapplication.mainactivity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.tasklist.ui.view.TaskListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel.stream.subscribe { type ->
            when(type) {
                Type.AddFragment -> {
                    addFragment()
                    viewModel.fragmentAdded()
                }
                else -> {/*NO OP*/}
            }
        }
    }

    private fun addFragment() {
        supportFragmentManager.beginTransaction()
            .add(binding.fragmentContainerView.id, TaskListFragment())
            .commit()
    }
}