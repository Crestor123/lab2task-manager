package com.example.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskmanager.model.Task
import com.example.taskmanager.ui.theme.TaskManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskManagerTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier : Modifier = Modifier) {
    var textInput by remember { mutableStateOf(" ") }
    val taskList = remember { mutableStateListOf<Task>() }
    Box(

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TaskInputField(
                value = textInput,
                onValueChange = { textInput = it },
                onAddButton = {
                    if (textInput == "") return@TaskInputField
                    val task = Task(textInput, false)
                    taskList.add(task)
                    textInput = ""
                }
            )
            TaskList(taskList)
        }
    }
}

@Composable
fun TaskInputField(
    value: String,
    onValueChange: (String) -> Unit,
    onAddButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text("Enter Task") },
            singleLine = true,
            modifier = Modifier.weight(.65f)
        )
        Button(
            onClick = onAddButton,
            colors = ButtonColors(Color.Blue, Color.White, Color.Gray, Color.LightGray)
        ) {
            Text("Add Task")
        }
    }
}

@Composable
fun TaskList(taskList: MutableList<Task>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(taskList) { task ->
            TaskItem(task, taskList)
        }
    }
}

@Composable
fun TaskItem(taskObject: Task, taskList: MutableList<Task>, modifier: Modifier = Modifier) {
    val task = taskObject
    var checked by remember { mutableStateOf(task.checked) }
    val style = if(task.checked) {
        TextStyle(textDecoration = TextDecoration.LineThrough, color = Color.LightGray)
    }
    else {
        TextStyle(textDecoration = TextDecoration.None)
    }
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 8.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = {
                task.checked = it
                checked = it
            }
        )
        Text(
            text = task.text,
            textAlign = TextAlign.Left,
            style = style
        )
        Spacer(
            Modifier.weight(.1f)
        )
        TextButton(
            onClick = { taskList.remove(task) }
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Delete Task",
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    TaskManagerTheme {
        MainScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun TaskItemPreview() {
    val task = Task("Test Item", false)
    val taskList: MutableList<Task> = listOf(task).toMutableList()
    TaskItem(task, taskList)
}