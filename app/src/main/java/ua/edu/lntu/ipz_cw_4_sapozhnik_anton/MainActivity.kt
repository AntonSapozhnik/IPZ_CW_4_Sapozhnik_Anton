package ua.edu.lntu.ipz_cw_4_sapozhnik_anton

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.edu.lntu.ipz_cw_4_sapozhnik_anton.ui.theme.IPZ_CW_4_Sapozhnik_AntonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IPZ_CW_4_Sapozhnik_AntonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun Screen1(items: List<Pair<String, String>>, onItemClick: (String) -> Unit) {//виправленно
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Завдання")
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            itemsIndexed(items) { index, (taskId, taskText) ->
                ListItem(taskId) { clickedTaskId ->
                    onItemClick(clickedTaskId)
                }
            }
        }
    }
}

@Composable
fun ListItem(taskId: String, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(taskId) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = taskId)
    }
}

@Composable
fun Screen2(taskText: String?, onDoneClick: () -> Unit) {
    var isTaskCompleted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Екран 2")
        Spacer(modifier = Modifier.height(16.dp))
        taskText?.let { // Перевіряємо, чи є текст завдання, перед тим як відображати
            Text(it)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!isTaskCompleted) {
            Button(onClick = {
                onDoneClick()
                isTaskCompleted = true
            }) {
                Text(text = "Done")
            }
        }
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    var selectedItem by remember { mutableStateOf<String?>(null) }
    var currentScreen by remember { mutableIntStateOf(0) }

    val items = remember {
        listOf(
            "Завдання 1" to "Перевірити можливість створення нового Jetpack Compose проекту в Android Studio",
            "Завдання 2" to "Перевірити роботу git(додати git до проекту, команди commit, push, можливість роботи з віддаленим репозиторієм)",
            "Завдання 3" to "Перевірити Інтернет з`єднання",
            "Завдання 4" to "Перевірити камеру для відеозвязку. Як варіант використовувати телефон"
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Завдання") },
                navigationIcon = {
                    IconButton(onClick = { currentScreen = 0 }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        when (currentScreen) {
            0 -> Screen1(items = items) { clickedTaskId ->
                selectedItem = items.find { it.first == clickedTaskId }?.second
                currentScreen = 1
            }

            1 -> Screen2(taskText = selectedItem) {
                selectedItem = null
                currentScreen = 0
            }
        }
    }
}

