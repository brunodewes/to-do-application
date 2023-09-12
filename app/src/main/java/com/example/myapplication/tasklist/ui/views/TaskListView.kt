package com.example.myapplication.tasklist.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.R
import com.example.myapplication.tasklist.ui.data.TaskListItemUiState
import com.example.myapplication.tasklist.ui.data.TaskListEvents
import com.example.myapplication.tasklist.ui.data.TaskListUiState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskListView(
    onNavigateToForm: () -> Unit,
    onEvent: (TaskListEvents) -> Unit,
    uiState: TaskListUiState,
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .fillMaxHeight()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Box {
                TopAppBar(
                    title = {
                        Text(text = "My todos")
                    },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search todo"
                            )
                        }
                        IconButton(onClick = { onEvent(TaskListEvents.DeleteDone) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Remove done todos"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToForm) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Add todo",
                )
            }
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                items(uiState.tasks) {
                    TaskItem(
                        uiState = it,
                        onEvent = onEvent,
                    )
                }
            }
        },
        bottomBar = {

        }
    )
}

@Composable
private fun TaskItem(
    uiState: TaskListItemUiState,
    onEvent: (TaskListEvents) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = dimensionResource(id = R.dimen.task_item_border_width),
                color = colorResource(id = R.color.black),
            )
            .padding(
                horizontal = dimensionResource(id = R.dimen.task_item_horizontal_padding),
                vertical = dimensionResource(id = R.dimen.task_item_vertical_padding),
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f, fill = true),
            text = uiState.title,
        )

        Checkbox(
            checked = uiState.isChecked,
            onCheckedChange = {
                onEvent(TaskListEvents.OnCheckChanged(uiState.id))
                println(uiState.isChecked)
            },
        )
    }
}

@Preview
@Composable
private fun TaskListPreview() {
    TaskListView(
        uiState = TaskListUiState(
            tasks = listOf(
                TaskListItemUiState(
                    id = "id",
                    title = "Title",
                    isChecked = true,
                )
            )
        ),
        onEvent = {},
        onNavigateToForm = {},
    )
}