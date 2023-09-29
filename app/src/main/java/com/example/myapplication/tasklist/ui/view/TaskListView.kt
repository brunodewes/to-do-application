package com.example.myapplication.tasklist.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.tasklist.ui.data.TaskListItemUiState
import com.example.myapplication.tasklist.ui.data.TaskListUiEvents
import com.example.myapplication.tasklist.ui.data.TaskListUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListView(
    uiState: TaskListUiState,
    onUiEvent: (TaskListUiEvents) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Box(modifier = Modifier.background(color = colorResource(id = R.color.primary_color))) {
        Scaffold(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                Column {
                    TopAppBar(
                        colors = topAppBarColors(
                            containerColor = colorResource(id = R.color.primary_color),
                            titleContentColor = colorResource(id = R.color.secondary_color)
                        ),
                        title = {
                            Text(text = "My todos")
                        },
                        actions = {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    tint = colorResource(id = R.color.secondary_color),
                                    contentDescription = "Search todo"
                                )
                            }
                            IconButton(onClick = { onUiEvent(TaskListUiEvents.DeleteDone) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    tint = colorResource(id = R.color.secondary_color),
                                    contentDescription = "Remove done todos"
                                )
                            }
                        },
                        scrollBehavior = scrollBehavior
                    )
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        color = colorResource(id = R.color.task_card_color),
                        thickness = 1.dp
                    )
                }
            },
            content = { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colorResource(id = R.color.primary_color))
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.tasks) {
                            TaskItem(
                                uiState = it, onUiEvent = onUiEvent
                            )
                        }
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(shape = CircleShape,
                    modifier = Modifier.size(60.dp),
                    containerColor = colorResource(id = R.color.tertiary_color),
                    contentColor = colorResource(id = R.color.secondary_color),
                    onClick = { onUiEvent(TaskListUiEvents.OnAddTaskClick) }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add todo",
                    )
                }
            },
            bottomBar = {

            },
        )
    }
}


@Composable
private fun TaskItem(
    uiState: TaskListItemUiState,
    onUiEvent: (TaskListUiEvents) -> Unit,
) {
    Card(
        modifier = Modifier
            .clickable { onUiEvent(TaskListUiEvents.OnTaskClick(taskId = uiState.id)) }
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.task_card_color)
        ),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.task_item_horizontal_padding),
                        vertical = dimensionResource(id = R.dimen.task_item_vertical_padding),
                    )
                    .weight(1f)
            ) {
                Text(
                    text = uiState.title,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.primary_text_color),
                    fontSize = 25.sp
                )
                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = uiState.description.orEmpty(),
                    color = colorResource(id = R.color.secondary_text_color),
                    fontSize = 12.sp
                )
            }
            Checkbox(
                colors = CheckboxDefaults.colors(
                    checkedColor = colorResource(id = R.color.tertiary_color),
                    checkmarkColor = colorResource(id = R.color.secondary_color),
                    uncheckedColor = colorResource(id = R.color.tertiary_color),
                ),
                checked = uiState.isChecked, onCheckedChange = {
                    onUiEvent(TaskListUiEvents.OnCheckChanged(uiState.id))
                })
        }
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
                    description = "Description",
                    isChecked = true,
                )
            )
        ),
        onUiEvent = {},
    )
}