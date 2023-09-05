package com.example.myapplication

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.tasklist.ui.views.TaskFormFragment
import com.example.myapplication.tasklist.ui.views.TaskListFragment
import com.example.myapplication.tasklist.ui.views.TaskListView
import com.example.myapplication.tasklist.utils.Routes
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.TASK_LIST) {
                    composable(Routes.TASK_LIST) {
                        TaskListFragment()
                    }
                    composable(Routes.TASK_FORM) {
                        TaskFormFragment()
                    }
                }
            }
        }
    }
}