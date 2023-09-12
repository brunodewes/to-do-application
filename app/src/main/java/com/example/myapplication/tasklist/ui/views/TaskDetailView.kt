package com.example.myapplication.tasklist.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.tasklist.ui.data.TaskDetailEvents
import com.example.myapplication.tasklist.ui.viewModels.TaskFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskDetailView(
    onPopBackStack: () -> Unit,
    viewModel: TaskFormViewModel = hiltViewModel(),
) {

    Scaffold(
        topBar = {
            Box {
                TopAppBar(
                    title = {

                    },
                    navigationIcon = {
                        IconButton(onClick = { TaskDetailEvents.GoBack }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Go back",
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { TODO() }) {
                            Icon(
                                imageVector = Icons.Default.Done, contentDescription = "Add todo",
                            )
                        }
                    },
                )
            }
        },
        content = {
//            Column(
//                modifier = Modifier.fillMaxSize()
//            ) {
//                TextField(
//                    value = uiState.title,
//                    onValueChange = {
//                        onEvent(TaskFormEvent.OnTitleChanged(it))
//                    },
//                    placeholder = {
//                        Text(text = "Title")
//                    },
//                    modifier = Modifier.fillMaxWidth(),
//                )
//                Spacer(modifier = Modifier.height(40.dp))
//                TextField(
//                    value = uiState.description.orEmpty(),
//                    onValueChange = {
//                        onEvent(TaskFormEvent.OnDescriptionChanged(it))
//                    },
//                    placeholder = {
//                        Text(text = "Description")
//                    },
//                    modifier = Modifier.fillMaxWidth(),
//                    singleLine = false,
//                )
//            }
        },
        bottomBar = {

        },
    )
}

@Preview
@Composable
private fun TaskDetailPreview() {
    TaskDetailView(onPopBackStack = { /*TODO*/ })
}