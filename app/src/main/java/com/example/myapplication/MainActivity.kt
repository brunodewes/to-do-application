package com.example.myapplication

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.tasklist.ui.viewModels.TaskDetailViewModel
import com.example.myapplication.tasklist.ui.viewModels.TaskFormViewModel
import com.example.myapplication.tasklist.ui.viewModels.TaskListViewModel
import com.example.myapplication.tasklist.ui.views.TaskDetailView
import com.example.myapplication.tasklist.ui.views.TaskFormView
import com.example.myapplication.tasklist.ui.views.TaskListView
import com.example.myapplication.tasklist.utils.Routes
import com.example.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val taskListViewModel: TaskListViewModel by viewModels()
    private val taskFormViewModel: TaskFormViewModel by viewModels()
    private val taskDetailViewModel: TaskDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Routes.TASK_LIST) {
                    composable(Routes.TASK_LIST) {
                        val screenState = taskListViewModel.taskListLiveData.observeAsState()
                        screenState.value?.let { uiState ->
                            TaskListView(
                                onNavigateToForm = { navController.navigate(Routes.TASK_FORM) },
                                uiState = uiState,
                                onEvent = { taskListViewModel.onEvent(it) }
                            )
                        }
                    }

                    composable(route = Routes.TASK_FORM) {
                        val screenState = taskFormViewModel.taskFormLiveData.observeAsState()
                        screenState.value?.let { uiState ->
                            TaskFormView(
                                onPopBackStack = { navController.popBackStack() },
                                uiState = uiState,
                                onEvent = { taskFormViewModel.onEvent(it) }
                            )
                        }
                    }

                    composable(route = Routes.TASK_DETAIL + "?taskId+{taskId}", arguments = listOf(
                        navArgument(name = "taskId") {
                            type = NavType.StringType
                        }
                    )) {
                        TaskDetailView(onPopBackStack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}