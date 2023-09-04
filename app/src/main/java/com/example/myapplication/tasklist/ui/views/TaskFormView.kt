package com.example.myapplication.tasklist.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.tasklist.ui.viewModels.TaskFormViewModel
import com.example.myapplication.tasklist.ui.viewModels.TaskFormViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskFormView(
    onPopBackStack: () -> Unit,
    viewModel: TaskFormViewModel = hiltViewModel()
) {

    Scaffold(
        topBar = {
            Box {
                TopAppBar(
                    title = {

                    },
                    navigationIcon = {
                        IconButton(onClick = { onPopBackStack }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Go back"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { viewModel.addTask() }) {
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = "Add todo"
                            )
                        }
                    },
                )
            }
        },
        content = {
            Column (
                modifier = Modifier.fillMaxSize()
            ) {
                TextField(
                    placeholder = {
                        Text(text = "Title")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        bottomBar = {

        },
    )
}

@Preview
@Composable
private fun TaskFormPreview() {
    TaskFormView()
}